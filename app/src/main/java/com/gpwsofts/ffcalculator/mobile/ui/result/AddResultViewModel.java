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
    }

    public LiveData<List<String>> getClassesChoices() {
        if (null == classesChoices) {
            classesChoices = new MutableLiveData<List<String>>();
            loadClassesChoices();
        }
        return classesChoices;
    }

    public LiveData<List<Integer>> getPrtsChoices() {
        if (null == prtsChoices) {
            prtsChoices = new MutableLiveData<List<Integer>>();
            loadPrtsChoices();
        }
        return prtsChoices;
    }

    public LiveData<List<Integer>> getPosChoices() {
        if (null == posChoices) {
            posChoices = new MutableLiveData<List<Integer>>();
            loadPosChoices();
        }
        return posChoices;
    }

    private void loadClassesChoices() {
        //TODO 1.0.0 operations asynchrones pour le chargement de la liste déroulante des classes

    }

    private void loadPrtsChoices() {

    }

    private void loadPosChoices() {
        //TODO 1.0.0 operation asynchrones
    }
}
