package com.bs23.domain.common;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Getter
@Jacksonized
@Builder(toBuilder = true)
public class UserJwtPayload implements Serializable {
    private String userName;
    private int userType;
    private Integer userRole;
    private Integer status;
}
