package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.PosRepository;
import com.gpwsofts.ffcalculator.mobile.repository.ResultRepository;

import java.util.List;

public class SynthesisViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "SynthesisViewModel";
    private ResultRepository repository;
    private PosRepository posRepository;
    public SynthesisViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiation de SynthesisViewModel");
        repository = new ResultRepository(application);
        posRepository = PosRepository.getInstance();
        Log.i(TAG_NAME, "Fin instantiation de SynthesisViewModel");
    }
    public LiveData<Double> getPts(){
        return repository.getPts();
    }

    public LiveData<Integer> getPos(){
        return posRepository.getPos();
    }
    public void searchPosApi(double pts, String classType){
        posRepository.searchPosApi(pts, classType);
    }

}
