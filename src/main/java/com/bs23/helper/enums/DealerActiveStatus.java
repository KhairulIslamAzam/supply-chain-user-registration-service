package com.bs23.helper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum DealerActiveStatus {
    ACTIVE(1),
    IN_ACTIVE(2);

    private Integer status;
}
