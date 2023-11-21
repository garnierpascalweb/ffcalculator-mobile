package com.gpwsofts.ffcalculator.mobile;

import android.content.SharedPreferences;

/**
 * Implementation LiveData de Shared Preferences
 * Source https://stackoverflow.com/questions/50649014/livedata-with-shared-preferences
 * @since 1.0.0
 */
public class SharedPreferencesStringLiveData extends SharedPreferencesLiveData<String>{
    public SharedPreferencesStringLiveData(SharedPreferences prefs, String key, String defValue) {
        super(prefs, key, defValue);
    }
    @Override
    String getValueFromPreferences(String key, String defValue) {
        return sharedPrefs.getString(key, defValue);
    }
}
