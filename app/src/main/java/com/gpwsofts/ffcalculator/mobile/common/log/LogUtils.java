package com.gpwsofts.ffcalculator.mobile.common.log;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;

/**
 * Classe utilitaire pour centraliser les logs
 * @since 1.0.0
 */
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

    public static void w(String tag, String message, Throwable t) {
        if (ENABLE_LOGS) {
            Log.w(tag, message, t);
        }
    }

    public static void v(String tag, String message) {
        if (ENABLE_LOGS) {
            Log.v(tag, message);
        }
    }

    public static void onCreateBegin(String tagName){
        if (ENABLE_LOGS) {
            Log.v(tagName, "debut onCreate");
        }
    }

    public static void onCreateEnd(String tagName){
        if (ENABLE_LOGS) {
            Log.v(tagName, "fin onCreate");
        }
    }

    public static void onCreateViewBegin(String tagName){
        if (ENABLE_LOGS) {
            Log.v(tagName, "debut onCreateView");
        }
    }

    public static void onCreateViewEnd(String tagName){
        if (ENABLE_LOGS) {
            Log.v(tagName, "fin onCreateView");
        }
    }

    public static void onViewCreatedBegin(String tagName) {
        Log.v(tagName, "debut onViewCreated");
    }

    public static void onViewCreatedEnd(String tagName) {
        Log.v(tagName, "fin onViewCreated");
    }
}
