package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;
import com.gpwsofts.ffcalculator.mobile.services.grid.api.GridApiClient;

import java.util.List;

/**
 * Repository pour la manipulation des grilles
 */
public class GridRepository {
    private static final String TAG_NAME = "GridRepository";
    private static GridRepository instance;
    private final GridApiClient gridApiClient;

    private GridRepository() {
        gridApiClient = GridApiClient.getInstance();
    }

    public static GridRepository getInstance() {
        if (null == instance)
            instance = new GridRepository();
        return instance;
    }

    public LiveData<List<IGrid>> getGridChoicesLiveData() {
        return gridApiClient.getGridChoicesLiveData();
    }

    public LiveData<List<Integer>> getPosChoicesLiveData() {
        return gridApiClient.getPosChoicesLiveData();
    }

    public void loadGridChoicesAsync(String vue) {
        gridApiClient.loadGridChoicesAsync(vue);
    }

    public void loadPosChoicesAsync(String libelle) {
        gridApiClient.loadPosChoicesAsync(libelle);
    }
}
