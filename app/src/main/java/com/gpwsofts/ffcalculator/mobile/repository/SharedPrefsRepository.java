package com.gpwsofts.ffcalculator.mobile.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.constants.SharedPreferencesConstants;
import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesStringLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Un repository pour les shard preferences
 *
 * @since 1.0.0
 */
public class SharedPrefsRepository {
    public static final String SHARED_PREFS_APP_NAME = "FFCalculatorSharedPrefs";
    private static final String TAG_NAME = "SharedPrefsRepository";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService sharedPrefsWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private SharedPreferencesStringLiveData vue;

    public SharedPrefsRepository() {
        //TODO 1.0.0 sur de toi avec le context ?
        sharedPrefs = FFCalculatorApplication.instance.getApplicationContext().getSharedPreferences(SHARED_PREFS_APP_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        vue = new SharedPreferencesStringLiveData(sharedPrefs, SharedPreferencesConstants.KEY_VUE, SharedPreferencesConstants.DEFAULT_VALUE_VUE);
    }

    public SharedPreferencesStringLiveData getVue() {
        return vue;
    }

    //TODO 1.0.0 pour tous les update, faire de l'asynchrone et bien faire les commit
    public void updateVue(String inVue) {
        sharedPrefsWriteExecutor.execute(() -> {
            Log.i(TAG_NAME, "Mise a jour Shared Preferences <" + SharedPreferencesConstants.KEY_VUE + "> valeur = <" + inVue + ">");
            sharedPrefsEditor.putString(SharedPreferencesConstants.KEY_VUE, inVue);
            sharedPrefsEditor.commit();
        });
    }
}
