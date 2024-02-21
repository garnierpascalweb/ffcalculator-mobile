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

    private MutableLiveData<String> selectedClasse;
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
        selectedClasse = new MutableLiveData<>();
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
            loadPosChoices(selectedClasse.getValue());
        }
        return posChoices;
    }

    private void loadClassesChoices() {
        classesChoices = gridRepository.getClassesChoices();
    }

    private void loadPosChoices(String code) {
        posChoices = gridRepository.getPosChoices(code);
    }

    public void refreshPosChoices(String code) {
        gridRepository.loadPosChoices(code);
    }

    private void loadPrtsChoices() {

    }

    public MutableLiveData<String> getSelectedClasse() {
        return selectedClasse;
    }

    public void setSelectedClasse(MutableLiveData<String> selectedClasse) {
        this.selectedClasse = selectedClasse;
    }
}
