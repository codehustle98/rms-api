package com.codehustle.rms.security;

import com.codehustle.rms.model.TokenSubject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static String getLoginUserEmail() {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            TokenSubject tokenSubject = objectMapper.readValue(((UserDetails)principal).getUsername(),TokenSubject.class);
            return tokenSubject.getUsername();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Long getUserId() {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getDetails();
            TokenSubject tokenSubject = objectMapper.readValue(((UserDetails)principal).getUsername(),TokenSubject.class);
            return tokenSubject.getUserId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
