package com.gpwsofts.ffcalculator.mobile.services.grid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpwsofts.ffcalculator.mobile.common.reader.ReaderProvider;
import com.gpwsofts.ffcalculator.mobile.model.grid.Grid;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @since 1.0.0
 */
public class SimpleGridService extends AbstractGridService {
    private static final String TAG_NAME = "SimpleGridService";
    private static final String GRID_RELATIVE_PATH = "grids/grilles.json";


    public SimpleGridService(ReaderProvider readerProvider) {
        super(readerProvider);
    }

    public List<IGrid> getGrids() throws IOException {
        if (null == grids || grids.isEmpty())
            loadGridsFromLocalResource();
        return grids;
    }



    /**
     * Chargement des grilles
     *
     * @throws IOException problème lors du chargement des grilles
     * @since 1.0.0
     */
    private void loadGridsFromLocalResource() throws IOException {
        Type listGridType;
        Gson gson;
        try {
            LogUtils.i(TAG_NAME, "debut de chargement des grilles");
            LogUtils.d(TAG_NAME, "ouverture du flux sur <" + GRID_RELATIVE_PATH + ">");
            listGridType = new TypeToken<List<IGrid>>() {}.getType();
            LogUtils.v(TAG_NAME, "construction d'une instance de liste depuis Gson");
            gson = new Gson();
            grids = gson.fromJson(readerProvider.openReader(GRID_RELATIVE_PATH), listGridType);
            LogUtils.v(TAG_NAME, "tri de la liste des grilles");
            Collections.sort(grids);
            LogUtils.i(TAG_NAME, "fin du chargement des grilles - <" + grids.size() + "> grilles chargees");
        } finally {

        }
    }

    @Override
    public void clean() {
        if (grids != null) {
            grids.clear();
        }
    }
}
