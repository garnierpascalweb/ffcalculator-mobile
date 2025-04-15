package com.gpwsofts.ffcalculator.mobile.services.town;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de chargement de la liste des villes
 * @since 1.0.0
 */
public class SimpleTownService implements ITownService {
    private static final String TAG_NAME = "SimpleTownService";
    private static final String TOWN_RELATIVE_PATH = "towns/towns.txt";
    private List<String> towns = null;

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
        try {
            LogUtils.d(TAG_NAME, "debut chargement de la liste des villes");
            towns = new ArrayList<>();
            LogUtils.v(TAG_NAME, "ouverture du flux sur <" + TOWN_RELATIVE_PATH + "> et lecture ligne par ligne pour construction de la liste des villes");
            try (InputStream is = FFCalculatorApplication.instance.getApplicationContext().getAssets().open(TOWN_RELATIVE_PATH);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    towns.add(line);
                }
            }
            LogUtils.v(TAG_NAME, "fin chargement de la liste des villes - <" + towns.size() + "> villes chargées");
        } finally {
            LogUtils.d(TAG_NAME, "fin chargement de la liste des villes");
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
