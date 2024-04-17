package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.DatabaseResultRepository;

import java.util.List;

/**
 * ViewModel pour les resultats
 *
 * @since 1.0.0
 */
public class SeasonViewModel extends AndroidViewModel {
    private static final String TAG_NAME = "ResultViewModel";
    private final DatabaseResultRepository databaseResultRepository;

    public SeasonViewModel(Application application) {
        super(application);
        Log.i(TAG_NAME, "Instantiotion de SeasonViewModel");
        databaseResultRepository = DatabaseResultRepository.getInstance();
        Log.i(TAG_NAME, "Fin instantiotion de SeasonViewModel");
    }

    public void insert(Result result) {
        Log.i(TAG_NAME, "Insertion d un nouveau resultat");
        databaseResultRepository.insert(result);
    }

    public void update(Result result) {
        Log.i(TAG_NAME, "Mise a jour resultat");
        databaseResultRepository.update(result);
    }

    public void delete(Result result) {
        Log.i(TAG_NAME, "Suppression d un resultat");
        databaseResultRepository.delete(result);
    }

    public void deleteAll() {
        Log.i(TAG_NAME, "Suppression de tous les resultats");
        databaseResultRepository.deleteAll();
    }

    public LiveData<List<Result>> getAllResults() {
        Log.i(TAG_NAME, "Recuperation de la liste de tous les resultats");
        return databaseResultRepository.getAllResults();
    }
}
