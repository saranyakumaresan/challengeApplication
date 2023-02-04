package com.mindex.challenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorCodeConfig {

    @Value("1001 The resource requested is not found")
    private String notFoundCode;

    @Value("1002 The server encountered an unexpected condition")
    private String internalServerCode;

    @Value("1003 The request is missing parameter")
    private String badRequestCode;

    public String getInternalServerCode() {
        return internalServerCode;
    }

    public void setInternalServerCode(String internalServerCode) {
        this.internalServerCode = internalServerCode;
    }

    public String getNotFoundCode() {
        return notFoundCode;
    }

    public void setNotFoundCode(String notFoundCode) {
        this.notFoundCode = notFoundCode;
    }

    public String getBadRequestCode() {
        return badRequestCode;
    }

    public void setBadRequestCode(String badRequestCode) {
        this.badRequestCode = badRequestCode;
    }
}
