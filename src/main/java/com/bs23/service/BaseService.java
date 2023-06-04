package com.bs23.service;

import com.bs23.common.logger.AuthServiceLogger;
import com.bs23.common.utils.IPUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
public class BaseService {
    protected  AuthServiceLogger authServiceLogger;
    @Value("${jwt.token.prefix}")
    protected String tokenPrefix;
    @Value("${jwt.secret.key}")
    protected String jwtSecretKey;
    @Value("${jwt.token.expiry.minute}")
    protected String jwtExpiryTime;
    protected HttpServletRequest request;
    protected ObjectMapper mapper = new ObjectMapper();

    public String getRemoteIPAddress() {
        String realIp = IPUtils.getClientRealIpAddress(request);
        if (StringUtils.isNotBlank(realIp)) {
            return realIp;
        } else {
            return request.getRemoteAddr();
        }
    }

    public Date getCurrentDate() {
        return new Date();
    }

}
