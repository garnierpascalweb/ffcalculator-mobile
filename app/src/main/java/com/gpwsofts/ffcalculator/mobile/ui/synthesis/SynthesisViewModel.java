package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.repository.OverallPosRepository;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;

public class SynthesisViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "SynthesisViewModel";
    private final DatabaseResultRepository repository;
    private final OverallPosRepository posRepository;
    public SynthesisViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiation de SynthesisViewModel");
        repository = DatabaseResultRepository.getInstance();
        posRepository = OverallPosRepository.getInstance();
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
