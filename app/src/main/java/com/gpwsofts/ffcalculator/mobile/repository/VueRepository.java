package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.services.vue.api.VueApiClient;

/**
 * Repository pour la vue en cours
 * Branché sur l'API Cliente VueApiClient
 */
public class VueRepository {
    private static VueRepository instance;

    private final VueApiClient vueApiClient;

    private VueRepository() {
        vueApiClient = VueApiClient.getInstance();
    }

    public static VueRepository getInstance() {
        if (null == instance)
            instance = new VueRepository();
        return instance;
    }

    public LiveData<String> getCodeVueLiveData() {
        return vueApiClient.getCodeVueLiveData();
    }

    public void loadCodeVueAsync(){
        vueApiClient.loadCodeVueAsync();
    }

    public void updateCodeVueAsync(String codeVue){
        vueApiClient.updateCodeVueAsync(codeVue);
    }
}
