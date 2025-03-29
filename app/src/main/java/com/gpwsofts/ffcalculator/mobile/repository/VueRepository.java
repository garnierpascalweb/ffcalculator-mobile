package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.services.client.VueApiClient;

/**
 * Repository pour la vue en cours
 * Branché sur l'API Cliente VueApiClient
 */
public class VueRepository {
    private static final String TAG_NAME = "VueRepository";
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

    public LiveData<Vue> getVueLiveData() {
        return vueApiClient.getVueLiveData();
    }

    public void setVueApi(String codeVue) {
        vueApiClient.setVueApiAsync(codeVue);
    }


}
