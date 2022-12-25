package com.codehustle.rms.controller;

import com.codehustle.rms.entity.User;
import com.codehustle.rms.exceptions.UnauthorizedException;
import com.codehustle.rms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "user/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "signin")
    @ResponseStatus(HttpStatus.OK)
    public void signInUser(@RequestBody User user){
        userService.signIn(user);
    }

    @PostMapping(value = "signup")
    public ResponseEntity<User> signUpUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok().body(userService.signUpUser(user));
    }

    @PostMapping(value = "auth/refresh")
    @ResponseStatus(HttpStatus.OK)
    public void refreshUserToken(@RequestBody String refreshToken, HttpServletResponse response) throws UnauthorizedException {
        userService.refreshUserToken(refreshToken,response);
    }

}
