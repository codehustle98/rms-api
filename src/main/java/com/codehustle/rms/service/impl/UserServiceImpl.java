package com.codehustle.rms.service.impl;

import com.codehustle.rms.constants.ApplicationConstants;
import com.codehustle.rms.constants.MessageConstants;
import com.codehustle.rms.entity.Organization;
import com.codehustle.rms.entity.User;
import com.codehustle.rms.exceptions.ConflictException;
import com.codehustle.rms.exceptions.InvalidArgumentsException;
import com.codehustle.rms.exceptions.NotFoundException;
import com.codehustle.rms.exceptions.UnauthorizedException;
import com.codehustle.rms.model.UserModel;
import com.codehustle.rms.repository.OrganizationRepository;
import com.codehustle.rms.repository.UserRepository;
import com.codehustle.rms.security.JwtUtils;
import com.codehustle.rms.security.SecurityUtils;
import com.codehustle.rms.service.UserService;
import com.codehustle.rms.types.UserType;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUserEmailId(username).orElseThrow(()->new UsernameNotFoundException(MessageConstants.USER_NOT_FOUND));
        return new UserModel(user.getUserId(),user.getUserEmailId(),user.getUserPassword(),user.getUserType(),user.isActive());
    }

    @Override
    public User signUpUser(User user) throws Exception {
        Optional<User> eUser = userRepository.findByUserEmailId(user.getUserEmailId());
        if(eUser.isPresent())
            throw new ConflictException(MessageConstants.USER_EMAIL_EXISTS);
        if(user.getOrganization() == null)
            throw new InvalidArgumentsException("Organization data is empty");
        Organization organization = organizationRepository.findByOrganizationCode(user.getOrganization().getOrganizationCode());
        if(organization != null)
            throw new ConflictException(MessageConstants.ORG_CODE_EXISTS);
        user.getOrganization().setCreatedBy(user.getUserEmailId());
        user.getOrganization().setModifiedBy(user.getUserEmailId());
        user.getOrganization().setCreatedAt(LocalDateTime.now());
        user.getOrganization().setModifiedAt(LocalDateTime.now());
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setUserType(UserType.MANAGER.getType());
        user.setActive(true);
        user.setCreatedBy(user.getUserEmailId());
        user.setModifiedBy(user.getUserEmailId());
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public void signIn(User user) {
        loadUserByUsername(user.getUserEmailId());
    }

    @Override
    public void refreshUserToken(String refreshToken, HttpServletResponse response) throws UnauthorizedException {
        try {
            if (JwtUtils.isTokenValid(refreshToken)) {
                UserDetails userDetails = loadUserByUsername(JwtUtils.getUsernameFromToken(refreshToken));
                if (userDetails != null) {
                    String token = JwtUtils.generateToken(
                            userDetails.getUsername(),
                            userDetails.getAuthorities()
                    );
                    String newRefreshToken = JwtUtils.generateRefreshToken(
                            userDetails.getUsername(),
                            userDetails.getAuthorities()
                    );
                    response.setHeader(ApplicationConstants.AUTH_HEADER, token);
                    response.setHeader(ApplicationConstants.REFRESH_HEADER, newRefreshToken);
                }
            }
        }catch (ExpiredJwtException e){
            throw new UnauthorizedException(MessageConstants.SESSION_EXPIRED);
        }
    }

    @Override
    public User findUserbyId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> getAllUsers() throws NotFoundException {
        User user = findUserbyId(SecurityUtils.getUserId());
        if(user == null)
            throw new NotFoundException(MessageConstants.USER_NOT_FOUND);
        return userRepository.findAllByOrganizationOrganizationId(user.getOrganization().getOrganizationId(), PageRequest.of(1,10));
    }
}
