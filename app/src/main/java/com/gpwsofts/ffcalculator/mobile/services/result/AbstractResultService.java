package com.gpwsofts.ffcalculator.mobile.services.result;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;

public abstract class AbstractResultService implements IResultService {
    protected MutableLiveData<ResultResponse> resultResponseLiveData;
    protected DatabaseResultRepository resultRepository;

    public AbstractResultService(){
        resultResponseLiveData = new MutableLiveData<ResultResponse>();
        resultRepository = DatabaseResultRepository.getInstance();
    }

    public LiveData<ResultResponse> getResultResponseLiveData() {
        return resultResponseLiveData;
    }

}
