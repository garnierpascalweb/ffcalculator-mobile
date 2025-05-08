package com.gpwsofts.ffcalculator.mobile.ui.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.repository.VueRepository;

public class VueViewModel extends ViewModel {
    private final VueRepository vueRepository;
    private String currentCodeVue;

    public VueViewModel() {
        vueRepository = VueRepository.getInstance();
    }

    public LiveData<String> getCodeVueLiveData() {
        return vueRepository.getCodeVueLiveData();
    }

    public void loadCodeVueAsync(){
        vueRepository.loadCodeVueAsync();
    }

    public void updateCodeVueAsync(String codeVue){
        vueRepository.updateCodeVueAsync(codeVue);
    }

    public String getCurrentCodeVue() {
        return currentCodeVue;
    }

    public void setCurrentCodeVue(String currentCodeVue) {
        this.currentCodeVue = currentCodeVue;
    }
}
