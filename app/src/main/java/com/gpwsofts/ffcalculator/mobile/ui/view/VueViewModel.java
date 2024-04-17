package com.gpwsofts.ffcalculator.mobile.ui.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.repository.VueRepository;

public class VueViewModel extends ViewModel {
    private final VueRepository vueRepository;
    public VueViewModel(){
        vueRepository = VueRepository.getInstance();
    }
    public LiveData<Vue> getVueLiveData(){
        return vueRepository.getVue();
    }
    public void updateVue(String vue){
        vueRepository.setVueApi(vue);
    }
}
