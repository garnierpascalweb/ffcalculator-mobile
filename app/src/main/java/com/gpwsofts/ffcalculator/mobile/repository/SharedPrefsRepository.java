package com.gpwsofts.ffcalculator.mobile.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Un repository pour les shard preferences
 * @since 1.0.0
 */
public class SharedPrefsRepository {
    private static final String TAG_NAME = "SharedPrefsRepository";

    public static final String SHARED_PREFS_APP_NAME = "FFCalculatorSharedPrefs";
    /**
     * La cle de l'attribut sharedPrefs contenant la vue
     * @since 1.0.0
     */
    public static final String SHARED_PREFS_KEY_VUE = "vue";
    /**
     * Par defaut, la vue générale
     * @since 1.0.0
     */
    public static final String SHARED_PREFS_DEFAULT_VALUE = "G";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    public SharedPrefsRepository(Application application){
        //TODO 1.0.0 sur de toi avec le context ?
        sharedPrefs = application.getApplicationContext().getSharedPreferences(SHARED_PREFS_APP_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
    }

    /**
     * update de la vue en shared prefs
     * @param vue
     */
    public void setVue(String vue){
        sharedPrefsEditor.putString(SHARED_PREFS_KEY_VUE, vue);
        Log.i(TAG_NAME, "passage en vue <" + vue + ">");
    }

    /**
     * recuperation de la vue
     * @return la vue courante, par défaut G
     */
    public String getVue(){
        String vue = sharedPrefs.getString(SHARED_PREFS_KEY_VUE, SHARED_PREFS_DEFAULT_VALUE);
        Log.i(TAG_NAME, "recuperation de la vue courante <" + vue + ">");
        return vue;
    }


}
