package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.services.client.GridApiClient;

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

    public LiveData<List<Grid>> getGridsChoices() {
        return gridApiClient.getGridChoices();
    }

    public LiveData<List<Integer>> getPosChoices() {
        return gridApiClient.getPosChoices();
    }

    public void loadClassesChoices(String vue) {
        gridApiClient.loadClassesChoices(vue);
    }

    public void loadPosChoices(String libelle) {
        gridApiClient.loadPosChoices(libelle);
    }
}
