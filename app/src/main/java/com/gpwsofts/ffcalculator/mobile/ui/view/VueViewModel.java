package com.gpwsofts.ffcalculator.mobile.ui.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gpwsofts.ffcalculator.mobile.model.vue.IVue;
import com.gpwsofts.ffcalculator.mobile.repository.VueRepository;

public class VueViewModel extends ViewModel {
    private final VueRepository vueRepository;
    private String currentCodeVue;

    public VueViewModel() {
        vueRepository = VueRepository.getInstance();
    }

    public LiveData<IVue> getVueLiveData() {
        return vueRepository.getVueLiveData();
    }

    public void loadVueAsync(){
        vueRepository.loadVueAsync();
    }

    public void updateVueAsync(String codeVue){
        vueRepository.updateVueAsync(codeVue);
    }

    public String getCurrentCodeVue() {
        return currentCodeVue;
    }

    public void setCurrentCodeVue(String currentCodeVue) {
        this.currentCodeVue = currentCodeVue;
    }
}
