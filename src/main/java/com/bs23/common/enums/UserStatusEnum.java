package com.bs23.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

@AllArgsConstructor
@Getter
public enum UserStatusEnum {
    ACTIVE(1, "ACTIVE"),
    FORCE_PASSWORD_CHANGED(2, "PASSWORD NEED TO RESET"),
    ACCOUNT_RESTRICTED(3, "ACCOUNT RESTRICTED"),
    ACCOUNT_TEMPORARY_BLOCK(4, "ACCOUNT TEMPORARY BLOCKED"),
    ACCOUNT_CLOSED(5, "ACCOUNT INACTIVE"),
    PASSWORD_EXPIRED(6, "PASSWORD EXPIRED"),
    BASIC_CUSTOMER(7, "BASIC CUSTOMER"),
    UNAUTHORIZED_CUSTOMER(8, "UNAUTHORIZED CUSTOMER"),
    CUSTOMER_PIN_NOT_SET(9, "Customer PIN Customer"),
    UNAUTHORIZED_CUSTOMER_FORCE_PASSWORD_CHANGE(10, "Unauthorized customer force password change"),
    DEVICE_BINDING(11, "Device binding needed");

    private int code;
    private String text;

    private CustomerStatus(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public static boolean isCustomerBasicUser(int status) {
        return BASIC_CUSTOMER.getCode() == status;
    }

    public int getCode() {
        return this.code;
    }

    public String getText() {
        return this.text;
    }

    public static boolean isCustomerTemporarilyBlocked(int status) {
        return ACCOUNT_TEMPORARY_BLOCK.getCode() == status;
    }

    public static boolean isCustomerActive(int status) {
        return ACTIVE.getCode() == status;
    }

    public static boolean isCustomerPasswordExpired(int status) {
        return PASSWORD_EXPIRED.getCode() == status;
    }

    public static boolean isCustomerEligibleForForcePasswordChange(int status) {
        return FORCE_PASSWORD_CHANGED.getCode() == status || PASSWORD_EXPIRED.getCode() == status;
    }

    public static boolean isAccountRestricted(int status) {
        return ACCOUNT_RESTRICTED.getCode() == status;
    }

    public static boolean isAccountClosed(int status) {
        return ACCOUNT_CLOSED.getCode() == status;
    }

    public static boolean isAccountAccessAble(int status) {
        return BASIC_CUSTOMER.getCode() == status || UNAUTHORIZED_CUSTOMER.getCode() == status || CUSTOMER_PIN_NOT_SET.getCode() == status || FORCE_PASSWORD_CHANGED.getCode() == status || PASSWORD_EXPIRED.getCode() == status || ACTIVE.getCode() == status;
    }
}
