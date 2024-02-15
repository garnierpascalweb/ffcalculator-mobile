package com.gpwsofts.ffcalculator.mobile.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.constants.SharedPreferencesConstants;
import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesStringLiveData;

/**
 * Un repository pour les shard preferences
 * @since 1.0.0
 */
public class SharedPrefsRepository {
    private static final String TAG_NAME = "SharedPrefsRepository";
    public static final String SHARED_PREFS_APP_NAME = "FFCalculatorSharedPrefs";

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private SharedPreferencesStringLiveData vue;
    public SharedPrefsRepository(){
        //TODO 1.0.0 sur de toi avec le context ?
        sharedPrefs = FFCalculatorApplication.instance.getApplicationContext().getSharedPreferences(SHARED_PREFS_APP_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        vue = new SharedPreferencesStringLiveData(sharedPrefs, SharedPreferencesConstants.KEY_VUE,SharedPreferencesConstants.DEFAULT_VALUE_VUE);
    }
    public SharedPreferencesStringLiveData getVue() {
        return vue;
    }

    //TODO 1.0.0 pour tous les update, faire de l'asynchrone et bien faire les commit
    public void updateVue(String inVue){
        Log.i(TAG_NAME, "Mise a jour Shared Preferences <" + SharedPreferencesConstants.KEY_VUE + "> valeur = <" +  inVue + ">");
        sharedPrefsEditor.putString(SharedPreferencesConstants.KEY_VUE, inVue);
        sharedPrefsEditor.commit();
    }
    //TODO 1.0.0 mettre cette grosse merde en executor
    private static class UpdateVueAsyncTask extends AsyncTask<String, Void, Void> {
        // private ResultDao resultDao;
        private SharedPreferences.Editor sharedPrefsEditor;
        private UpdateVueAsyncTask(SharedPreferences.Editor sharedPrefsEditor) {
            this.sharedPrefsEditor = sharedPrefsEditor;
        }
        @Override
        protected Void doInBackground(String... vue) {
            Log.i(TAG_NAME, "SharedPrefsEditor : put dans " + SharedPreferencesConstants.KEY_VUE + " de va valeur " + vue[0]);
            sharedPrefsEditor.putString(SharedPreferencesConstants.KEY_VUE, vue[0]);
            return null;
        }
    }
}
