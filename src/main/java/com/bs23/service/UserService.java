package com.bs23.service;

import com.bs23.domain.dto.UserDto;
import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;

public interface UserService {
    UserDto getUserByUserName(String userName) throws IllegalAccessException, InstantiationException;
    TokenResponse authenticateUser(AuthenticationRequest request);
    UserDto getActiveUserByUserId(Long userId);
}
