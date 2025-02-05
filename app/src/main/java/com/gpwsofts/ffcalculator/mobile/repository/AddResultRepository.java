package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.services.client.AddResultApiClient;

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

    public LiveData<Result> getAddedResultLiveData() {
        return addResultApiClient.getAddedResultLiveData();
    }

    public LiveData<String> getAddedResultMessageLiveData(){
        return addResultApiClient.getAddedResultMessageLiveData();
    }

    public void addResultApiAsync(String place, String libelle, String pos, String prts) {
        addResultApiClient.addResultApiAsync(place, libelle, pos, prts);
    }
}
