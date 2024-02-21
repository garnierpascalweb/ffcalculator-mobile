package com.gpwsofts.ffcalculator.mobile.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpwsofts.ffcalculator.mobile.model.Grid;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridRepository {
    private static final String TAG_NAME = "GridRepository";
    public static final ExecutorService gridRepositoryExecutor = Executors.newFixedThreadPool(1);

    public static final List<Integer> DEFAULT_5_POS = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());
    MutableLiveData<List<String>> classesChoices;
    MutableLiveData<List<Integer>> posChoices;
    List<Grid> grids = null;

    public GridRepository(final Application application) {
        classesChoices = new MutableLiveData<>();
        Gson gson = new Gson();
        InputStream is = null;
        String jsonDatas = null;
        Type listGridType = null;
        try {
            Log.i(TAG_NAME, "chargement du fichier json des grilles");
            is = application.getApplicationContext().getAssets().open("grids/grilles.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            listGridType = new TypeToken<List<Grid>>() {
            }.getType();
            jsonDatas = new String(buffer, StandardCharsets.UTF_8);
            grids = gson.fromJson(jsonDatas, listGridType);
            //TODO 1.0.0 pas O1 en default, mais ce quil y a en shared prefs
            Log.i(TAG_NAME, "fin chargement du fichier json des grilles");
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

    /**
     * Chargement asynchrone de la liste déroulante des classes
     * @param vue en fonction de la vue
     */
    private void loadClassesChoices(String vue) {
        Log.i(TAG_NAME, "tache asynchrone de chargement des choix de classes pour la vue <" + vue + ">");
        gridRepositoryExecutor.execute(() -> {
            List<String> currentClasesChoices = grids.stream().filter(grid -> grid.vues.contains(vue)).map(grid -> new StringBuilder().append(grid.libelle).append(" (").append(grid.code).append(")").toString()).collect(Collectors.toList());
            Log.d(TAG_NAME, currentClasesChoices.size() + " choix de classes renvoyees pour <" + vue + ">");
            classesChoices.postValue(currentClasesChoices);
        });
    }

    /**
     * Chargement en asynchrone de la liste deroulance des position
     * @param code en fonction de la classe de course (1.25.1)
     */
    public void loadPosChoices(String code){
        Log.i(TAG_NAME, "tache asynchrone de chargement des choix de positions pour <" + code + ">");
        gridRepositoryExecutor.execute(()->{
            List<Integer> currentPosChoices = DEFAULT_5_POS;
            Grid myGrid = grids.stream().filter(grid -> grid.code.equals(code)).findAny().orElse(null);
            if (myGrid != null){
                currentPosChoices = IntStream.rangeClosed(1, myGrid.maxPos).boxed().collect(Collectors.toList());
            }
            Log.d(TAG_NAME, currentPosChoices.size() + " choux de positions renvoyees pour <" + code + ">");
            posChoices.postValue(currentPosChoices);
        });
    }

    public LiveData<List<String>> getClassesChoices() {
        return classesChoices;
    }

    public LiveData<List<Integer>> getPosChoices(String code){
        if (null == posChoices){
            posChoices = new MutableLiveData<>();
            loadPosChoices(code);
        }
        return posChoices;
    }

    private void loadPrtsChoices() {

    }

}
