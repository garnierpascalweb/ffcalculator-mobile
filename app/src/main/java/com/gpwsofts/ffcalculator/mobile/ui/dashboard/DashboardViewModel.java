package com.gpwsofts.ffcalculator.mobile.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.model.IResult;
import com.gpwsofts.ffcalculator.mobile.model.Result;

public class DashboardViewModel extends ViewModel {


    private final MutableLiveData<IResult> result;

    public DashboardViewModel() {
        result = new MutableLiveData<>();
        IResult mockResult = new Result();
        mockResult.setPts(123.56);
        mockResult.setPrts(177);
        mockResult.setPos(6);
        mockResult.setLibelle("Open 2/3");
        mockResult.setIdClasse("1.25.1");
        result.setValue(mockResult);
    }

    public LiveData<IResult> getResult() {
        return result;
    }
}