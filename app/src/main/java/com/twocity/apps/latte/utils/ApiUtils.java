package com.twocity.apps.latte.utils;

import android.os.Build;

/**
 * Created by twocity on 14-2-20.
 */
public class ApiUtils {

    private ApiUtils() {
    }

    public static boolean hasKK() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return true;
        }
        return false;
    }

}
