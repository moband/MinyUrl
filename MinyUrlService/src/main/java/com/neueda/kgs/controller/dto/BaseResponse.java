package com.neueda.kgs.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * base response for REST services.
 *
 * @author Mohamamd Reza Alagheband
 *
 */
public class BaseResponse {

    // error codes
    public static final int SUCCESSFUL = 0;
    public static final int NOT_AUTHENTICATED = 1;
    public static final int ACCESS_DENIED = 2;
    public static final int SERVER_ERROR = 3;
    public static final int RESOURCE_NOT_FOUND = 4;
    public static final int METHOD_NOT_SUPPORTED = 5;
    public static final int MEDIA_TYPE_NOT_SUPPORTED = 6;
    public static final int BAD_REQUEST = 7;
    public static final int VALIDATION_ERROR = 8;
    public static final int JSON_PARSE_ERROR = 13;
    public static final int DATA_INTEGRITY_ERROR = 14;
    public static final int WS_ERROR = 15;

    protected boolean success;
    protected String message;
    protected Integer code;

    public BaseResponse() {
    }

    public BaseResponse(boolean success, String message, Integer code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return String.format("BaseResponse[success:%s, message: %s, code: %s]", String.valueOf(success), message, String.valueOf(code));
    }
}

