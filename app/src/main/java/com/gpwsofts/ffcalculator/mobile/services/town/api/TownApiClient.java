package com.gpwsofts.ffcalculator.mobile.services.town.api;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.common.api.AbstractApiClient;
import com.gpwsofts.ffcalculator.mobile.common.executor.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TownApiClient extends AbstractApiClient {
    private static final String TAG_NAME = "TownApiClient";
    private static TownApiClient instance;
    private final SingleLiveEvent<List<String>> mTownChoices;
    private TownApiClient.LoadTownsChoicesRunnable loadTownChoicesRunnable;
    private static final int JOB_TIMEOUT = 5000;

    private TownApiClient() {
        LogUtils.d(TAG_NAME, "instanciation de TownApiClient");
        mTownChoices = new SingleLiveEvent<>();
        LogUtils.d(TAG_NAME, "fin instanciation de TownApiClient");
    }

    public static TownApiClient getInstance() {
        if (null == instance)
            instance = new TownApiClient();
        return instance;
    }

    public LiveData<List<String>> getTownChoicesLiveData() {
        return mTownChoices;
    }

    public void loadTownChoicesAsync() {
        if (loadTownChoicesRunnable != null) {
            loadTownChoicesRunnable = null;
        }
        loadTownChoicesRunnable = new TownApiClient.LoadTownsChoicesRunnable();
        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(loadTownChoicesRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            myHandler.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
        //TODO 1.0.0 revoir ce timeout
    }


    /**
     * @since 1.0.0
     * Job qui permet le chargement asynchrone de la liste des villes sélectionnables à l'interface,
     */
    private class LoadTownsChoicesRunnable implements Runnable {
        boolean cancelRequest;
        public LoadTownsChoicesRunnable() {

        }
        @Override
        public void run() {
            List<String> towns = null;
            int nb = 0;
            try {
                LogUtils.d(TAG_NAME, "debut du job asynchrone LoadTownsChoicesRunnable");
                towns = FFCalculatorApplication.instance.getServicesManager().getTownService().getTowns();
                nb = towns.size();
            } catch (Exception e){
                LogUtils.e(TAG_NAME, "echec du chargement de la liste des villes", e);
                towns = null;
                sendErrorToBackEnd(TAG_NAME, e);
            } finally {
                mTownChoices.postValue(towns);
                LogUtils.i(TAG_NAME, "fin du job asynchrone LoadTownsChoicesRunnable - <" + nb + "> towns chargees");
            }
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }
}
