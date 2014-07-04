package com.twocity.apps.latte;

import android.app.Application;
import android.content.Context;

import com.twocity.apps.latte.data.api.ApiModule;
import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-12.
 */
public class LatteApp extends Application {

  private ObjectGraph originObjectGraph;
  private ObjectGraph userObjectGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      // TODO
    }
    buildObjectGraphAndInject();
  }

  private void buildObjectGraphAndInject() {
    originObjectGraph = ObjectGraph.create(Modules.list(this));
    userObjectGraph = originObjectGraph;
  }

  public void plusUserObjectGraph(String token) {
    userObjectGraph = originObjectGraph.plus(new ApiModule(token));
  }

  public void removeUserObjectGraph() {
    userObjectGraph = originObjectGraph;
    userObjectGraph = originObjectGraph.plus(new ApiModule(""));
  }

  public void inject(Object o) {
    userObjectGraph.inject(o);
  }

  public static LatteApp get(Context context) {
    return (LatteApp) context.getApplicationContext();
  }
}
