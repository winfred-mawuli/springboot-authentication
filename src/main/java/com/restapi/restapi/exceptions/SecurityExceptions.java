package com.restapi.restapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SecurityExceptions extends RuntimeException {
    public SecurityExceptions(String message){
        super(message);
    }
}
