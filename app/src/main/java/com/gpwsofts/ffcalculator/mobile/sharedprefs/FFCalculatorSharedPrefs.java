package com.gpwsofts.ffcalculator.mobile.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
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
                sharedPrefsEditor.commit();
                Log.i(TAG_NAME, "ecriture uuid = <" + uniqueID + ">");
            }
        } else {
            Log.i(TAG_NAME, "lecture uuid = <" + uniqueID + ">");
        }
        Log.i(TAG_NAME, "renvoi uuid = <" + uniqueID + ">");
        return uniqueID;
    }

    public String getSharedPrefsVue() {
        return sharedPrefs.getString(KEY_VUE, DEFAULT_VALUE_VUE);
    }

    public void setSharedPrefsVue(String vue){
        sharedPrefsEditor.putString(KEY_VUE, vue);
        sharedPrefsEditor.commit();
    }
}
