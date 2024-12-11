package com.gpwsofts.ffcalculator.mobile.services.api.business.grid;

import android.util.Log;

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

/**
 * @since 1.0.0
 */
public class SimpleGridService implements IGridService {
    private static final String TAG_NAME = "SimpleGridService";
    private static final String GRID_RELATIVE_PATH = "grids/grilles.json";
    private static final ExecutorService gridServiceExecutor = Executors.newFixedThreadPool(1);
    private List<Grid> grids = null;

    public SimpleGridService() {
        grids = new ArrayList<Grid>();
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
        } catch (IOException e) {
            Log.wtf(TAG_NAME, "probleme lors du chargement des grilles");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.w(TAG_NAME, "probleme lors de la fermeture dun flux");
                }
            }
        }
    }

    public List<Grid> getGrids() {
        return grids;
    }
}
