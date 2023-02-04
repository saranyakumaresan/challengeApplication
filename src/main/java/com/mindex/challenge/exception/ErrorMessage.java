package com.mindex.challenge.exception;

public class ErrorMessage {
    private int status;
    private String errorCode;
    private String message;
    private String description;


    public ErrorMessage(int status, String errorCode, String message, String description) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
