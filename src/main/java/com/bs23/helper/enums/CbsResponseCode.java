package com.bs23.helper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CbsResponseCode {
    SUCCESS(100),
    FAILED(400),
    NOT_FOUND(101),
    TIME_OUT(300);

    private Integer status;
}
