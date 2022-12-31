package com.codehustle.rms.exceptions;

import com.codehustle.rms.constants.MessageConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AuthenticationException.class,UnauthorizedException.class})
    @ResponseBody
    public ResponseEntity handleAuthenticationException(HttpServletRequest request,Exception e){
        if(e instanceof UnauthorizedException){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(prepareErrorMap(e.getMessage()));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(prepareErrorMap(MessageConstants.TOKEN_EXPIRED));
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity handleNotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(prepareErrorMap(e.getMessage()));
    }

    private Map<String,Object> prepareErrorMap(String message){
        return Map.of(
                "message",message
        );
    }
}
