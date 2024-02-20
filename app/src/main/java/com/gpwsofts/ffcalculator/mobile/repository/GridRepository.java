package com.gpwsofts.ffcalculator.mobile.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.Grid;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GridRepository {
    private static final String TAG_NAME = "GridRepository";
    public static final ExecutorService gridRepositoryExecutor = Executors.newFixedThreadPool(1);

    MutableLiveData<List<String>> classesChoices;
    List<Grid> grids = null;

    public GridRepository(final Application application) {
        classesChoices = new MutableLiveData<>();
        Gson gson = new Gson();
        InputStream is = null;
        String jsonDatas = null;
        Type listGridType = null;
        try {
            is = application.getApplicationContext().getAssets().open("grids/grilles.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            listGridType = new TypeToken<List<Grid>>() {
            }.getType();
            jsonDatas = new String(buffer, StandardCharsets.UTF_8);
            grids = gson.fromJson(jsonDatas, listGridType);
            //TODO 1.0.0 pas O1 en default, mais ce quil y a en shared prefs
            loadClassesChoices("O1");
        } catch (IOException e) {
            Log.e(TAG_NAME, "probleme sur le chargement du json", e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {

                } finally {

                }
        }

    }

    public void init() {

    }

    private void loadClassesChoices(String vue) {
        gridRepositoryExecutor.execute(() -> {
            List<String> values = grids.stream().filter(grid -> grid.vues.contains(vue)).map(grid -> new StringBuilder().append(grid.libelle).append(" ").append(grid.code).toString()).collect(Collectors.toList());
            ;
            classesChoices.postValue(values);
        });
    }

    public LiveData<List<String>> getClassesChoices() {
        return classesChoices;
    }

    private void loadPrtsChoices() {

    }

    private void loadPosChoices() {
        //TODO 1.0.0 operation asynchrones
    }
}
