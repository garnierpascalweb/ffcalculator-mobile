package com.gpwsofts.ffcalculator.mobile.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.repository.ResultRepository;

import java.util.List;

public class ResultViewModel extends AndroidViewModel {
    private ResultRepository repository;
    private LiveData<List<Result>> allResults;
    public ResultViewModel(@NonNull Application application) {
        super(application);
        repository = new ResultRepository(application);
        allResults = repository.getAllResults();
    }
    public void insert(Result result){
        repository.insert(result);
    }
    public void update(Result result){
        repository.update(result);
    }
    public void delete(Result result){
        repository.delete(result);
    }
    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Result>> getAllResults() {
        return allResults;
    }
}
