package com.bs23.service.auth;

import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthServiceImpl implements AuthService {
    @Override
    public TokenResponse authenticate(AuthenticationRequest request) {
        return null;
    }

    @Override
    public UserDetails validateToken(String token) {
        return null;
    }
}
