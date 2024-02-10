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
 *
 * @since 1.0.0
 */
public class ResultRepository {
    private static final String TAG_NAME = "ResultRepository";
    private ResultDao resultDao;
    private LiveData<List<Result>> allResults;

    public ResultRepository() {
        Log.i(TAG_NAME, "Instanciation de ResultRepository");
        FFCalculatorDatabase database = FFCalculatorDatabase.getInstance(application);
        resultDao = database.resultDao();
        //allResults = new LiveData<List<Result>>() {};
        //select();
        allResults = resultDao.getAllResults();
        Log.i(TAG_NAME, "Fin Instanciation de ResultRepository");
    }

    /*
    public void select(){
        Log.i(TAG_NAME, "Lancement Executor Select");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            Log.i(TAG_NAME, "recuperation des resultats en tache de fond");
            LiveData<List<Result>> loadedAllResults = resultDao.getAllResults();
            Log.i(TAG_NAME, "fin recuperation des resultats en tache de fond");
            handler.post(() -> {
                //UI Thread work here
                if (loadedAllResults != null) {
                    Log.i(TAG_NAME, "post affectation des resultats consistants");
                    allResults = loadedAllResults;
                } else {
                    Log.w(TAG_NAME, "post affectation des resultats null");
                }
            });
        });
    }
    */

    public LiveData<List<Result>> getAllResults() {
        Log.i(TAG_NAME, "Recuperation de tous les resultats");
        return allResults;
    }

    public void insert(Result result) {
        Log.i(TAG_NAME, "Lancement Executor Insert");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            resultDao.insert(result);
        });
    }

    public void update(Result result) {
        Log.i(TAG_NAME, "Lancement Executor Update");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            resultDao.update(result);
        });
   }

    public void delete(Result result) {
        Log.i(TAG_NAME, "Lancement Executor Delete");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            resultDao.delete(result);
        });
    }

    public void deleteAll() {
        Log.i(TAG_NAME, "Lancement Executor Delete All");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            resultDao.deleteAllResults();
        });
    }
}
