package com.twocity.apps.latte;

import com.twocity.apps.latte.data.api.OkClientFactory;
import com.twocity.apps.latte.data.api.RestAdapterFactory;
import com.twocity.apps.latte.data.api.UserManager;
import com.twocity.apps.latte.data.api.WeiboClient;

import android.app.Application;
import android.content.Context;

import retrofit.RestAdapter;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-12.
 */
public class LatteApp extends Application {

    private WeiboClient mClient;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // TODO
        }
        initWeiboClient();
    }

    public void initWeiboClient() {
        RestAdapter adapter = RestAdapterFactory
                .make(new UserManager(this), OkClientFactory.make(this));
        mClient = new WeiboClient(adapter);
    }

    public WeiboClient getApiClient() {
        return mClient;
    }

    public static LatteApp get(Context context) {
        return (LatteApp) context.getApplicationContext();
    }

}
