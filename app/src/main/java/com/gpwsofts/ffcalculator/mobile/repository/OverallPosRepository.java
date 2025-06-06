package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.services.pos.api.OverAllPosApiClient;

/**
 * Repository pour la position au classement national
 * Basé sur l'API Cliente OverAllPosApiClient
 *
 * @since 1.0.0
 */
public class OverallPosRepository {
    private static OverallPosRepository instance;
    private final OverAllPosApiClient posApiClient;

    private OverallPosRepository() {
        posApiClient = OverAllPosApiClient.getInstance();
    }

    public static OverallPosRepository getInstance() {
        if (null == instance)
            instance = new OverallPosRepository();
        return instance;
    }

    public LiveData<Integer> getOverAllPosLiveData() {
        return posApiClient.getOverAllPosLiveData();
    }

    public void searchPosApi(double pts, String classType) {
        posApiClient.searchPosApiAsync(pts, classType);
    }
}
