package com.gpwsofts.ffcalculator.mobile.ui.shared;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesDoubleLiveData;
import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesIntLiveData;
import com.gpwsofts.ffcalculator.mobile.livedata.SharedPreferencesStringLiveData;
import com.gpwsofts.ffcalculator.mobile.repository.SharedPrefsRepository;

/**
 * Un ViewModel s'appuyant sur les Shared Preferences
 * @since 1.0.0
 */
public class SharedPrefsViewModel extends ViewModel {
    private static final String TAG_NAME = "SharedPrefsViewModel";
    private SharedPreferencesStringLiveData vue;

    private SharedPreferencesDoubleLiveData pts;

    private SharedPreferencesIntLiveData pos;
    private SharedPrefsRepository repository;

    public SharedPrefsViewModel() {
        Log.i(TAG_NAME, "Construction du SharedPrefsViewModel");
        Log.i(TAG_NAME, "Instanciation du repository");
        repository = new SharedPrefsRepository();
        Log.i(TAG_NAME, "Recuperation de la vue en sharedPreferences");
        vue = repository.getVue();
        Log.i(TAG_NAME, "vue en sharedPreferences = <" + vue.getValue() + ">");
        Log.i(TAG_NAME, "Recuperation des pts en sharedPreferences");
        pts = repository.getPts();
        Log.i(TAG_NAME, "pts en sharedPreferences = <" + pts.getValue() + ">");
        Log.i(TAG_NAME, "Recuperation de la pos en sharedPreferences");
        pos = repository.getPos();
        Log.i(TAG_NAME, "pos en sharedPreferences = <" + pos.getValue() + ">");
    }
    public void updateVue(String inVue){
        Log.i(TAG_NAME, "demande de mise a jour de la vue vers <" + inVue + ">");
        repository.updateVue(inVue);
    }

    public void updatePts(double inPts){
        Log.i(TAG_NAME, "demande de mise a jour des pts vers <" + inPts + ">");
        repository.updatePts(inPts);
    }

    public void updatePos(int inPos){
        Log.i(TAG_NAME, "demande de mise a jour des pts vers <" + inPos + ">");
        repository.updatePos(inPos);
    }

    public SharedPreferencesStringLiveData getVue() {
        return vue;
    }
    public SharedPreferencesIntLiveData getPos() {return pos;}
    public SharedPreferencesDoubleLiveData getPts(){return pts;}

}
