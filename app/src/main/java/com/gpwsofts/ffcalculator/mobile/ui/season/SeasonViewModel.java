package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.ResultRepository;

import java.util.List;

/**
 * ViewModel pour les resultats
 *
 * @since 1.0.0
 */
public class SeasonViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "ResultViewModel";
    private ResultRepository repository;
    private LiveData<List<Result>> allResults = new MutableLiveData();

    public SeasonViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Construction du ResultViewModel");
        Log.i(TAG_NAME, "Instanciation du repository");
        repository = new ResultRepository(application);
        Log.i(TAG_NAME, "Recuperation de tous les resultats");
        allResults = repository.getAllResults();
        if (null == allResults)
            Log.w(TAG_NAME, "La liste des resultats est null");
        //allResults.observe();
    }

    public void insert(Result result) {
        Log.i(TAG_NAME, "Insertion d un nouveau resultat");
        repository.insert(result);
    }

    public void update(Result result) {
        Log.i(TAG_NAME, "Mise a jour resultat");
        repository.update(result);
    }

    public void delete(Result result) {
        Log.i(TAG_NAME, "Suppression d un resultat");
        repository.delete(result);
    }

    public void deleteAll() {
        Log.i(TAG_NAME, "Suppression de tous les resultats");
        repository.deleteAll();
    }

    public LiveData<List<Result>> getAllResults() {
        Log.i(TAG_NAME, "Recuperation de la liste de tous les resultats");
        if (null == allResults)
            Log.w(TAG_NAME, "La liste des resultats est null");
        return allResults;
    }
}
