package com.mymemefolder.mmfgateway.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DataIsPrivateException extends Exception {
    public DataIsPrivateException(String message) {
        super(message);
    }
}

