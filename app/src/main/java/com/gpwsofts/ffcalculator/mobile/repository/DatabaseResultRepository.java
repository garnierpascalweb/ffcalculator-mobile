package com.gpwsofts.ffcalculator.mobile.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.FFCalculatorDatabase;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.dao.ResultDao;

import java.util.List;

/**
 * Repository pour la liste des resultats en base de données
 * Basé sur la base de données
 *
 * @since 1.0.0
 */
public class DatabaseResultRepository {
    private static final String TAG_NAME = "DatabaseResultRepository";
    private static DatabaseResultRepository instance;
    private final ResultDao resultDao;

    private DatabaseResultRepository() {
        Log.i(TAG_NAME, "Instanciation de ResultRepository");
        // FFCalculatorDatabase database = FFCalculatorDatabase.getInstance(application);
        // https://medium.com/@imkuldeepsinghrai/a-comprehensive-guide-to-room-database-in-android-implementation-and-best-practices-f3af8c498624
        FFCalculatorDatabase database = FFCalculatorDatabase.getInstance();
        resultDao = database.resultDao();
        Log.i(TAG_NAME, "Fin Instanciation de ResultRepository");
    }

    public static DatabaseResultRepository getInstance() {
        if (null == instance)
            instance = new DatabaseResultRepository();
        return instance;
    }

    public LiveData<List<Result>> getAllResultsLiveData() {
        Log.i(TAG_NAME, "Recuperation de tous les resultats");
        return resultDao.getAllResults();
    }

    public LiveData<Double> getAllPtsLiveData() {
        Log.i(TAG_NAME, "Recuperation du total des points");
        // si ya rien en ase de données, le liveData contient un Double qui est null
        return resultDao.getPts();
    }

    public void insert(Result result) {
        Log.i(TAG_NAME, "Envoi dans le pool Executor database dun ordre de insert");
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> {
            resultDao.insert(result);
        });
    }

    public void update(Result result) {
        Log.i(TAG_NAME, "Envoi dans le pool Executor database dun ordre de update");
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> {
            resultDao.update(result);
        });
    }

    public void delete(Result result) {
        Log.i(TAG_NAME, "Envoi dans le pool Executor database dun ordre de delete");
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> {
            resultDao.delete(result);
        });
    }

    public void deleteAll() {
        Log.i(TAG_NAME, "Envoi dans le pool Executor database dun ordre de Delete All");
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> {
            resultDao.deleteAllResults();
        });
    }
}
