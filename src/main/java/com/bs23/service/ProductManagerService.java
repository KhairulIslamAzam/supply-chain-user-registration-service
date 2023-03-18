package com.bs23.service;

import com.bs23.domain.dto.UserDto;
import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;

public class ProductManagerService implements UserService{
    @Override
    public UserDto getUserByUserName(String userName) {
        return null;
    }

    @Override
    public TokenResponse authenticateUser(AuthenticationRequest request) {
        return null;
    }

    @Override
    public UserDto getActiveUserByUserId(Long userId) {
        return null;
    }
}
