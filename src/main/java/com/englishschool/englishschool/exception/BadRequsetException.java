package com.englishschool.englishschool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequsetException extends RuntimeException {
    public BadRequsetException() {
        super();
    }

    public BadRequsetException(String message) {
        super(message);
    }
}
