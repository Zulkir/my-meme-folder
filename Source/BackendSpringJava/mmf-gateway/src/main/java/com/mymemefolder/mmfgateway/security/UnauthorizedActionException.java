package com.mymemefolder.mmfgateway.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedActionException extends Exception {
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
