package com.twocity.apps.latte.ui;

import retrofit.Callback;
import retrofit.client.Response;

/**
 * Created by twocity on 14-5-5.
 */
public abstract class TimelineDatabase<T> implements Callback<T> {

  T data;
  Response response;

  @Override public void success(T t, Response response) {
    if (data != null) {
      onSuccess(t, this.response);
    } else {
      this.data = t;
      this.response = response;
    }
  }

  public abstract void onSuccess(T t, Response response);
}
