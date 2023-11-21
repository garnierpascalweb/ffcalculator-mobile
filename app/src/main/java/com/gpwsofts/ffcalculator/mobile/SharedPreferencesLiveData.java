package com.gpwsofts.ffcalculator.mobile;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

/**
 * Implementation LiveData de Shared Preferences
 * Source https://stackoverflow.com/questions/50649014/livedata-with-shared-preferences
 * @since 1.0.0
 * @param <T>
 */
public abstract class SharedPreferencesLiveData<T> extends LiveData<T> {
    SharedPreferences sharedPrefs;
    String key;
    public T defValue;
    public SharedPreferencesLiveData(SharedPreferences prefs, String key, T defValue) {
        this.sharedPrefs = prefs;
        this.key = key;
        this.defValue = defValue;
    }
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (SharedPreferencesLiveData.this.key.equals(key)) {
                setValue(getValueFromPreferences(key, defValue));
            }
        }
    };
    abstract T getValueFromPreferences(String key, T defValue);
    @Override
    protected void onActive() {
        super.onActive();
        setValue(getValueFromPreferences(key, defValue));
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
    @Override
    protected void onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
        super.onInactive();
    }
    public SharedPreferencesLiveData<String> getStringLiveData(String key, String defaultValue) {
        return new SharedPreferencesStringLiveData(sharedPrefs,key, defaultValue);
    }
}
