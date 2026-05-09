package com.myproject.nabe_bank.core.exception;

import com.myproject.nabe_bank.core.response.ResponseCode;

public class AppException extends RuntimeException {
    private final ResponseCode responseCode;

    public AppException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
