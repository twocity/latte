package com.twocity.apps.latte.utils;

import java.io.IOException;
import java.io.InputStream;

import timber.log.Timber;

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
            Timber.e(e, "parse long error");
        }
        return defaultValue;
    }

    public static int parseIntSafely(String value, int defaultValue) {
        try {
            return Integer.valueOf(value)
        } catch (NumberFormatException e) {
            Timber.e(e, "parse int error");
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
