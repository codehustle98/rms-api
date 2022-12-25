package com.codehustle.rms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentsException extends Exception{

    public InvalidArgumentsException(){}
    public InvalidArgumentsException(String message){super(message);}
}
