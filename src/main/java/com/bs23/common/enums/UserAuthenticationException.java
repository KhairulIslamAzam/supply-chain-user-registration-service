package com.bs23.common.enums;

import com.bs23.common.exceptions.CustomRootException;

public class UserAuthenticationException extends CustomRootException {
    public UserAuthenticationException(String message) {
        super(message);
    }
}
