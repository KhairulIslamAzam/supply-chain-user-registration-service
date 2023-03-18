package com.bs23.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CbsResponse<T> extends ApiResponse{
    private Integer responseCode;
}
