package com.gpwsofts.ffcalculator.mobile.livedata;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Implementation Live Data pour une data Double des sharedPreferences,
 * Concretement, la somme des points des 15 meilleurs resultats symbolisé par la clé
 * SharedPreferencesConstants.KEY_PTS
 * @since 1.0.0
 */
public class SharedPreferencesDoubleLiveData extends SharedPreferencesLiveData<Double> {
    private static final String TAG_NAME = "SharedPreferencesDoubleLiveData";
    public SharedPreferencesDoubleLiveData(SharedPreferences prefs, String key, Double defValue) {
        super(prefs, key, defValue);
    }
    @Override
    protected Double getValueFromPreferences(String key, Double defValue) {
        Log.i(TAG_NAME, "recuperation dans les shard prefs de key = <" + key + "> et defvalue = <" + defValue + ">");
        //TODO 1.0.0 valeur ape defaut  a revoir et ci dessous un peu merdique
        Double valueFromPreferences = Double.valueOf(Float.valueOf(sharedPrefs.getFloat(key, 0)).doubleValue());
        Log.i(TAG_NAME, "lecture depuis les sharedPrefs = <" + valueFromPreferences + ">");
        return valueFromPreferences;
    }{
}
