package com.englishschool.englishschool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnathorizedException extends RuntimeException {
    public UnathorizedException() {
        super();
    }

    public UnathorizedException(String message) {
        super(message);
    }
}
