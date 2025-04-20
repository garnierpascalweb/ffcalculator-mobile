package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;
import com.gpwsofts.ffcalculator.mobile.model.vue.IVue;
import com.gpwsofts.ffcalculator.mobile.repository.AddResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.GridRepository;
import com.gpwsofts.ffcalculator.mobile.repository.TownRepository;
import com.gpwsofts.ffcalculator.mobile.services.pts.api.response.AddResultRunnableResponse;

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
    private final AddResultRepository addResultRepository;
    private final DatabaseResultRepository databaseResultRepository;
    private final GridRepository gridRepository;
    private final TownRepository townRepository;
    private List<String> currentListTowns;

    public AddResultViewModel(Application application) {
        super(application);
        addResultRepository = AddResultRepository.getInstance();
        databaseResultRepository = DatabaseResultRepository.getInstance();
        gridRepository = GridRepository.getInstance();
        townRepository = TownRepository.getInstance();
        currentListTowns = new ArrayList<>();
    }

    public LiveData<List<Integer>> getPosChoicesLiveData() {
        return gridRepository.getPosChoicesLiveData();
    }

    public LiveData<List<IGrid>> getGridChoicesLiveData() {
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

    public void loadGridChoicesAsync(IVue vue) {
        gridRepository.loadGridChoicesAsync(vue.getCode());
    }

    public void loadTownChoicesAsync() {
        townRepository.loadTownChoicesAsync();
    }

    /**
     * Declenche la creation d'un nouveau resultat
     * @param place lieu de la course
     * @param libelle libelle de la course
     * @param pos position obtenue
     * @param prts nombre de partants
     */
    public void addResultApiAsync(String place, String libelle, String pos, String prts) {
        addResultRepository.addResultApiAsync(place, libelle, pos, prts);
    }

    public void onNewResultCreated(Result newResult) {
        databaseResultRepository.insert(newResult);
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
