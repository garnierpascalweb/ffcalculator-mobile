package com.gpwsofts.ffcalculator.mobile.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;
import com.gpwsofts.ffcalculator.mobile.services.vues.IVueService;

public class VueViewModel extends ViewModel {
    private final IVueService vueService;
    public VueViewModel(){
        vueService = FFCalculatorApplication.instance.getServicesManager().getVueService();
    }

    public LiveData<String> getVueLiveData(){
        return vueService.getCurrentVueLiveData();
    }

    public void updateVue(String vue){
        vueService.changeVueAsynchronously(vue);
    }
}
