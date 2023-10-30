package com.gpwsofts.ffcalculator.mobile.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.model.IResult;
import com.gpwsofts.ffcalculator.mobile.model.Result;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {


    private final MutableLiveData<List<IResult>> result;

    public DashboardViewModel() {
        result = new MutableLiveData<List<IResult>>();
        IResult mockResult1 = new Result();
        mockResult1.setPlace("Eguzon Chantome");
        mockResult1.setPts(123.56);
        mockResult1.setPrts(177);
        mockResult1.setPos(6);
        mockResult1.setLibelle("Open 2/3");
        mockResult1.setIdClasse("1.25.1");
        IResult mockResult2 = new Result();
        mockResult2.setPlace("Ecueille");
        mockResult2.setPts(12.30);
        mockResult2.setPrts(86);
        mockResult2.setPos(9);
        mockResult2.setLibelle("Open 2/3");
        mockResult2.setIdClasse("1.25.1");
        IResult mockResult3 = new Result();
        mockResult3.setPlace("Mouhet");
        mockResult3.setPts(41.23);
        mockResult3.setPrts(133);
        mockResult3.setPos(2);
        mockResult3.setLibelle("Open 2/3");
        mockResult3.setIdClasse("1.25.1");
        List<IResult> listResult = new ArrayList<IResult>(15);
        listResult.add(mockResult1);
        listResult.add(mockResult2);
        listResult.add(mockResult3);
        result.setValue(listResult);
    }

    public LiveData<List<IResult>> getResult() {
        return result;
    }
}