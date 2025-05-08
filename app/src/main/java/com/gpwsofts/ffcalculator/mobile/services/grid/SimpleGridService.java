package com.gpwsofts.ffcalculator.mobile.services.grid;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.common.reader.ReaderProvider;
import com.gpwsofts.ffcalculator.mobile.model.grid.Grid;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 1.0.0
 */
public class SimpleGridService extends AbstractGridService {
    private static final String TAG_NAME = "SimpleGridService";
    private static final String GRID_RELATIVE_PATH = "grids/grilles.json";


    public SimpleGridService(ReaderProvider readerProvider) {
        super(readerProvider);
    }

    @Override
    public List<IGrid> getGrids() throws IOException {
        if (null == grids || grids.isEmpty())
            loadGridsFromLocalResource();
        return grids;
    }

    @Override
    public List<IGrid> getGrids(String codeVue) throws IOException {
        return getGrids().stream().filter(grid -> grid.getVues().contains(codeVue)).collect(Collectors.toList());
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
        int nb = 0;
        try {
            LogUtils.d(TAG_NAME, "debut chargement des grilles depuis <" + GRID_RELATIVE_PATH + ">");
            // ici obligé de mettre le type concret Grid sinon Unable to invoke no-args constructor for interface com.gpwsofts.ffcalculator.mobile.model.grid.IGrid
            listGridType = new TypeToken<List<Grid>>() {}.getType();
            gson = new Gson();
            grids = gson.fromJson(readerProvider.openReader(GRID_RELATIVE_PATH), listGridType);
            Collections.sort(grids);
            nb = grids.size();
        } finally {
            LogUtils.i(TAG_NAME, "fin chargement des grilles - <" + nb + "> classes d'épreuve chargées");
        }
    }

    @Override
    public void clean() {
        if (grids != null) {
            grids.clear();
        }
    }
}
