package com.gpwsofts.ffcalculator.mobile.services.client;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @since 1.0.0
 * Api Cliente pour le chargement asynchrone des grilles, et des positions possibles pour chaque grille
 * Appelé par GridRepository
 *
 */
public class GridApiClient {
    private static final String TAG_NAME = "GridApiClient";
    private static GridApiClient instance;
    private final SingleLiveEvent<List<Grid>> mGridChoices;
    private final SingleLiveEvent<List<Integer>> mPosChoices;
    private LoadClassesChoicesRunnable loadClassesChoicesRunnable;
    private LoadPosChoicesRunnable loadPosChoicesRunnable;
    private static final int JOB_TIMEOUT = 5000;

    private GridApiClient() {
        Log.i(TAG_NAME, "instanciation de GridApiClient");
        mGridChoices = new SingleLiveEvent<List<Grid>>();
        mPosChoices = new SingleLiveEvent<List<Integer>>();
        Log.i(TAG_NAME, "fin instanciation de GridApiClient");
    }

    public static GridApiClient getInstance() {
        if (null == instance)
            instance = new GridApiClient();
        return instance;
    }

    public LiveData<List<Grid>> getGridChoicesLiveData() {
        return mGridChoices;
    }

    public LiveData<List<Integer>> getPosChoicesLiveData() {
        return mPosChoices;
    }

    public void loadGridChoicesAsync(String vue) {
        if (loadClassesChoicesRunnable != null) {
            loadClassesChoicesRunnable = null;
        }
        loadClassesChoicesRunnable = new LoadClassesChoicesRunnable(vue);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(loadClassesChoicesRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            myHandler.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
        //TODO 1.0.0 revoir ce timeout
    }

    public void loadPosChoicesAsync(String libelle) {
        if (loadPosChoicesRunnable != null) {
            loadPosChoicesRunnable = null;
        }
        loadPosChoicesRunnable = new LoadPosChoicesRunnable(libelle);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(loadPosChoicesRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            myHandler.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
        //TODO 1.0.0 revoir ce timeout
    }


    /**
     * @since 1.0.0
     * Job qui permet le chargement asynchrone de la liste des classes sélectionnables à l'interface,
     * à partir d'une vue
     */
    private class LoadClassesChoicesRunnable implements Runnable {
        private final String vue;
        boolean cancelRequest;

        public LoadClassesChoicesRunnable(String vue) {
            this.vue = vue;
        }

        @Override
        public void run() {
            Log.i(TAG_NAME, "debut du job asynchrone LoadClassesChoicesRunnable selon la vue <" + vue + ">");
            IGridService gridService = null;
            List<Grid> listGridsForMyVue = null;
            try {
                List<Grid> grids = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids();
                Log.d(TAG_NAME, " nombre de grilles <" + grids.size() + ">");
                listGridsForMyVue = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids().stream().filter(grid -> grid.getVues().contains(vue)).collect(Collectors.toList());
                Log.d(TAG_NAME, " <" + listGridsForMyVue.size() + "> vues chargees pour la vue <" + vue + ">");
            } catch (Exception e){
                Log.e(TAG_NAME, "echec du chargement de la liste des classes de courses par rapport a la vue", e);
                listGridsForMyVue = null;
            } finally {
                mGridChoices.postValue(listGridsForMyVue);
                Log.i(TAG_NAME, "fin  du job asynchrone LoadClassesChoicesRunnable selon la vue <" + vue + ">");
            }
        }

        private void cancelRequest() {
            Log.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }

    /**
     * @since 1.0.0
     * Job qui permet le chargement asynchrone le la liste des positions sélectionnables à l'interface,
     * en fonction d'un item sélectionné
     */
    private class LoadPosChoicesRunnable implements Runnable {
        private final String libelle;
        boolean cancelRequest;

        public LoadPosChoicesRunnable(String libelle) {
            this.libelle = libelle;
        }

        @Override
        public void run() {
            Log.i(TAG_NAME, "debut du job asynchrone LoadPosChoicesRunnable selon le libelle <" + libelle + ">");
            List<Integer> posChoices = null;
            IGridService gridService = null;
            try{
                final String idClasse = FFCalculatorApplication.instance.getServicesManager().getGridService().getIdClasseFromLibelle(libelle);
                final Grid myGrid = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids().stream().filter(grid -> grid.getCode().equals(idClasse)).findAny().orElse(null);
                if (myGrid != null) {
                    posChoices = IntStream.rangeClosed(1, myGrid.getMaxPos()).boxed().collect(Collectors.toList());
                } else {
                    posChoices = null;
                }
            } catch (Exception e){
                Log.e(TAG_NAME, "echec du chargement de la liste des ppositions possibles ", e);
                posChoices = null;
            } finally {
                mPosChoices.postValue(posChoices);
                Log.i(TAG_NAME, "fin du job asynchrone LoadPosChoicesRunnable selon le libelle <" + libelle + ">");
            }
        }

        private void cancelRequest() {
            Log.v("TAGNAME", "annulation de la requete");
            cancelRequest = true;
        }
    }

    private class GridToLibelleFunction implements Function<Grid, String> {
        @Override
        public String apply(Grid grid) {
            return grid.getLibelle() + " (" + grid.getCode() + ")";
        }
    }
}
