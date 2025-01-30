package com.gpwsofts.ffcalculator.mobile.services.client;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.dao.FFCalculatorDatabase;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.exception.SwitchVueException;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.model.Vue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @since 1.0.0
 * Api Cliente pour le définition asynchrone de la vue
 * Appelé par VueRepository
 */
public class VueApiClient {
    private static final String TAG_NAME = "VueApiClient";
    private static final String SHARED_PREFS_APP_NAME = "FFCalculatorSharedPrefs";
    private static final String KEY_VUE = "vue";
    private static final String DEFAULT_VUE_VALUE = "G";
    private static final int JOB_TIMEOUT = 5000;
    private static VueApiClient instance;
    private final SingleLiveEvent<Vue> mVue;
    private SetVueRunnable setVueRunnable;


    private VueApiClient() {
        Log.i(TAG_NAME, "instanciation de VueApiClient");
        mVue = new SingleLiveEvent<Vue>();
        Log.v(TAG_NAME, "recherche dans les shared prefs de la valeur de <" + KEY_VUE + ">");
        final String currentCodeVue = FFCalculatorApplication.instance.getSharedPrefs().getSharedPrefsVue();
        Log.v(TAG_NAME, "set en liveData instance Vue = <" + currentCodeVue + ">");
        final Vue currentVue = FFCalculatorApplication.instance.getServicesManager().getVueService().createVue(currentCodeVue);
        mVue.setValue(currentVue);
        Log.i(TAG_NAME, "fin instanciation de VueApiClient");
    }

    public static VueApiClient getInstance() {
        if (null == instance)
            instance = new VueApiClient();
        return instance;
    }

    public LiveData<Vue> getVueLiveData() {
        return mVue;
    }

    public void setVueApiAsync(String vue) {
        if (setVueRunnable != null) {
            setVueRunnable = null;
        }
        setVueRunnable = new SetVueRunnable(vue);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(setVueRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            // annuler l'appel a l'API
            myHandler.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    /**
     * @since 1.0.0
     * Job permettant de mettre a jour la vue en shared preferences
     */
    private class SetVueRunnable implements Runnable {
        private final String codeVue;
        boolean cancelRequest;

        public SetVueRunnable(String codeVue) {
            this.codeVue = codeVue;
        }

        @Override
        public void run() {
            Vue newVue = null;
            try {
                Log.i(TAG_NAME, "debut du job asynchrone SetVueRunnable");
                // TODO 1.0.0 est ce que la vue selectionnee est compatible avec la liste des courses en cours ?
                //  Pas de bascule U17 si Open et inversement
                // Pas de bascule Open si y'a des courses U17
                // Pas de bascule U17 si ya des courses Open
                // FFCalculatorDatabase.getInstance().resultDao().getAllResults().getValue().stream().allMatch(result -> FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids().stream().filter(grid -> grid.getCode().equals(result.getIdClasse())).
                Log.d(TAG_NAME, "envoi de la cle valeur en shared prefs - <" + KEY_VUE + "> <" + codeVue + ">");
                // sharedPrefsEditor.putString(KEY_VUE, vue);
                if (FFCalculatorApplication.instance.getSharedPrefs().setSharedPrefsVue(codeVue)){
                    newVue = FFCalculatorApplication.instance.getServicesManager().getVueService().createVue(codeVue);
                    mVue.postValue(newVue);
                } else {
                    SwitchVueException sve = new SwitchVueException(FFCalculatorApplication.instance.getResources().getString(R.string.toast_update_vue_ko));
                    sve.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_update_vue_ko));
                    throw sve;
                }
            } catch (SwitchVueException sve) {
                Log.e(TAG_NAME, "probleme sur le job SetVueRunnable", sve);
                newVue = null;
                //TODO 1.0.0 prendre en consideration la valeur null pour tout observe
            }catch (Exception e) {
                Log.e(TAG_NAME, "probleme sur le job SetVueRunnable", e);
                newVue = null;
                //TODO 1.0.0 prendre en consideration la valeur null pour tout observe
            } finally {
                mVue.postValue(newVue);
                Log.i(TAG_NAME, "fin du job asynchrone SetVueRunnable");
            }
        }

        private void cancelRequest() {
            Log.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }
}
