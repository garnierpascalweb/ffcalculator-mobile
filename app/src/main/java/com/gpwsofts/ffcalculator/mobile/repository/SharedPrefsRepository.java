package com.gpwsofts.ffcalculator.mobile.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesStringLiveData;

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
    private SharedPreferencesStringLiveData vue;
    public SharedPrefsRepository(Application application){
        //TODO 1.0.0 sur de toi avec le context ?
        sharedPrefs = application.getApplicationContext().getSharedPreferences(SHARED_PREFS_APP_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        vue = new SharedPreferencesStringLiveData(sharedPrefs, SHARED_PREFS_KEY_VUE,SHARED_PREFS_DEFAULT_VALUE);
    }

    public SharedPreferencesStringLiveData getVue() {
        return vue;
    }

    public void updateVue(String inVue){
        // Log.i(TAG_NAME, "Lancement de Async Task UpdateVueAsyncTask");
        //new SharedPrefsRepository.UpdateVueAsyncTask(sharedPrefsEditor).execute(inVue);
        Log.i(TAG_NAME, "Envoi de la valeur <" + inVue + "> dans sharedPrefsEditor");
        sharedPrefsEditor.putString(SHARED_PREFS_KEY_VUE, inVue);
        sharedPrefsEditor.commit();
        //TODO 1.0.0 faire de l'asynchrone, bien faire le commit
    }

    private static class UpdateVueAsyncTask extends AsyncTask<String, Void, Void> {
        // private ResultDao resultDao;
        private SharedPreferences.Editor sharedPrefsEditor;
        private UpdateVueAsyncTask(SharedPreferences.Editor sharedPrefsEditor) {
            this.sharedPrefsEditor = sharedPrefsEditor;
        }
        @Override
        protected Void doInBackground(String... vue) {
            Log.i(TAG_NAME, "SharedPrefsEditor : put dans " + SHARED_PREFS_KEY_VUE + " de va valeur " + vue[0]);
            sharedPrefsEditor.putString(SHARED_PREFS_KEY_VUE, vue[0]);
            return null;
        }
    }
}
