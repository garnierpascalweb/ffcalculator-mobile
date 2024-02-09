package com.gpwsofts.ffcalculator.mobile.livedata;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Implementation Live Data pour une data Int des sharedPreferences,
 * Concretement, la position au classement national symbolisé par la clé
 * SharedPreferencesConstants.KEY_POS
 * @since 1.0.0
 */
public class SharedPreferencesIntLiveData extends SharedPreferencesLiveData<Integer> {
    private static final String TAG_NAME = "SharedPreferencesIntLiveData";
    public SharedPreferencesIntLiveData(SharedPreferences prefs, String key, Integer defValue) {
        super(prefs, key, defValue);
    }
    @Override
    protected Integer getValueFromPreferences(String key, Integer defValue) {
        Log.i(TAG_NAME, "recuperation dans les shard prefs de key = <" + key + "> et defvalue = <" + defValue + ">");
        Integer valueFromPreferences = Integer.valueOf(sharedPrefs.getInt(key, 7000));
        Log.i(TAG_NAME, "lecture depuis les sharedPrefs = <" + valueFromPreferences + ">");
        return valueFromPreferences;
    }

}
