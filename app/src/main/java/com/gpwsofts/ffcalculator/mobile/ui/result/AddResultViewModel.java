package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.repository.AddResultRepository;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;
import com.gpwsofts.ffcalculator.mobile.services.result.IResultService;
import com.gpwsofts.ffcalculator.mobile.services.result.ResultResponse;

import java.util.List;

/**
 * @since 1.0.0
 * ViewModel propre au fragment d'ajout de resultat
 */
public class AddResultViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "AddResultViewModel";
    //private final GridRepository gridRepository;
    private final IGridService gridService;
    private final IResultService resultService;
    private AddResultRepository addResultRepository;

    private DatabaseResultRepository databaseResultRepository;


    public AddResultViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiation de AddResultViewModel");
        Log.d(TAG_NAME, "Instantiation si besoin d'un IGridService");
        gridService = FFCalculatorApplication.instance.getServicesManager().getGridService();
        resultService = FFCalculatorApplication.instance.getServicesManager().getResultService();
        addResultRepository = AddResultRepository.getInstance();
        databaseResultRepository = DatabaseResultRepository.getInstance();
        Log.i(TAG_NAME, "Fin instantiotion de AddResultViewModel");
    }

    public LiveData<List<Integer>> getPosChoices() {
        return gridService.getPosChoices();
    }

    public LiveData<List<Grid>> getGridsChoices() {
        return gridService.getGridChoices();
    }

    public LiveData<Result> getAddedResult(){
        return addResultRepository.getResult();
    }

    public void updatePosChoices(String itemValue) {
        gridService.loadPosChoicesAsynchronously(itemValue);
    }

    public void updateGridChoices(String vue) {
        gridService.loadGridChoicesAsynchronously(vue);
    }

    public void createNewResult(String place, String libelle, int pos, int prts){
        addResultRepository.addResultApi(place, libelle,pos,prts);
    }

    public void onNewResultCreated(Result newResult){
        databaseResultRepository.insert(newResult);
    }
}
