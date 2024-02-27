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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridRepository {
    private static final String TAG_NAME = "GridRepository";
    public static final ExecutorService gridRepositoryExecutor = Executors.newFixedThreadPool(1);

    public static final List<Integer> DEFAULT_5_POS = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());

    MutableLiveData<List<String>> classesChoices;
    MutableLiveData<List<Integer>> posChoices;

    MutableLiveData<List<Integer>> prtsChoices;
    private List<Grid> grids = null;

    public static final String GRID_RELATIVE_PATH = "grids/grilles.json";

    public GridRepository(final Application application) {
        InputStream is = null;
        Type listGridType = null;
        String jsonDatas = null;
        Gson gson = null;
        try {
            Log.i(TAG_NAME, "debut de chargement des grilles");
            is = application.getApplicationContext().getAssets().open(GRID_RELATIVE_PATH);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            listGridType = new TypeToken<List<Grid>>() {
            }.getType();
            jsonDatas = new String(buffer, StandardCharsets.UTF_8);
            gson = new Gson();
            grids = gson.fromJson(jsonDatas, listGridType);
            Log.i(TAG_NAME, "fin du chargement des grilles - <" + grids.size() + "> grilles chargees");
            classesChoices = new MutableLiveData<>();
            posChoices = new MutableLiveData<>();
            prtsChoices = new MutableLiveData<>();
            updatePrtsChoices();
        } catch (IOException e) {
            Log.e(TAG_NAME, "probleme lors du chargement des grilles");
        }  finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.w(TAG_NAME, "probleme lors de la fermeture dun flux");
                }
            }
        }
    }


    /**
     * Chargement asynchrone de la liste déroulante des classes
     * @param vue en fonction de la vue
     */
    public void updateClassesChoices(String vue) {
        Log.i(TAG_NAME, "tache asynchrone de chargement des choix de classes pour la vue <" + vue + ">");
        gridRepositoryExecutor.execute(() -> {
            List<String> currentClasesChoices = null;
            currentClasesChoices = grids.stream().filter(grid -> grid.vues.contains(vue)).map(new GridToLibelleFunction()).collect(Collectors.toList());
            Log.d(TAG_NAME, currentClasesChoices.size() + " choix de classes renvoyees pour <" + vue + ">");
            classesChoices.postValue(currentClasesChoices);
        });
    }

    private class GridToLibelleFunction implements Function<Grid, String> {
        @Override
        public String apply(Grid grid) {
            return new StringBuilder().append(grid.libelle).append(" (").append(grid.code).append(")").toString();
        }
    }

    /**
     * Chargement en asynchrone de la liste deroulance des position
     * @param itemValue en fonction de la classe de course (1.25.1)
     */
    public void updatePosChoices(String itemValue){
        Log.i(TAG_NAME, "tache asynchrone de chargement des choix de positions pour un itemValue <" + itemValue + ">");
        gridRepositoryExecutor.execute(()->{
            List<Integer> currentPosChoices = DEFAULT_5_POS;
            String code =  itemValue.substring(itemValue.indexOf("(")+1, itemValue.indexOf(")"));
            Grid myGrid = grids.stream().filter(grid -> grid.code.equals(code)).findAny().orElse(null);
            if (myGrid != null){
                currentPosChoices = IntStream.rangeClosed(1, myGrid.maxPos).boxed().collect(Collectors.toList());
            }
            Log.d(TAG_NAME, currentPosChoices.size() + " choix de positions renvoyees pour <" + itemValue + ">");
            posChoices.postValue(currentPosChoices);
        });
    }

    private void updatePrtsChoices(){
        Log.i(TAG_NAME, "tache asynchrone de chargement des choix de partants");
        gridRepositoryExecutor.execute(()->{
            List<Integer> currentPrtsChoices = null;
            //TODO 1.0.0 200 pas en dur
            currentPrtsChoices = IntStream.rangeClosed(1, 200).boxed().collect(Collectors.toList());
            prtsChoices.postValue(currentPrtsChoices);
            Log.d(TAG_NAME, currentPrtsChoices.size() + " choix de partants renvoyees");
        });
    }

    public MutableLiveData<List<String>> getClassesChoices() {
        return classesChoices;
    }

    public MutableLiveData<List<Integer>> getPosChoices() {
        return posChoices;
    }
    public LiveData<List<Integer>> getPrtsChoices() {
        return prtsChoices;
    }
}
