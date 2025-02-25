package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

public class SynthesisViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "SynthesisViewModel";

    public SynthesisViewModel(Application application) {
        super(application);
        LogUtils.i(TAG_NAME, "Instantiation de SynthesisViewModel");
        LogUtils.i(TAG_NAME, "Fin instantiation de SynthesisViewModel");
    }
}
