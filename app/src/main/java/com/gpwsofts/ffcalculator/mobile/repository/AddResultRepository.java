package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.services.pts.api.response.AddResultRunnableResponse;
import com.gpwsofts.ffcalculator.mobile.services.pts.api.AddResultApiClient;

/**
 * Repository pour l'ajout d'un nouveau resultat
 * Basé sur l'API Cliente AddResultApiClient
 *
 * @since 1.0.0
 */
public class AddResultRepository {
    private static final String TAG_NAME = "AddResultRepository";
    private static AddResultRepository instance;
    private final AddResultApiClient addResultApiClient;

    private AddResultRepository() {
        addResultApiClient = AddResultApiClient.getInstance();
    }

    public static AddResultRepository getInstance() {
        if (null == instance)
            instance = new AddResultRepository();
        return instance;
    }

    public LiveData<AddResultRunnableResponse> getAddedResultLiveData() {
        return addResultApiClient.getAddedResultLiveData();
    }

    public void addResultApiAsync(String place, String libelle, String pos, String prts) {
        addResultApiClient.addResultApiAsync(place, libelle, pos, prts);
    }
}
