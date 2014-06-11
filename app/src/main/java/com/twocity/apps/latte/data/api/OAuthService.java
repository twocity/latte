package com.twocity.apps.latte.data.api;

import com.twocity.apps.latte.data.api.model.OAuthToken;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface OAuthService {

  @FormUrlEncoded @POST("/oauth2/access_token")
  public Observable<OAuthToken> accessToken(@Field("username") String userName,
      @Field("password") String password, @Field("client_id") String clientId,
      @Field("client_secret") String clientSecret, @Field("grant_type") String grantType);

  @FormUrlEncoded @POST("/oauth2/access_token")
  public OAuthToken accessToken1(@Field("username") String userName,
      @Field("password") String password, @Field("client_id") String clientId,
      @Field("client_secret") String clientSecret, @Field("grant_type") String grantType);
}