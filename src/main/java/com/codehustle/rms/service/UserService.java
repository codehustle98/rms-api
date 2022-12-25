package com.codehustle.rms.service;

import com.codehustle.rms.entity.User;
import com.codehustle.rms.exceptions.UnauthorizedException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    User signUpUser(User user) throws Exception;

    void signIn(User user);

    void refreshUserToken(String refreshToken, HttpServletResponse response) throws UnauthorizedException;

    User findUserbyId(Long userId);
}
