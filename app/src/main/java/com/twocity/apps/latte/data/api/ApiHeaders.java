package com.twocity.apps.latte.data.api;

import retrofit.RequestInterceptor;

/**
 * Created by twocity on 14-2-12.
 */
public class ApiHeaders implements RequestInterceptor {

    private final String AUTHORIZATION_PREFIX = "OAuth2";

    private String authorizationValue;

    public ApiHeaders(String accessToken) {
        authorizationValue = AUTHORIZATION_PREFIX + " " + accessToken;
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", authorizationValue);
    }
}
