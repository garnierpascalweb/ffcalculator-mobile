package com.gpwsofts.ffcalculator.mobile.services.vues;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.services.result.ResultResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SimpleVueService implements IVueService {
    private static final String TAG_NAME = "SimpleVueService";
    private static final String KEY_VUE = "vue";
    private static final String DEFAULT_VUE_VALUE = "G";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor sharedPrefsEditor;
    public static final String SHARED_PREFS_APP_NAME = "FFCalculatorSharedPrefs";
    private static final ExecutorService vueServiceExecutor = Executors.newFixedThreadPool(1);

    protected MutableLiveData<String> currentVueLiveData;

    public SimpleVueService() {
        currentVueLiveData = new MutableLiveData<>();
        sharedPrefs = FFCalculatorApplication.instance.getApplicationContext().getSharedPreferences(SHARED_PREFS_APP_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = sharedPrefs.edit();
        loadAsynchronously();
    }

    public LiveData<String> getCurrentVueLiveData() {
        return currentVueLiveData;
    }

    private void loadAsynchronously() {
        Log.i(TAG_NAME, "chargement asynchrone de la vue en shared preferences");
        vueServiceExecutor.execute(() -> {
            String currentVue = sharedPrefs.getString(KEY_VUE, DEFAULT_VUE_VALUE);
            Log.d(TAG_NAME, "vue chargee = <" + currentVue + ">");
            Log.d(TAG_NAME, "postValue");
            currentVueLiveData.postValue(currentVue);
        });
    }
    @Override
    public void changeVueAsynchronously(String vue) {
        Log.i(TAG_NAME, "mise a jour asynchrone de la vue en shared preferences = <" + vue + ">");
        vueServiceExecutor.execute( () -> {
            Log.d(TAG_NAME, "envoi de la cle valeur en shared prefs - <" + KEY_VUE + "> <" + vue + ">");
            sharedPrefsEditor.putString(KEY_VUE, vue);
            Log.d(TAG_NAME, "commit");
            sharedPrefsEditor.commit();
            Log.d(TAG_NAME, "postValue");
            currentVueLiveData.postValue(vue);
        });
    }

    public int getIndexInMenu(String vue) {
        int indexToSelect = 0;
        switch (vue) {
            case ELITE: {
                indexToSelect = 4;
                break;
            }
            case OPEN_1: {
                indexToSelect = 3;
                break;
            }
            case OPEN_2: {
                indexToSelect = 2;
                break;
            }
            case OPEN_3: {
                indexToSelect = 1;
                break;
            }
            case U19: {
                indexToSelect = 5;
                break;
            }
            case U17: {
                indexToSelect = 6;
                break;
            }
            case ACCESS: {
                indexToSelect = 7;
                break;
            }
            default: {
                indexToSelect = 0;
            }
        }
        return indexToSelect;
    }
}
