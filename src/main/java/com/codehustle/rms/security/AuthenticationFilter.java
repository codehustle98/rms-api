package com.codehustle.rms.security;

import antlr.Token;
import com.codehustle.rms.constants.ApplicationConstants;
import com.codehustle.rms.constants.MessageConstants;
import com.codehustle.rms.entity.User;
import com.codehustle.rms.model.TokenSubject;
import com.codehustle.rms.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/user/signin");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserEmailId(),user.getUserPassword());
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserModel userModel = ((UserModel)authResult.getPrincipal());
        TokenSubject tokenSubject = objectMapper.convertValue(userModel, TokenSubject.class);
        String token = JwtUtils.generateToken(
                objectMapper.writeValueAsString(tokenSubject),
                authResult.getAuthorities()
        );
        String refreshToken = JwtUtils.generateRefreshToken(
                tokenSubject.getUsername(),
                authResult.getAuthorities()
        );
        response.setHeader(ApplicationConstants.AUTH_HEADER, token);
        response.setHeader(ApplicationConstants.REFRESH_HEADER,refreshToken);
        response.setHeader("content-type", MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        response.sendError(HttpStatus.UNAUTHORIZED.value(), MessageConstants.INVALID_CREDENTIALS);
    }
}
