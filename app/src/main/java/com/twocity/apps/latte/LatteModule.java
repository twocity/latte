package com.twocity.apps.latte;

import android.app.Application;
import com.twocity.apps.latte.data.DataModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by twocity on 14-6-12.
 */
@Module(
    includes = {
        DataModule.class
    }
    //injects = {
    //    LatteApp.class
    //}
)
public class LatteModule {
  private final LatteApp app;

  public LatteModule(LatteApp app) {
    this.app = app;
  }

  @Provides @Singleton Application provideApplication() {
    return this.app;
  }
}
