package com.codehustle.rms.security;

import com.codehustle.rms.constants.ApplicationConstants;
import com.codehustle.rms.constants.MessageConstants;
import com.codehustle.rms.exceptions.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final List<String> excludeUrls = List.of("/signup","/signin","/auth/refresh");

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludeUrls.stream().anyMatch(x -> request.getRequestURI().contains(x));
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getAuthTokenFromHeader(request);
       if(token != null){
           try {
                if(JwtUtils.isTokenValid(token)){
                    UserDetails userDetails = new User(
                            JwtUtils.getUsernameFromToken(token),
                            "",
                            JwtUtils.getAuthorities(token)
                    );
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
                    authenticationToken.setDetails(userDetails);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
           }catch (IllegalArgumentException | MalformedJwtException e){
               logger.info("Invalid Auth token");
               throw new UnauthorizedException(e,HttpStatus.UNAUTHORIZED.getReasonPhrase());
           }catch (ExpiredJwtException e){
               throw new UnauthorizedException(e, MessageConstants.TOKEN_EXPIRED);
           }
       }
        filterChain.doFilter(request,response);
    }

    private String getAuthTokenFromHeader(HttpServletRequest request){
        String token = request.getHeader(ApplicationConstants.AUTH_HEADER);
        return StringUtils.hasText(token) ? token : null;
    }

}
