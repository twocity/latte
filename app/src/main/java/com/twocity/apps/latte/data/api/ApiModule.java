package com.twocity.apps.latte.data.api;

import com.squareup.okhttp.OkHttpClient;
import com.twocity.apps.latte.service.PostService;
import com.twocity.apps.latte.ui.LatteActivity;
import com.twocity.apps.latte.ui.OAuthActivity;
import com.twocity.apps.latte.ui.TimeLineFragment;
import com.twocity.apps.latte.ui.TimeLineFragment2;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * Created by twocity on 14-6-12.
 */
@Module(
    injects = {
        TimeLineFragment.class, TimeLineFragment2.class, OAuthActivity.class,
        PostService.class
    },
    complete = false,
    library = true)
public class ApiModule {

  private final String accessToken;

  public ApiModule(String accessToken) {
    this.accessToken = accessToken;
  }

  @Provides @Singleton @TOKEN String provideAccessToken() {
    return accessToken;
  }

  @Provides @Singleton Endpoint provideEndpoint() {
    return Endpoints.newFixedEndpoint(Config.BASE_URL);
  }

  @Provides @Singleton Client provideClient(OkHttpClient client) {
    return new OkClient(client);
  }

  @Provides @Singleton RestAdapter provideRestAdapter(Endpoint endpoint, Client client,
      ApiHeaders headers, ErrorHandler handler) {
    return new RestAdapter.Builder().setEndpoint(endpoint)
        .setClient(client)
        .setErrorHandler(handler)
        .setRequestInterceptor(headers)
        .build();
  }

  @Provides @Singleton ErrorHandler provideErrorHandler() {
    return new ErrorHandler() {
      @Override public Throwable handleError(RetrofitError cause) {
        return WeiboApiException.create(cause);
      }
    };
  }

  @Provides @Singleton PostService providePostService(RestAdapter restAdapter) {
    return restAdapter.create(PostService.class);
  }

  @Provides @Singleton OAuthService provideOAuthService(RestAdapter restAdapter) {
    return restAdapter.create(OAuthService.class);
  }

  @Provides @Singleton WeiboService provideWeiboService(RestAdapter restAdapter) {
    return restAdapter.create(WeiboService.class);
  }
}

