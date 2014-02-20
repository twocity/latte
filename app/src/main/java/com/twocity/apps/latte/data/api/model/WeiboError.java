package com.twocity.apps.latte.data.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by twocity on 14-2-20.
 */
public class WeiboError {

    private String request;

    @SerializedName("error_code")
    private String errorCode;

    @SerializedName("error")
    private String errorMessage;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "WeiboError" +
                "request='" + request + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage;
    }
}
