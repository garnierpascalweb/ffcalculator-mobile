package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.OverallPosRepository;

import java.util.List;

/**
 * ViewModel pour les resultats
 *
 * @since 1.0.0
 */
public class SeasonViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "SeasonViewModel";
    private final DatabaseResultRepository databaseResultRepository;
    private final OverallPosRepository posRepository;
    private Double currentPts;
    private Integer currentPos;
    private String currentClassType;

    public SeasonViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiotion de SeasonViewModel");
        databaseResultRepository = DatabaseResultRepository.getInstance();
        posRepository = OverallPosRepository.getInstance();
        currentPts = Double.valueOf(0.0);
        currentPos = Integer.valueOf(0);
        currentClassType = new String();
        Log.i(TAG_NAME, "Fin instantiotion de SeasonViewModel");
    }

    public LiveData<Double> getAllPtsLiveData() {
        return databaseResultRepository.getAllPtsLiveData();
    }

    public void insert(Result result) {
        Log.i(TAG_NAME, "Insertion d un nouveau resultat");
        databaseResultRepository.insert(result);
    }

    public void update(Result result) {
        Log.i(TAG_NAME, "Mise a jour resultat");
        databaseResultRepository.update(result);
    }

    public void delete(Result result) {
        Log.i(TAG_NAME, "Suppression d un resultat");
        databaseResultRepository.delete(result);
    }

    public void deleteAll() {
        Log.i(TAG_NAME, "Suppression de tous les resultats");
        databaseResultRepository.deleteAll();
    }

    public LiveData<List<Result>> getAllResultsLiveData() {
        Log.i(TAG_NAME, "Recuperation de la liste de tous les resultats");
        return databaseResultRepository.getAllResultsLiveData();
    }

    public LiveData<Integer> getOverAllPosLiveData() {
        return posRepository.getOverAllPosLiveData();
    }

    public void searchPosApi(double pts, String classType) {
        posRepository.searchPosApi(pts, classType);
    }

    public Integer getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(Integer currentPos) {
        this.currentPos = currentPos;
    }

    public Double getCurrentPts() {
        return currentPts;
    }

    public void setCurrentPts(Double currentPts) {
        this.currentPts = currentPts;
    }

    public String getCurrentClassType() {
        return currentClassType;
    }

    public void setCurrentClassType(String currentClassType) {
        this.currentClassType = currentClassType;
    }
}
