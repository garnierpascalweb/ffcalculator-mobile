package com.gpwsofts.ffcalculator.mobile.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.FFCalculatorDatabase;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.dao.ResultDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Couche Repository
 * @since 1.0.0
 */
public class ResultRepository {
    private static final String TAG_NAME = "ResultRepository";
    private ResultDao resultDao;
    private LiveData<List<Result>> allResults;

    public ResultRepository(Application application) {
        Log.i(TAG_NAME, "Instanciation de ResultRepository");
        FFCalculatorDatabase database = FFCalculatorDatabase.getInstance(application);
        resultDao = database.resultDao();
        allResults = resultDao.getAllResults();
        Log.i(TAG_NAME, "Fin Instanciation de ResultRepository");
    }

    public LiveData<List<Result>> getAllResults() {
        Log.i(TAG_NAME, "Recuperation de tous les resultats");
        return allResults;
    }

    public void insert(Result result) {
        Log.i(TAG_NAME, "Envoi dans le pool Executor database dun ordre de insert");
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> {
            resultDao.insert(result);
        });
    }

    public void delete(Result result) {
        Log.i(TAG_NAME, "Envoi dans le pool Executor database dun ordre de Delete");
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
