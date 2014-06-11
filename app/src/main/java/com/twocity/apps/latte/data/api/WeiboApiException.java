package com.twocity.apps.latte.data.api;

import com.twocity.apps.latte.data.api.model.WeiboError;
import com.twocity.apps.latte.utils.Utils;

import retrofit.RetrofitError;

/**
 * Created by twocity on 14-2-12.
 */

public class WeiboApiException extends RuntimeException {

  private int code = ERROR_UNEXPECTED;

  WeiboApiException(String message, int code, StackTraceElement[] trace) {
    super(message);
    setStackTrace(trace);
    this.code = code;
  }

  public int getErrorCode() {
    return code;
  }

  public static WeiboApiException create(RetrofitError error) {
    int code = ERROR_UNEXPECTED;
    String message = "unknown";
    if (error.getMessage() != null) {
      message = error.getMessage();
    }
    if (error.isNetworkError()) {
      code = ERROR_NETWORK;
      message = "network error";
    }
    if (error.getResponse() == null) {
      code = ERROR_UNEXPECTED;
      message = "Unexpected Error";
    } else {
      WeiboError weiboError = (WeiboError) error.getBodyAs(WeiboError.class);
      if (weiboError != null) {
        code = Utils.parseIntSafely(weiboError.getErrorCode(), ERROR_UNEXPECTED);
        message = weiboError.getErrorMessage();
      }
    }
    return new WeiboApiException(message, code, error.getStackTrace());
  }

  private static final int ERROR_NETWORK = 1;

  private static final int ERROR_UNEXPECTED = 2;
}