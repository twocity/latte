package com.twocity.apps.latte.data.api;

import retrofit.RestAdapter;

/**
 * Created by twocity on 14-2-14.
 */
public class WeiboClient {

  private RestAdapter mRestAdapter;

  private WeiboService mWeiboService;

  private UserService mUserService;

  private OAuthService mOAuthService;

  public WeiboClient(RestAdapter adapter) {
    mRestAdapter = adapter;
    mWeiboService = mRestAdapter.create(WeiboService.class);
    mUserService = mRestAdapter.create(UserService.class);
    mOAuthService = mRestAdapter.create(OAuthService.class);
  }

  public OAuthService getOAuthService() {
    return mOAuthService;
  }

  public WeiboService getWeiboService() {
    return mWeiboService;
  }

  public UserService getUserService() {
    return mUserService;
  }
}
