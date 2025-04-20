package com.gpwsofts.ffcalculator.mobile.services.town;

import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.common.reader.ReaderProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de chargement de la liste des villes
 * @since 1.0.0
 */
public class SimpleTownService extends AbstractTownService {
    private static final String TAG_NAME = "SimpleTownService";
    private static final String TOWN_RELATIVE_PATH = "towns/towns.txt";
    private List<String> towns = null;

    public SimpleTownService(ReaderProvider readerProvider){
        super(readerProvider);
    }

    @Override
    public List<String> getTowns() throws IOException {
        if (null == towns || towns.isEmpty())
            loadTownsFromLocalResource();
        return towns;
    }

    /**
     * Methode de chargement de la liste des villes
     * Doit etre appelé dans un contexte asynchrone
     * @throws IOException
     */
    private void loadTownsFromLocalResource() throws IOException {
        int nb = 0;
        try {
            towns = new ArrayList<>();
            try (BufferedReader reader = readerProvider.openReader(TOWN_RELATIVE_PATH)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    towns.add(line);
                }
            }
            nb = towns.size();
        } finally {
            LogUtils.i(TAG_NAME, "fin chargement des villes - <" + nb + "> villes chargées");
        }
    }

    @Override
    public void clean() {
        if (towns != null) {
            LogUtils.v(TAG_NAME, "nettoyage de la liste des villes");
            towns.clear();
        }
    }
}
