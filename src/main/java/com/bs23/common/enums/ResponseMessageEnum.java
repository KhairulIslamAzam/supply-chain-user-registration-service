package com.bs23.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ResponseMessageEnum {

    SERVICE_NOT_FOUND_EXP("Required Service not found"),
    CREDENTIAL_MISMATCH("Credential not match");

    private String text;
}
