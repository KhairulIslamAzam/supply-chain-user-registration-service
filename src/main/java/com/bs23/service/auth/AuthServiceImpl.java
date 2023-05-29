package com.bs23.service.auth;

import com.bs23.common.enums.ResponseMessageEnum;
import com.bs23.common.exceptions.CredentialMismatchException;
import com.bs23.common.exceptions.InvalidTokenException;
import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;
import com.bs23.service.BaseService;
import com.bs23.service.UserService;
import com.bs23.service.UserServiceFactory;
import com.bs23.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthService {

    private final UserServiceFactory factory;
    private final UserDetailsService userDetailService;
    @Override
    public TokenResponse authenticate(AuthenticationRequest request) {

        String username = request.getUserName();
        String password = request.getPassword();
        int userType = request.getUserType();

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password) || userType <=0)
            throw new CredentialMismatchException(ResponseMessageEnum.CREDENTIAL_MISMATCH.getText());

        UserService userService = factory.getUserDetailsByType(userType);
        return userService.authenticateUser(request);
    }

    @Override
    public UserDetails validateToken(String bearerToken) {
        if(StringUtils.isBlank(bearerToken) || !StringUtils.startsWith(bearerToken, tokenPrefix))
            throw new InvalidTokenException("Invalid Bearer Token");

        String jwtToken = JWTUtils.trimToken(bearerToken, tokenPrefix);
        String userName = JWTUtils.extractUserName(jwtToken, jwtSecretKey);

        UserDetails userDetails = userDetailService.loadUserByUsername(userName);
        if(!JWTUtils.validateToken(jwtToken, userDetails.getUsername(), jwtSecretKey))
            throw new InvalidTokenException("Invalid token or Expired!");

        return userDetails;
    }
}
