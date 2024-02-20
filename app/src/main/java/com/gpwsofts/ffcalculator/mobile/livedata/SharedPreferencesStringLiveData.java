package com.gpwsofts.ffcalculator.mobile.livedata;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Implementation LiveData de Shared Preferences
 * Source https://stackoverflow.com/questions/50649014/livedata-with-shared-preferences
 *
 * @since 1.0.0
 */
public class SharedPreferencesStringLiveData extends SharedPreferencesLiveData<String> {

    private static final String TAG_NAME = "SharedPreferencesStringLiveData";

    public SharedPreferencesStringLiveData(SharedPreferences prefs, String key, String defValue) {
        super(prefs, key, defValue);
    }

    @Override
    protected String getValueFromPreferences(String key, String defValue) {
        Log.i(TAG_NAME, "recuperation dans les shard prefs de key = <" + key + "> et defvalue = <" + defValue + ">");
        String valueFromPreferences = sharedPrefs.getString(key, defValue);
        Log.i(TAG_NAME, "lecture depuis les sharedPrefs = <" + valueFromPreferences + ">");
        return valueFromPreferences;
    }
}
