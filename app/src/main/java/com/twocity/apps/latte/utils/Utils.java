package com.twocity.apps.latte.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by twocity on 14-2-20.
 */
public class Utils {

    private Utils() {
    }

    public static long parseLongSafely(String value, long defaultValue) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {

        }
        return defaultValue;
    }

    static void closeSafely(InputStream is) {
        if (is == null) {
            return;
        }
        try {
            is.close();
        } catch (IOException ignored) {
        }
    }
}
