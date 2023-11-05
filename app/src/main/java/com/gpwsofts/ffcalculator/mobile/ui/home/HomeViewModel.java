package com.gpwsofts.ffcalculator.mobile.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.model.IResult;
import com.gpwsofts.ffcalculator.mobile.model.MockResult;
import com.gpwsofts.ffcalculator.mobile.services.result.IResultService;
import com.gpwsofts.ffcalculator.mobile.services.season.ISeasonService;

public class HomeViewModel extends ViewModel {

    private static final String TAG_NAME = "HomeViewModel";
    private final IResultService resultService;
    private final MutableLiveData<String> mText;

    private final MutableLiveData<IResult> mResult;

    public HomeViewModel() {
        resultService = FFCalculatorApplication.instance.getServicesManager().getResultService();
        double pts = resultService.getPts(6,215,"1.25.1");
        mResult = new MutableLiveData<>();
        //TODO 1.0.0 appel a un mock
        IResult mockResult = new MockResult();
        mResult.setValue(mockResult);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<IResult> getResult() {
        return mResult;
    }
}