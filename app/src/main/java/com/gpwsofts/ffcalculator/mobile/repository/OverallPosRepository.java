package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.services.client.OverAllPosApiClient;

/**
 * Repository pour la position au classement national
 * Basé sur l'API Cliente OverAllPosApiClient
 *
 * @since 1.0.0
 */
public class OverallPosRepository {
    private static final String TAG_NAME = "OverallPosRepository";
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

    public LiveData<Integer> getPos() {
        return posApiClient.getPos();
    }

    public void searchPosApi(double pts, String classType) {
        posApiClient.searchPosApiAsync(pts, classType);
    }
}
