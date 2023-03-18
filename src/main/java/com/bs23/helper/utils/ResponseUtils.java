package com.bs23.helper.utils;

import com.bs23.domain.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static <T> ResponseEntity createHttpResponse(HttpStatus status,
                                                        String message) {
        return ResponseEntity.status(status)
                .body(createResponseObject(message, null));
    }

    private ResponseUtils() {}

    public static <T> ResponseEntity<ApiResponse<T>> createHttpResponse(
            HttpStatus status, String message, T data) {
        return ResponseEntity.status(status).body(createResponseObject(message, data));
    }

    public static <T> ApiResponse<T> createResponseObject(String message, T data) {
        ApiResponse apiResponse = new ApiResponse<T>();
        apiResponse.setMessage(message);
        apiResponse.setData(data);
        return apiResponse;
    }
}
