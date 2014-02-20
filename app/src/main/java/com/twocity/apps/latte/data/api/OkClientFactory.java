package com.twocity.apps.latte.data.api;

import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;

import android.app.Application;

import java.io.File;
import java.io.IOException;

import retrofit.client.OkClient;
import timber.log.Timber;

/**
 * Created by twocity on 14-2-14.
 */
public class OkClientFactory {

    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private OkClientFactory() {
    }

    public static OkClient make(Application app) {
        OkHttpClient okHttpClient = provideOkHttpClient(app);
        return new OkClient(okHttpClient);
    }

    private static OkHttpClient provideOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();
        // Install an Http cache in the application cache directory
        try {
            File cacheDir = new File(app.getCacheDir(), "http");
            HttpResponseCache cache = new HttpResponseCache(cacheDir, DISK_CACHE_SIZE);
            client.setResponseCache(cache);
        } catch (IOException e) {
            Timber.e(e, "Unable to install disk cache to OKHttp.");
        }
        return client;
    }
}
