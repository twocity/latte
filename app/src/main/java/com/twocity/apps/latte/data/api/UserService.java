package com.twocity.apps.latte.data.api;

import com.twocity.apps.latte.data.api.model.User;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by twocity on 14-2-12.
 */
public interface UserService {
  @GET("/users/show.json")
  public User userDetail(@Query("uid") long uid);
}
