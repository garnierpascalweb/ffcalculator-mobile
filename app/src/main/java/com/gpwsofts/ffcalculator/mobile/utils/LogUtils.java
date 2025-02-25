package com.gpwsofts.ffcalculator.mobile.utils;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;

public class LogUtils {
    private static final boolean ENABLE_LOGS = BuildConfig.ENABLE_LOGS;

    public static void d(String tag, String message) {
        if (ENABLE_LOGS) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (ENABLE_LOGS) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable t) {
        if (ENABLE_LOGS) {
            Log.e(tag, message, t);
        }
    }

    public static void i(String tag, String message) {
        if (ENABLE_LOGS) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (ENABLE_LOGS) {
            Log.w(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (ENABLE_LOGS) {
            Log.v(tag, message);
        }
    }
}
