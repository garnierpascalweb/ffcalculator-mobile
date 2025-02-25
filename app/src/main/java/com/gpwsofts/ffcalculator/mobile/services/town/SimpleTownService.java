package com.gpwsofts.ffcalculator.mobile.services.town;

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
import java.util.Scanner;

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

    private void loadTownsFromLocalResource() throws IOException {
        InputStream is = null;
        try {
            Log.i(TAG_NAME, "debut de chargement des villes");
            towns = new ArrayList<String>();
            Log.d(TAG_NAME, "ouverture du flux sur <" + TOWN_RELATIVE_PATH + ">");
            is = FFCalculatorApplication.instance.getApplicationContext().getAssets().open(TOWN_RELATIVE_PATH);
            final Scanner scanner = new Scanner(is);
            while (scanner.hasNextLine()){
                final String town = scanner.nextLine();
                if (town != null && !town.isEmpty()){
                    towns.add(town);
                }
            }
            Log.i(TAG_NAME, "fin du chargement des villes - <" + towns.size() + "> villes chargees");
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

    @Override
    public void clean() {
        if (towns != null)
            towns.clear();
    }
}
