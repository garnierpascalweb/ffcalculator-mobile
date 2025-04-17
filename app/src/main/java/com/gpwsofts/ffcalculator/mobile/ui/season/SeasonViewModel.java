package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.OverallPosRepository;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;

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
        LogUtils.i(TAG_NAME, "Instantiation de SeasonViewModel");
        databaseResultRepository = DatabaseResultRepository.getInstance();
        posRepository = OverallPosRepository.getInstance();
        currentPts = 0.0;
        currentPos = 0;
        currentClassType = "";
        LogUtils.i(TAG_NAME, "Fin instantiation de SeasonViewModel");
    }

    public LiveData<Double> getAllPtsLiveData() {
        return databaseResultRepository.getAllPtsLiveData();
    }

    public void delete(Result result) {
        databaseResultRepository.delete(result);
    }

    public LiveData<List<Result>> getAllResultsLiveData() {
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
