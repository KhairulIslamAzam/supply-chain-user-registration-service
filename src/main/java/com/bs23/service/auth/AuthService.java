package com.bs23.service.auth;

import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    TokenResponse authenticate(AuthenticationRequest request);
    UserDetails validateToken(String token);
}
