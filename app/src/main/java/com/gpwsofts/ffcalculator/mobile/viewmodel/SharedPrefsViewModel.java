package com.gpwsofts.ffcalculator.mobile.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.SharedPreferencesStringLiveData;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.ResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.SharedPrefsRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Un ViewModel s'appuyant sur les Shared Preferences
 * @since 1.0.0
 */
public class SharedPrefsViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "SharedPrefsViewModel";
    private SharedPreferencesStringLiveData vue;
    private SharedPrefsRepository repository;

    public SharedPrefsViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAG_NAME, "Construction du SharedPrefsViewModel");
        Log.i(TAG_NAME, "Instanciation du repository");
        repository = new SharedPrefsRepository(application);
        Log.i(TAG_NAME, "Recuperation de la vue en sharedPreferences");
        vue = repository.getVue();
        Log.i(TAG_NAME, "vue en sharedPreferences = <" + vue.getValue() + ">");
    }
    public void update(String inVue){
        Log.i(TAG_NAME, "demande de mise a jour du repository vers vue " + inVue);
        repository.updateVue(inVue);
    }

    public SharedPreferencesStringLiveData getVue() {
        return vue;
    }

}
