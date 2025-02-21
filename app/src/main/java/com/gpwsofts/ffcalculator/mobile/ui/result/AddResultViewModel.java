package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.repository.AddResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.GridRepository;
import com.gpwsofts.ffcalculator.mobile.repository.TownRepository;

import java.util.List;

/**
 * @since 1.0.0
 * ViewModel propre au fragment d'ajout de resultat
 */
public class AddResultViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "AddResultViewModel";
    private final AddResultRepository addResultRepository;
    private final DatabaseResultRepository databaseResultRepository;
    private final GridRepository gridRepository;
    private final TownRepository townRepository;


    public AddResultViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiation de AddResultViewModel");
        addResultRepository = AddResultRepository.getInstance();
        databaseResultRepository = DatabaseResultRepository.getInstance();
        gridRepository = GridRepository.getInstance();
        townRepository = TownRepository.getInstance();
        Log.i(TAG_NAME, "Fin Instantiation de AddResultViewModel");
    }

    public LiveData<List<Integer>> getPosChoicesLiveData() {
        return gridRepository.getPosChoicesLiveData();
    }

    public LiveData<List<Grid>> getGridChoicesLiveData() {
        return gridRepository.getGridChoicesLiveData();
    }

    public LiveData<List<String>> getTownChoicesLiveData() {
        return townRepository.getTownChoicesLiveData();
    }

    public LiveData<Result> getAddedResultLiveData() {
        return addResultRepository.getAddedResultLiveData();
    }
    public LiveData<String> getAddedResultMessageLiveData(){
        return addResultRepository.getAddedResultMessageLiveData();
    }


    public void loadPosChoicesAsync(String itemValue) {
        gridRepository.loadPosChoicesAsync(itemValue);
    }

    public void loadGridChoicesAsync(Vue vue) {
        gridRepository.loadGridChoicesAsync(vue.getCode());
    }

    public void loadTownChoicesAsync() {
        townRepository.loadTownChoicesAsync();
    }

    /**
     * Declenche la creation d'un nouveau resultat
     * @param place
     * @param libelle
     * @param pos
     * @param prts
     */
    public void addResultApiAsync(String place, String libelle, String pos, String prts) {
        addResultRepository.addResultApiAsync(place, libelle, pos, prts);
    }

    public void onNewResultCreated(Result newResult) {
        databaseResultRepository.insert(newResult);
    }
}
