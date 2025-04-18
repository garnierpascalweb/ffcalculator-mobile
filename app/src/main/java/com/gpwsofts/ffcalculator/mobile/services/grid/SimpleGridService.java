package com.gpwsofts.ffcalculator.mobile.services.grid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.exception.InputLibelleFormatException;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @since 1.0.0
 */
public class SimpleGridService implements IGridService {
    private static final String TAG_NAME = "SimpleGridService";
    private static final String GRID_RELATIVE_PATH = "grids/grilles.json";
    private List<Grid> grids;

    public SimpleGridService() {
        grids = new ArrayList<>();
    }

    public List<Grid> getGrids() throws IOException {
        if (null == grids || grids.isEmpty())
            loadGridsFromLocalResource();
        return grids;
    }

    @Override
    public String getIdClasseFromLibelle(String libelle) throws InputLibelleFormatException {
        String idClasse;
        if (null == libelle)
            throw new InputLibelleFormatException("libelle est null");
        try {
            final int borneInf = libelle.indexOf("(") + 1;
            final int borneSup = libelle.indexOf(")");
            idClasse = libelle.substring(borneInf, borneSup);
        } catch (IndexOutOfBoundsException ie) {
            throw new InputLibelleFormatException(" le libelle " + libelle + " nest pas au bon format", ie);
        }
        return idClasse;
    }

    /**
     * Chargement des grilles
     *
     * @throws IOException problème lors du chargement des grilles
     * @since 1.0.0
     */
    private void loadGridsFromLocalResource() throws IOException {
        InputStream is = null;
        Type listGridType;
        String jsonDatas;
        Gson gson;
        try {
            LogUtils.i(TAG_NAME, "debut de chargement des grilles");
            LogUtils.d(TAG_NAME, "ouverture du flux sur <" + GRID_RELATIVE_PATH + ">");
            is = FFCalculatorApplication.instance.getApplicationContext().getAssets().open(GRID_RELATIVE_PATH);
            int size = is.available();
            LogUtils.v(TAG_NAME, "<" + size + "> octets lus - creation dun buffer de cette taille");
            byte[] buffer = new byte[size];
            final int readed = is.read(buffer);
            listGridType = new TypeToken<List<Grid>>() {}.getType();
            jsonDatas = new String(buffer, StandardCharsets.UTF_8);
            LogUtils.v(TAG_NAME, "construction d'une instance de liste depuis Gson");
            gson = new Gson();
            grids = gson.fromJson(jsonDatas, listGridType);
            LogUtils.v(TAG_NAME, "tri de la liste des grilles");
            Collections.sort(grids);
            LogUtils.i(TAG_NAME, "fin du chargement des grilles - <" + grids.size() + "> grilles chargees");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtils.w(TAG_NAME, "probleme lors de la fermeture dun flux");
                }
            }
        }
    }

    @Override
    public void clean() {
        if (grids != null) {
            grids.clear();
        }
    }
}
