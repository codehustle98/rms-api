package com.codehustle.rms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends Exception{

    public UnauthorizedException(String message){
        super(message);
    }
    public UnauthorizedException(Exception e,String message){
        super(message);
    }
}
