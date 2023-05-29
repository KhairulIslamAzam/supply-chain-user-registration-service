package com.bs23.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserTypeEnum {
    ADMIN(1,"ADMIN"),
    DEALER(2,"DEALER"),
    PRODUCT_MANAGER(3,"PRODUCT_MANAGER"),
    INVENTORY_MANAGER(4,"INVENTORY_MANAGER");

    private int code;
    private String name;
}
