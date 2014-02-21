package com.twocity.apps.latte.data.api;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;

/**
 * Created by twocity on 14-2-14.
 */
public class RestAdapterFactory {

    public static RestAdapter make(UserManager userManager, OkClient client) {
        if (userManager.hasUser()) {
            ApiHeaders apiHeaders = new ApiHeaders(userManager.getCurrentUserOAuthToken());
            return new RestAdapter.Builder().setEndpoint(Config.BASE_URL).setClient(client)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setErrorHandler(new ErrorHandler() {
                        @Override
                        public Throwable handleError(RetrofitError cause) {
                            return new WeiboApiException(cause);
                        }
                    }).setRequestInterceptor(apiHeaders).build();

        }
        return new RestAdapter.Builder().setEndpoint(Config.BASE_URL).setClient(client)
                .setLogLevel(RestAdapter.LogLevel.HEADERS)
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        return new WeiboApiException(cause);
                    }
                }).build();
    }
}
