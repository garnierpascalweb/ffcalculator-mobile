package com.gpwsofts.ffcalculator.mobile.services.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.model.Vue;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @since 1.0.0
 * Api Cliente pour le définition asynchrone de la vue
 * Appelé par VueRepository
 */
public class VueApiClient {
    private static final String TAG_NAME = "VueApiClient";
    private static final String SHARED_PREFS_APP_NAME = "FFCalculatorSharedPrefs";
    private static final String KEY_VUE = "vue";
    private static final String DEFAULT_VUE_VALUE = "G";
    private static VueApiClient instance;
    private final SingleLiveEvent<Vue> mVue;
    private SetVueRunnable setVueRunnable;

    private VueApiClient() {
        Log.i(TAG_NAME, "instanciation de VueApiClient");
        mVue = new SingleLiveEvent<Vue>();
        Log.v(TAG_NAME, "recherche dans les shared prefs de la valeur de <" + KEY_VUE + ">");
        final String currentCodeVue = FFCalculatorApplication.instance.getSharedPrefs().getSharedPrefsVue();
        Log.v(TAG_NAME, "set en liveData instance Vue = <" + currentCodeVue + ">");
        final Vue currentVue = new Vue(currentCodeVue);
        mVue.setValue(currentVue);
        Log.i(TAG_NAME, "fin instanciation de VueApiClient");
    }

    public static VueApiClient getInstance() {
        if (null == instance)
            instance = new VueApiClient();
        return instance;
    }

    public LiveData<Vue> getVue() {
        return mVue;
    }

    public void setVueApiAsync(String vue) {
        if (setVueRunnable != null) {
            setVueRunnable = null;
        }
        setVueRunnable = new SetVueRunnable(vue);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(setVueRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            // annuler l'appel a l'API
            myHandler.cancel(true);
        }, 5000, TimeUnit.MILLISECONDS);
    }


    /**
     * @since 1.0.0
     * Job permettant de mettre a jour la vue en shared preferences
     */
    private class SetVueRunnable implements Runnable {
        private final String vue;
        boolean cancelRequest;

        public SetVueRunnable(String vue) {
            this.vue = vue;
        }

        @Override
        public void run() {
            Log.i(TAG_NAME, "debut du job asynchrone SetVueRunnable");
            Log.d(TAG_NAME, "envoi de la cle valeur en shared prefs - <" + KEY_VUE + "> <" + vue + ">");
            // sharedPrefsEditor.putString(KEY_VUE, vue);
            FFCalculatorApplication.instance.getSharedPrefs().setSharedPrefsVue(vue);
            final Vue newVue = new Vue(vue);
            Log.d(TAG_NAME, "post de la nouvelle vue en livedata = <" + vue + ">");
            mVue.postValue(newVue);
            Log.i(TAG_NAME, "fin du job asynchrone SetVueRunnable");
        }

        private void cancelRequest() {
            Log.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }
}
