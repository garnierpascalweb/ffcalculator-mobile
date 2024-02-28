package com.gpwsofts.ffcalculator.mobile.services.grid;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.model.Grid;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @since 1.0.0
 */
public class SimpleGridService implements IGridService {
    private static final String TAG_NAME = "SimpleGridService";
    private static final String GRID_RELATIVE_PATH = "grids/grilles.json";
    private static final ExecutorService gridServiceExecutor = Executors.newFixedThreadPool(1);
    private static final List<Integer> DEFAULT_5_POS = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());
    private List<Grid> grids = null;

    private MutableLiveData<List<Grid>> gridsChoices;

    private MutableLiveData<List<Integer>> posChoices;

    public SimpleGridService() {
        grids = new ArrayList<Grid>();
        gridsChoices = new MutableLiveData<List<Grid>>();
        posChoices = new MutableLiveData<List<Integer>>();
        loadAsynchronously();
    }

    private void loadAsynchronously() {
        Log.i(TAG_NAME, "chargement asynchrone des grilles depuis la ressource en asset = <" + GRID_RELATIVE_PATH + ">");
        gridServiceExecutor.execute(() -> {
            InputStream is = null;
            Type listGridType = null;
            String jsonDatas = null;
            Gson gson = null;
            try {
                Log.i(TAG_NAME, "debut de chargement des grilles");
                Log.d(TAG_NAME, "ouverture du flux sur <" + GRID_RELATIVE_PATH + ">");
                is = FFCalculatorApplication.instance.getApplicationContext().getAssets().open(GRID_RELATIVE_PATH);
                int size = is.available();
                Log.v(TAG_NAME, "<" + size + "> octets lus - creation dun buffer de cette taille");
                byte[] buffer = new byte[size];
                is.read(buffer);
                listGridType = new TypeToken<List<Grid>>() {
                }.getType();
                jsonDatas = new String(buffer, StandardCharsets.UTF_8);
                Log.v(TAG_NAME, "construction d'une instance de liste depuis Gson");
                gson = new Gson();
                grids = gson.fromJson(jsonDatas, listGridType);
                Log.v(TAG_NAME, "tri de la liste des grilles");
                Collections.sort(grids);
                Log.i(TAG_NAME, "fin du chargement des grilles - <" + grids.size() + "> grilles chargees");
                posChoices = new MutableLiveData<>();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.w(TAG_NAME, "probleme lors de la fermeture dun flux");
                    }
                }
            }
        });
    }

    public void loadGridChoicesAsynchronously(String vue) {
        Log.i(TAG_NAME, "chargement asynchrone  des grilles pour la vue <" + vue + ">");
        gridServiceExecutor.execute(() -> {
            List<Grid> currentGridsChoices = null;
            currentGridsChoices = grids.stream().filter(grid -> grid.vues.contains(vue)).collect(Collectors.toList());
            Log.d(TAG_NAME, currentGridsChoices.size() + " choix de grids renvoyees pour <" + vue + ">");
            gridsChoices.postValue(currentGridsChoices);
        });
    }

    public void loadPosChoicesAsynchronously(String itemValue) {
        Log.i(TAG_NAME, "chargement asynchrone des choix de positions pour un itemValue <" + itemValue + ">");
        gridServiceExecutor.execute(() -> {
            List<Integer> currentPosChoices = DEFAULT_5_POS;
            String code = itemValue.substring(itemValue.indexOf("(") + 1, itemValue.indexOf(")"));
            Grid myGrid = grids.stream().filter(grid -> grid.code.equals(code)).findAny().orElse(null);
            if (myGrid != null) {
                currentPosChoices = IntStream.rangeClosed(1, myGrid.maxPos).boxed().collect(Collectors.toList());
            }
            Log.d(TAG_NAME, currentPosChoices.size() + " choix de positions renvoyees pour <" + itemValue + ">");
            posChoices.postValue(currentPosChoices);
        });
    }

    public LiveData<List<Integer>> getPosChoices() {
        return posChoices;
    }

    public LiveData<List<Grid>> getGridChoices() {
        return gridsChoices;
    }

    private class GridToLibelleFunction implements Function<Grid, String> {
        @Override
        public String apply(Grid grid) {
            return new StringBuilder().append(grid.libelle).append(" (").append(grid.code).append(")").toString();
        }
    }
}
