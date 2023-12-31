package com.gpwsofts.ffcalculator.mobile.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataScope;

import com.gpwsofts.ffcalculator.mobile.dao.FFCalculatorDatabase;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.dao.ResultDao;
import com.gpwsofts.ffcalculator.mobile.model.IResult;

import java.util.List;

/**
 * Couche Repository
 *
 * @since 1.0.0
 */
public class ResultRepository {
    private static final String TAG_NAME = "ResultRepository";
    private ResultDao resultDao;
    private LiveData<List<Result>> allResults;

    public ResultRepository(Application application) {
        FFCalculatorDatabase database = FFCalculatorDatabase.getInstance(application);
        resultDao = database.resultDao();
        allResults = resultDao.getAllResults();
    }

    public void insert(Result result) {
        Log.i(TAG_NAME, "Lancement de Async Task InsertResultAsyncTask");
        new InsertResultAsyncTask(resultDao).execute(result);
    }

    public void update(Result result) {
        Log.i(TAG_NAME, "Lancement de Async Task UpdateResultAsyncTask");
        new UpdateResultAsyncTask(resultDao).execute(result);
//TODO 1.0.0 asynctask deprecated , use Execotors https://stackoverflow.com/questions/58767733/the-asynctask-api-is-deprecated-in-android-11-what-are-the-alternatives
    }

    public void delete(Result result) {
        Log.i(TAG_NAME, "Lancement de Async Task DeleteResultAsyncTask");
        new DeleteResultAsyncTask(resultDao).execute(result);
    }

    public void deleteAll() {
        Log.i(TAG_NAME, "Lancement de Async Task DeleteAllResultsAsyncTask");
        new DeleteAllResultsAsyncTask(resultDao).execute();
    }

    public LiveData<List<Result>> getAllResults() {
        Log.i(TAG_NAME, "Recuperation de tous les resultats");
        return allResults;
    }

    /**
     * Async Task pour l'insertion en DB
     *
     * @since 1.0.0
     */
    private static class InsertResultAsyncTask extends AsyncTask<Result, Void, Void> {
        private ResultDao resultDao;

        private InsertResultAsyncTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            resultDao.insert(results[0]);
            return null;
        }
    }

    /**
     * Async Task pour l'update en DB
     *
     * @since 1.0.0
     */
    private static class UpdateResultAsyncTask extends AsyncTask<Result, Void, Void> {
        private ResultDao resultDao;

        private UpdateResultAsyncTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            resultDao.update(results[0]);
            return null;
        }
    }

    /**
     * Async Task pour la suppression en DB
     *
     * @since 1.0.0
     */
    private static class DeleteResultAsyncTask extends AsyncTask<Result, Void, Void> {
        private ResultDao resultDao;

        private DeleteResultAsyncTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            resultDao.delete(results[0]);
            return null;
        }
    }

    /**
     * Async Task pour la suppression massive en DB
     *
     * @since 1.0.0
     */
    private static class DeleteAllResultsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ResultDao resultDao;

        private DeleteAllResultsAsyncTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            resultDao.deleteAllResults();
            return null;
        }
    }
}
