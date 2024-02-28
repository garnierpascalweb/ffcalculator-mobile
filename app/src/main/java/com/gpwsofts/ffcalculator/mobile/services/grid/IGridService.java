package com.gpwsofts.ffcalculator.mobile.services.grid;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IGridService {
    public LiveData<List<String>> getClassesChoices();
    public LiveData<List<Integer>> getPosChoices();
    public LiveData<List<Integer>> getPrtsChoices();
    public void loadClassesChoicesAsynchronously(String vue);
    public void loadPosChoicesAsynchronously(String itemValue);

}
