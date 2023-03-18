package com.bs23.helper.enums;

public enum ResponseMessages {

    OPERATION_SUCCESSFUL("Operation Successful"),
    RECORD_FOUND("Record Already Exists"),
    OPERATION_FAILED("Operation failed"),
    CONNECTION_OUT("CBS connection timeout"),
    RESULT_NOT_FOUND("Result Not Found"),
    RECORD_NOT_FOUND("Record Not Found");

    private String key;

    ResponseMessages(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
