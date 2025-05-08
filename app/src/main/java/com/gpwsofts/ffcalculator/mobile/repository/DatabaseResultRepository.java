package com.gpwsofts.ffcalculator.mobile.repository;

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
        // FFCalculatorDatabase database = FFCalculatorDatabase.getInstance(application);
        // https://medium.com/@imkuldeepsinghrai/a-comprehensive-guide-to-room-database-in-android-implementation-and-best-practices-f3af8c498624
        FFCalculatorDatabase database = FFCalculatorDatabase.getInstance();
        resultDao = database.resultDao();
    }

    public static DatabaseResultRepository getInstance() {
        if (null == instance)
            instance = new DatabaseResultRepository();
        return instance;
    }

    public LiveData<List<Result>> getAllResultsLiveData() {
        return resultDao.getAllResults();
    }

    public LiveData<Double> getAllPtsLiveData() {
        // si ya rien en ase de données, le liveData contient un Double qui est null
        return resultDao.getPts();
    }

    public void insert(Result result) {
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> resultDao.insert(result));
    }

    /**
     * non utilisée en 1.0.0
     * @param result
     */
    public void update(Result result) {
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> resultDao.update(result));
    }

    public void delete(Result result) {
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> resultDao.delete(result));
    }

    /**
     * non utilisé en 1.0.0
     */
    public void deleteAll() {
        FFCalculatorDatabase.databaseWriteExecutor.execute(() -> resultDao.deleteAllResults());
    }
}
