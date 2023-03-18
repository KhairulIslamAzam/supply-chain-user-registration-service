package com.bs23.common.exceptions;

public abstract class CustomRootException extends RuntimeException{
    public CustomRootException(String message) {
        super(message);
    }
}
