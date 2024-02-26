package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.repository.GridRepository;

import java.util.List;

/**
 * @since 1.0.0
 * ViewModel propre au fragment d'ajout de resultat
 */
public class AddResultViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "AddResultViewModel";
    private final GridRepository gridRepository;
    /**
     * La liste deroulante des classes de course
     */
    private LiveData<List<String>> classesChoices;
    /**
     * La liste deroulante de la place obtenue
     */
    private LiveData<List<Integer>> posChoices;
    /**
     * La liste deroulante des partants
     */
    private LiveData<List<Integer>> prtsChoices;

    public AddResultViewModel(Application application) {
        super(application);
        gridRepository = new GridRepository(application);
        classesChoices = gridRepository.getClassesChoices();
        posChoices = gridRepository.getPosChoices();
        prtsChoices = gridRepository.getPrtsChoices();
    }

    public LiveData<List<String>> getClassesChoices() {
        return classesChoices;
    }

    public LiveData<List<Integer>> getPosChoices() {
        return posChoices;
    }

    public LiveData<List<Integer>> getPrtsChoices() {
        return prtsChoices;
    }

    public void updatePosChoices(String itemValue) {
        gridRepository.updatePosChoices(itemValue);
    }

    public void updateClassesChoices(String vue){
        gridRepository.updateClassesChoices(vue);
    }
}
