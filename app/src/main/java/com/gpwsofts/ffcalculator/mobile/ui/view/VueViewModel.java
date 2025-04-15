package com.gpwsofts.ffcalculator.mobile.ui.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.repository.VueRepository;

public class VueViewModel extends ViewModel {
    private final VueRepository vueRepository;
    private String currentCodeVue;

    public VueViewModel() {
        vueRepository = VueRepository.getInstance();
    }

    public LiveData<Vue> getVueLiveData() {
        return vueRepository.getVueLiveData();
    }

    public void loadVueAsync(){
        vueRepository.loadVueAsync();
    }

    public void updateVueFromMenuAsync(int itemId){
        vueRepository.updateVueFromMenuAsync(itemId);
    }

    public String getCurrentCodeVue() {
        return currentCodeVue;
    }

    public void setCurrentCodeVue(String currentCodeVue) {
        this.currentCodeVue = currentCodeVue;
    }
}
