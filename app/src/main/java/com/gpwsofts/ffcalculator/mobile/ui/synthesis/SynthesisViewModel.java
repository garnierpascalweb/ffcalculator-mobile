package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.OverallPosRepository;

public class SynthesisViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "SynthesisViewModel";

    public SynthesisViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiation de SynthesisViewModel");
        Log.i(TAG_NAME, "Fin instantiation de SynthesisViewModel");
    }
}
