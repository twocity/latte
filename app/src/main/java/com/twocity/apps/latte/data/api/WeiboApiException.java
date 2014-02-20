package com.twocity.apps.latte.data.api;

import com.twocity.apps.latte.data.api.model.WeiboError;

import retrofit.RetrofitError;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-12.
 */
// TODO redefine this exception
public class WeiboApiException extends RuntimeException {

    private final RetrofitError mRetrofitError;

    WeiboApiException(RetrofitError error) {
        super(createExceptionMessage(error));
        setStackTrace(error.getStackTrace());
        this.mRetrofitError = error;
    }

    private static String createExceptionMessage(RetrofitError error) {
        if (error.getMessage() != null) {
            return error.getMessage();
        }
        if (error.getResponse() != null) {
            Timber.d("response: %s", error.getResponse().toString());
            return "Status: " + error.getBodyAs(WeiboError.class).toString();
        }
        return "Unknow error";
    }

}
