package com.gpwsofts.ffcalculator.mobile.services.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridApiClient {
    private static final String TAG_NAME = "GridApiClient";
    private static GridApiClient instance;

    private MutableLiveData<List<Grid>> mGridChoices;
    private MutableLiveData<List<Integer>> mPosChoices;

    private LoadClassesChoicesRunnable loadClassesChoicesRunnable;
    private LoadPosChoicesRunnable loadPosChoicesRunnable;
    public static GridApiClient getInstance() {
        if (null == instance)
            instance = new GridApiClient();
        return instance;
    }
    private GridApiClient(){
        Log.i(TAG_NAME,"instanciation de GridApiClient");
        mGridChoices = new MutableLiveData<List<Grid>>();
        mPosChoices = new MutableLiveData<List<Integer>>();
        Log.i(TAG_NAME,"fin instanciation de GridApiClient");
    }

    public LiveData<List<Grid>> getGridChoices() {
        return mGridChoices;
    }

    public LiveData<List<Integer>> getPosChoices() {
        return mPosChoices;
    }

    public void loadClassesChoices(String vue){
        if (loadClassesChoicesRunnable != null){
            loadClassesChoicesRunnable = null;
        }
        loadClassesChoicesRunnable = new LoadClassesChoicesRunnable(vue);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(loadClassesChoicesRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            myHandler.cancel(true);
        }, 5000, TimeUnit.MILLISECONDS);
        //TODO 1.0.0 revoir ce timeout
    }

    public void loadPosChoices(String libelle){
        if (loadPosChoicesRunnable != null){
            loadPosChoicesRunnable = null;
        }
        loadPosChoicesRunnable = new LoadPosChoicesRunnable(libelle);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(loadPosChoicesRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            myHandler.cancel(true);
        }, 5000, TimeUnit.MILLISECONDS);
        //TODO 1.0.0 revoir ce timeout
    }



    /**
     * @since 1.0.0
     * Job qui permet le chargement asynchrone de la liste des classes sélectionnables à l'interface,
     * à partir d'une vue
     */
    private class LoadClassesChoicesRunnable implements Runnable {
        boolean cancelRequest;
        private String vue;

        public LoadClassesChoicesRunnable(String vue){
            this.vue = vue;
        }
        @Override
        public void run() {
            Log.i(TAG_NAME, "debut du job asynchrone LoadClassesChoicesRunnable selon la vue <" + vue + ">");
            IGridService gridService = FFCalculatorApplication.instance.getServicesManager().getGridService();
            List<Grid> listGrids = gridService.getGrids().stream().filter(grid -> grid.vues.contains(vue)).collect(Collectors.toList());
            Log.d(TAG_NAME, "post du la liste des grilles en liveData (" + listGrids.size() + " elements charges)");
            mGridChoices.postValue(listGrids);
            Log.i(TAG_NAME, "fin  du job asynchrone LoadClassesChoicesRunnable selon la vue <" + vue + ">");
        }

        private void cancelRequest() {
            Log.v("TAGNAME", "annulation de la requete");
            cancelRequest = true;
        }
    }

    /**
     * @since 1.0.0
     * Job qui permet le chargement asynchrone le la liste des positions sélectionnables à l'interface,
     * en fonction d'un item sélectionné
     */
    private class LoadPosChoicesRunnable implements Runnable {
        boolean cancelRequest;
        private String libelle;

        public LoadPosChoicesRunnable(String libelle){
            this.libelle = libelle;
        }
        @Override
        public void run() {
            Log.i(TAG_NAME, "debut du job asynchrone LoadPosChoicesRunnable selon le libelle <" + libelle + ">");
            List<Integer> posChoices = null;
            IGridService gridService = FFCalculatorApplication.instance.getServicesManager().getGridService();
            String idClasse = libelle.substring(libelle.indexOf("(") + 1, libelle.indexOf(")"));
            Grid myGrid = gridService.getGrids().stream().filter(grid -> grid.code.equals(idClasse)).findAny().orElse(null);
            if (myGrid != null) {
                posChoices = IntStream.rangeClosed(1, myGrid.maxPos).boxed().collect(Collectors.toList());
            } //TODO 1.0.0 et si null ?
            Log.d(TAG_NAME, "post du la liste des pos en liveData (" + posChoices.size() + " elements charges)");
            mPosChoices.postValue(posChoices);
            Log.i(TAG_NAME, "fin du job asynchrone LoadPosChoicesRunnable selon le libelle <" + libelle + ">");
        }

        private void cancelRequest() {
            Log.v("TAGNAME", "annulation de la requete");
            cancelRequest = true;
        }
    }

    private class GridToLibelleFunction implements Function<Grid, String> {
        @Override
        public String apply(Grid grid) {
            return new StringBuilder().append(grid.libelle).append(" (").append(grid.code).append(")").toString();
        }
    }
}
