package com.bs23.helper.exceptions;

public abstract class CustomRootException extends RuntimeException {

    public CustomRootException(String message) {
        super(message);
    }
}
