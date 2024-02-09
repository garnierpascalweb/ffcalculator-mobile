package com.gpwsofts.ffcalculator.mobile.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.constants.SharedPreferencesConstants;
import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesDoubleLiveData;
import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesIntLiveData;
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

    /**
     * Par defaut, la vue générale
     * @since 1.0.0
     */

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    private SharedPreferencesStringLiveData vue;
    private SharedPreferencesDoubleLiveData pts;
    private SharedPreferencesIntLiveData pos;
    public SharedPrefsRepository(Application application){
        //TODO 1.0.0 sur de toi avec le context ?
        sharedPrefs = application.getApplicationContext().getSharedPreferences(SHARED_PREFS_APP_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        vue = new SharedPreferencesStringLiveData(sharedPrefs, SharedPreferencesConstants.KEY_VUE,SharedPreferencesConstants.DEFAULT_VALUE_VUE);
        //TODO 1.0.0 mettre des valeurs par defaut en constantes
        pts = new SharedPreferencesDoubleLiveData(sharedPrefs, SharedPreferencesConstants.KEY_PTS,0.0);
        pos = new SharedPreferencesIntLiveData(sharedPrefs, SharedPreferencesConstants.KEY_POS,7000);
    }

    public SharedPreferencesStringLiveData getVue() {
        return vue;
    }
    public SharedPreferencesDoubleLiveData getPts() {
        return pts;
    }
    public SharedPreferencesIntLiveData getPos() {
        return pos;
    }

    //TODO 1.0.0 pour tous les update, faire de l'asynchrone et bien faire les commit
    public void updateVue(String inVue){
        Log.i(TAG_NAME, "Mise a jour Shared Preferences <" + SharedPreferencesConstants.KEY_VUE + "> valeur = <" +  inVue + ">");
        sharedPrefsEditor.putString(SharedPreferencesConstants.KEY_VUE, inVue);
        sharedPrefsEditor.commit();
    }

    /**
     * Mise a jour des points en sharedPreferences
     * Devrait etre appelé a chaque fois que la liste des résultats change
     * @param pts
     */
    public void updatePts(double pts){
        Log.i(TAG_NAME, "Mise a jour Shared Preferences <" + SharedPreferencesConstants.KEY_PTS + "> valeur = <" +  pts + ">");
        sharedPrefsEditor.putFloat(SharedPreferencesConstants.KEY_PTS, Double.valueOf(pts).floatValue());
        sharedPrefsEditor.commit();
    }
    public void updatePos(int pos){
        Log.i(TAG_NAME, "Mise a jour Shared Preferences <" + SharedPreferencesConstants.KEY_POS + "> valeur = <" +  pos + ">");
        sharedPrefsEditor.putInt(SharedPreferencesConstants.KEY_POS, Integer.valueOf(pos));
        sharedPrefsEditor.commit();
    }

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
