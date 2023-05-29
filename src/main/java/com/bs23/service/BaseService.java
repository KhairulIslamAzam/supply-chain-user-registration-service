package com.bs23.service;

import com.bs23.common.logger.AuthServiceLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class BaseService {
    protected  AuthServiceLogger authServiceLogger;
    @Value("${jwt.token.prefix}")
    protected String tokenPrefix;

    @Value("${jwt.secret.key}")
    protected String jwtSecretKey;

}
