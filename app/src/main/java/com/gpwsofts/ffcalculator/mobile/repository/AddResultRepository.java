package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.services.client.AddResultApiClient;

/**
 * Repository pour l'ajout d'un nouveau resultat
 * Basé sur l'API Cliente AddResultApiClient
 * @since 1.0.0
 */
public class AddResultRepository {
    private static final String TAG_NAME = "AddResultRepository";
    private static AddResultRepository instance;
    private final AddResultApiClient addResultApiClient;
    public static AddResultRepository getInstance(){
        if (null == instance)
            instance = new AddResultRepository();
        return instance;
    }
    private AddResultRepository(){
        addResultApiClient = AddResultApiClient.getInstance();
    }
    public LiveData<Result> getResult(){
        return addResultApiClient.getResult();
    }
    public void addResultApi(String place, String libelle, int pos, int prts){
        addResultApiClient.addResultApi(place,libelle,pos,prts);
    }
}
