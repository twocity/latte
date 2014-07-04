package com.twocity.apps.latte.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.twocity.apps.latte.data.api.ApiModule;
import com.twocity.apps.latte.ui.LatteActivity;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by twocity on 14-6-12.
 */
@Module(
    injects = { LatteActivity.class },
    complete = false,
    library = true)
public class DataModule {

  @Provides @Singleton SharedPreferences provideSharedPreference(Application app) {
    return app.getSharedPreferences("Latte", Context.MODE_PRIVATE);
  }

  @Provides @Singleton OkHttpClient provideOkHttpClient(Application app) {
    return createOkHttpClient(app);
  }

  @Provides @Singleton Picasso providePicasso(Application app, OkHttpClient client) {
    return new Picasso.Builder(app).downloader(new OkHttpDownloader(client)).build();
  }

  static OkHttpClient createOkHttpClient(Application app) {
    OkHttpClient client = new OkHttpClient();
    return client;
  }
}
