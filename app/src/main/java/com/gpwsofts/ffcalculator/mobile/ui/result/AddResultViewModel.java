package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.common.AddResultRunnableResponse;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.repository.AddResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.GridRepository;
import com.gpwsofts.ffcalculator.mobile.repository.TownRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @since 1.0.0
 * ViewModel propre au fragment d'ajout de resultat
 */
public class AddResultViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "AddResultViewModel";
    private static final List<Integer> INTEGER_LIST_1_200 = IntStream.rangeClosed(1, 200).boxed().collect(Collectors.toList());
    private static final List<Integer> INTEGER_LIST_1_50 = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
    private static final List<Grid> EMPTY_GRID_LIST = new ArrayList<Grid>();
    private static final String VIDE = "";
    private final AddResultRepository addResultRepository;
    private final DatabaseResultRepository databaseResultRepository;
    private final GridRepository gridRepository;
    private final TownRepository townRepository;
    private String currentListGridHelperText;
    private List<String> currentListTowns;



    public AddResultViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiation de AddResultViewModel");
        addResultRepository = AddResultRepository.getInstance();
        databaseResultRepository = DatabaseResultRepository.getInstance();
        gridRepository = GridRepository.getInstance();
        townRepository = TownRepository.getInstance();
        currentListTowns = new ArrayList<String>();
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

    public LiveData<AddResultRunnableResponse> getAddedResultLiveData() {
        return addResultRepository.getAddedResultLiveData();
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

    public List<Grid> getCurrentListGrid() {
        return gridRepository.getGridChoicesLiveData().getValue();
    }

    public List<String> getCurrentListTownsFromLiveData() {
        return townRepository.getTownChoicesLiveData().getValue();
    }

    public String getCurrentListGridHelperText() {
        return currentListGridHelperText;
    }

    public void setCurrentListGridHelperText(String currentListGridHelperText) {
        this.currentListGridHelperText = currentListGridHelperText;
    }

    public List<Integer> getIntegerList200(){
        return INTEGER_LIST_1_200;
    }

    public List<Integer> getIntegerList50(){
        return INTEGER_LIST_1_50;
    }

    public List<String> getCurrentListTowns() {
        return currentListTowns;
    }

    public void setCurrentListTowns(List<String> currentListTowns) {
        this.currentListTowns = currentListTowns;
    }
}
