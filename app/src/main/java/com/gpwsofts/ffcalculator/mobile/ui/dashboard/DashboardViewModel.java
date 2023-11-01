package com.gpwsofts.ffcalculator.mobile.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.model.IResult;
import com.gpwsofts.ffcalculator.mobile.model.Result;
import com.gpwsofts.ffcalculator.mobile.services.season.ISeasonService;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {

    private static final String TAG_NAME = "DashboardViewModel";
    private final MutableLiveData<List<IResult>> result;
    private final ISeasonService service;

    public DashboardViewModel() {
        service = FFCalculatorApplication.instance.getServicesManager().getSeasonService();
        result = new MutableLiveData<List<IResult>>();
        result.setValue(service.getResults());
        Log.d(TAG_NAME, "recuperation des resultats depuis le service soit <" + result.getValue().size() + ">");

    }
    public LiveData<List<IResult>> getResult() {
        return result;
    }
}