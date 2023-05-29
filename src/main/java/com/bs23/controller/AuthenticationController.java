package com.bs23.controller;

import com.bs23.common.utils.ResponseUtils;
import com.bs23.domain.request.AuthenticationRequest;
import com.bs23.domain.response.TokenResponse;
import com.bs23.service.auth.AuthServiceFactory;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class AuthenticationController {

    private final AuthServiceFactory authServiceFactory;

    @PostMapping("/access/token")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
        TokenResponse response = authServiceFactory.getAuthService().authenticate(request);
        return ResponseUtils.createHttpResponse(HttpStatus.OK, "", response);
    }
}
