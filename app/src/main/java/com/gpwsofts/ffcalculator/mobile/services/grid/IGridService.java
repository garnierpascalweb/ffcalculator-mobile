package com.gpwsofts.ffcalculator.mobile.services.grid;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.model.Grid;

import java.util.List;

public interface IGridService {
    public LiveData<List<Integer>> getPosChoices();

    public LiveData<List<Grid>> getGridChoices();

    public void loadPosChoicesAsynchronously(String itemValue);

    public void loadGridChoicesAsynchronously(String vue);

}
