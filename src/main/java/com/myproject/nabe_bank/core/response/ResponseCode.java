package com.myproject.nabe_bank.core.response;

public enum ResponseCode {
    SUCCESS(1000, "Success"),
    CREATED(1001, "Created"),

    INVALID_REQUEST(2000, "Invalid request"),
    VALIDATION_ERROR(2001, "Validation failed"),
    USER_NOT_FOUND(2002, "User not found"),
    EMAIL_ALREADY_EXISTS(2003, "Email already exists"),
    NOT_FOUND(2004, "Not found"),

    UNAUTHORIZED(3001, "Unauthorized"),
    INTERNAL_SERVER_ERROR(9000, "Internal server error");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
