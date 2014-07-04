package com.twocity.apps.latte.data;

import android.app.Application;
import com.twocity.apps.latte.data.api.UserManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by twocity on 14-6-12.
 */
@Module(
    complete = false,
    library = true)

public class UserModule {
  @Provides @Singleton UserManager provideUserManager(Application app) {
    return new UserManager(app);
  }
}
