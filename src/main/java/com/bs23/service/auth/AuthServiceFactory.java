package com.bs23.service.auth;

import com.bs23.common.utils.ApplicationContextHolder;
import com.bs23.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AuthServiceFactory extends BaseService {
    public AuthService getAuthService() {
        return ApplicationContextHolder.getContext().getBean(AuthServiceImpl.class);
    }
}
