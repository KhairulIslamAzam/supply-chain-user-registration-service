package com.bs23.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

@AllArgsConstructor
@Getter
public enum UserStatusEnum {
    ACTIVE(1,"ACTIVE"),
    ACCOUNT_CLOSED(2, "ACCOUNT INACTIVE"),
    ACCOUNT_TEMPORARY_BLOCK(3, "ACCOUNT TEMPORARY BLOCK"),
    PASSWORD_EXPIRED(5, "ACCOUNT EXPIRED");

    private Integer code;
    private String message;

//    public static boolean isUserBasicUser(int status) {
//        return UserStatusEnum.BASIC_DST_AGENT.getCode() == status;
//    }

    public static boolean isUserTemporarilyBlocked(int status) {
        return UserStatusEnum.ACCOUNT_TEMPORARY_BLOCK.getCode() == status;
    }

    public static boolean isUserActive(int status) {
        return UserStatusEnum.ACTIVE.getCode() == status;
    }
    public static boolean isUserPasswordExpired(int status) {
        return UserStatusEnum.PASSWORD_EXPIRED.getCode() == status;
    }
    public static boolean isAccountClosed(int status) {
        return UserStatusEnum.ACCOUNT_CLOSED.getCode() == status;
    }
}
