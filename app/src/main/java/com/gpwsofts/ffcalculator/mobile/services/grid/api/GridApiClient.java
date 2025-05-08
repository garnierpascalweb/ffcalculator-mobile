package com.gpwsofts.ffcalculator.mobile.services.grid.api;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.common.api.AbstractApiClient;
import com.gpwsofts.ffcalculator.mobile.common.executor.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @since 1.0.0
 * Api Cliente pour le chargement asynchrone des grilles, et des positions possibles pour chaque grille
 * Appelé par GridRepository
 *
 */
public class GridApiClient extends AbstractApiClient {
    private static final String TAG_NAME = "GridApiClient";
    private static GridApiClient instance;
    private final SingleLiveEvent<List<IGrid>> mGridChoices;
    private final SingleLiveEvent<List<Integer>> mPosChoices;
    private LoadClassesChoicesRunnable loadClassesChoicesRunnable;
    private LoadPosChoicesRunnable loadPosChoicesRunnable;
    private static final int JOB_TIMEOUT = 5000;

    private GridApiClient() {
        LogUtils.d(TAG_NAME, "instanciation de GridApiClient");
        mGridChoices = new SingleLiveEvent<>();
        mPosChoices = new SingleLiveEvent<>();
        LogUtils.d(TAG_NAME, "fin instanciation de GridApiClient");
    }

    public static GridApiClient getInstance() {
        if (null == instance)
            instance = new GridApiClient();
        return instance;
    }

    public LiveData<List<IGrid>> getGridChoicesLiveData() {
        return mGridChoices;
    }

    public LiveData<List<Integer>> getPosChoicesLiveData() {
        return mPosChoices;
    }

    public void loadGridChoicesAsync(String codeVue) {
        if (loadClassesChoicesRunnable != null) {
            loadClassesChoicesRunnable = null;
        }
        loadClassesChoicesRunnable = new LoadClassesChoicesRunnable(codeVue);
        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(loadClassesChoicesRunnable);
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
        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(loadPosChoicesRunnable);
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
        private final String codeVue;
        boolean cancelRequest;

        public LoadClassesChoicesRunnable(String codeVue) {
            this.codeVue = codeVue;
        }

        @Override
        public void run() {
            LogUtils.d(TAG_NAME, "debut du job asynchrone LoadClassesChoicesRunnable selon la vue <" + codeVue + ">");
            List<IGrid> listGridsForCodeVue = null;
            try {
                listGridsForCodeVue = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids(codeVue);
                LogUtils.v(TAG_NAME, " <" + listGridsForCodeVue.size() + "> vues chargees pour codeVue <" + codeVue + ">");
            } catch (Exception e){
                LogUtils.e(TAG_NAME, "echec du chargement de la liste des classes de courses par rapport a la vue", e);
                sendErrorToBackEnd(TAG_NAME, e);
            } finally {
                mGridChoices.postValue(listGridsForCodeVue);
                LogUtils.d(TAG_NAME, "fin du job asynchrone LoadClassesChoicesRunnable pour codeVue <" + codeVue + ">");
            }
        }

        private void cancelRequest() {
            LogUtils.v(TAG_NAME, "annulation de la requete");
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
            LogUtils.d(TAG_NAME, "debut du job asynchrone LoadPosChoicesRunnable selon le libelle <" + libelle + ">");
            List<Integer> posChoices = null;
            try{
                final String idClasse = FFCalculatorApplication.instance.getServicesManager().getGridService().getIdClasseFromLibelle(libelle);
                final IGrid myGrid = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids().stream().filter(grid -> grid.getCode().equals(idClasse)).findAny().orElse(null);
                if (myGrid != null) {
                    posChoices = IntStream.rangeClosed(1, myGrid.getMaxPos()).boxed().collect(Collectors.toList());
                }
            } catch (Exception e){
                LogUtils.e(TAG_NAME, "echec du chargement de la liste des positions possibles ", e);
                sendErrorToBackEnd(TAG_NAME, e);
            } finally {
                mPosChoices.postValue(posChoices);
                LogUtils.d(TAG_NAME, "fin du job asynchrone LoadPosChoicesRunnable selon le libelle <" + libelle + ">");
            }
        }

        private void cancelRequest() {
            LogUtils.v("TAGNAME", "annulation de la requete");
            cancelRequest = true;
        }
    }

}
