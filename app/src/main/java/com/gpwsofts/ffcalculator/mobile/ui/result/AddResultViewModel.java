package com.gpwsofts.ffcalculator.mobile.ui.result;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.repository.ResultRepository;
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

    private ResultRepository resultRepository;
    /**
     * Un message toast
     */
    private LiveData<String> toastMessage;

    private LiveData<ResultResponse> resultResponse;

    public AddResultViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiation de AddResultViewModel");
        resultRepository = new ResultRepository(FFCalculatorApplication.instance);
        Log.d(TAG_NAME, "Instantiation si besoin d'un IGridService");
        gridService = FFCalculatorApplication.instance.getServicesManager().getGridService();
        resultService = FFCalculatorApplication.instance.getServicesManager().getResultService();
        Log.d(TAG_NAME, "Branchement des livedata ");
        toastMessage = new MutableLiveData<String>();
        resultResponse = new MutableLiveData<>();

        Log.i(TAG_NAME, "Fin instantiotion de AddResultViewModel");
    }

    public LiveData<List<Integer>> getPosChoices() {
        return gridService.getPosChoices();
    }

    public LiveData<List<Grid>> getGridsChoices() {
        return gridService.getGridChoices();
    }
    public LiveData<ResultResponse> getResultResponse() {
        return resultService.getResultResponseLiveData();
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }



    public void updatePosChoices(String itemValue) {
        gridService.loadPosChoicesAsynchronously(itemValue);
    }

    public void updateGridChoices(String vue) {
        gridService.loadGridChoicesAsynchronously(vue);
    }

    public void updateToastMessage(String message) {
        ((MutableLiveData) toastMessage).postValue(message);
    }

    public void createNewResult(String place, String libelle, int pos, int prts){
        resultService.createResult(place, libelle, pos, prts);
    }



}
