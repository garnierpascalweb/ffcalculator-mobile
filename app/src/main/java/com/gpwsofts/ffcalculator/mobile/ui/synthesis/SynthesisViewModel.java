package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SynthesisViewModel extends ViewModel {
    private static final String TAG_NAME = "SynthesisViewModel";
    private final MutableLiveData<String> mText;

    public SynthesisViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}