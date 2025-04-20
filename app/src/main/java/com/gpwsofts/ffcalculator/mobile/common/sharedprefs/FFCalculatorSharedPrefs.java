package com.gpwsofts.ffcalculator.mobile.common.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;

import java.util.UUID;

/**
 * @since 1.0.0
 * Centralisation des shared Preferences
 */
public class FFCalculatorSharedPrefs {
    private static final String TAG_NAME = "FFCalculatorSharedPrefs";
    private static final String SHARED_PREFS_APP_NAME = "FFCalculatorSharedPrefs";
    private static final String KEY_VUE = "vue";
    private static final String KEY_PREF_UNIQUE_ID = "pref_unique_id";
    private static final String DEFAULT_VALUE_VUE = "G";
    private static SharedPreferences sharedPrefs = null;
    private static SharedPreferences.Editor sharedPrefsEditor = null;
    private static String uniqueID = null;

    public FFCalculatorSharedPrefs(FFCalculatorApplication application) {
        if (application.isSharedPreferencesAlreadyExist()) {
            throw new ExceptionInInitializerError("FFCalculatorSharedPrefs deja instancié pour FFCalculatorApplication");
        }
        sharedPrefs = application.getApplicationContext().getSharedPreferences(SHARED_PREFS_APP_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        id();
    }

    public synchronized static String id() {
        if (uniqueID == null) {
            uniqueID = sharedPrefs.getString(KEY_PREF_UNIQUE_ID, null);
            if (null == uniqueID) {
                uniqueID = UUID.randomUUID().toString();
                sharedPrefsEditor.putString(KEY_PREF_UNIQUE_ID, uniqueID);
                final boolean status = sharedPrefsEditor.commit();
                LogUtils.i(TAG_NAME, "affectation <" + KEY_PREF_UNIQUE_ID + "> = <" + uniqueID + "> - status = <" + status + ">");
            }
        }
        return uniqueID;
    }

    public String getSharedPrefsVue() {
        return sharedPrefs.getString(KEY_VUE, DEFAULT_VALUE_VUE);
    }

    public boolean setSharedPrefsVue(String codeVue){
        sharedPrefsEditor.putString(KEY_VUE, codeVue);
        final boolean status = sharedPrefsEditor.commit();
        LogUtils.i(TAG_NAME, "affectation <" + KEY_VUE + "> = <" + codeVue + "> - status = <" + status + ">");
        return status;
    }
}
