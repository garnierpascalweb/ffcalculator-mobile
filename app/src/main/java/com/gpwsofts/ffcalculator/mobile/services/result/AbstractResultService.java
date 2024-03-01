package com.gpwsofts.ffcalculator.mobile.services.result;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.ResultRepository;
import com.gpwsofts.ffcalculator.mobile.services.result.IResultService;

public abstract class AbstractResultService implements IResultService {
    protected MutableLiveData<ResultResponse> resultResponseLiveData;
    protected ResultRepository resultRepository;

    public AbstractResultService(){
        resultResponseLiveData = new MutableLiveData<ResultResponse>();
        resultRepository = new ResultRepository(FFCalculatorApplication.instance);
    }

    public LiveData<ResultResponse> getResultResponseLiveData() {
        return resultResponseLiveData;
    }

}
