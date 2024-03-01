package com.gpwsofts.ffcalculator.mobile.services.result;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;

public interface IResultService {
    public void createResult(String place, String libelle, int pos, int prts);
    public LiveData<ResultResponse> getResultResponseLiveData();
}
