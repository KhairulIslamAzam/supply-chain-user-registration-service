package com.bs23.common.utils;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private String message;
    private T data;

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public ApiResponse() {
    }

    public ApiResponse(final String message, final T data) {
        this.message = message;
        this.data = data;
    }
}
