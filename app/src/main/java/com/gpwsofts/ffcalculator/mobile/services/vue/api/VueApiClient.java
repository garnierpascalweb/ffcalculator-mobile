package com.gpwsofts.ffcalculator.mobile.services.vue.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.api.AbstractApiClient;
import com.gpwsofts.ffcalculator.mobile.common.exception.SwitchVueException;
import com.gpwsofts.ffcalculator.mobile.common.executor.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @since 1.0.0
 * Api Cliente pour le définition asynchrone de la vue
 * Appelé par VueRepository
 */
public class VueApiClient extends AbstractApiClient {
    private static final String TAG_NAME = "VueApiClient";
    private static final String KEY_VUE = "vue";
    private static final int JOB_TIMEOUT = 5000;
    private static VueApiClient instance;
    private final MutableLiveData<String> mCodeVue;
    private SetVueRunnable setVueRunnable;
    private GetVueRunnable getVueRunnable;


    private VueApiClient() {
        mCodeVue = new MutableLiveData<String>();
        // on initialise a G, on veut pas avoir a gérer de null sur getVueLiveData
        mCodeVue.setValue("G");
        //TODO 1.0.0 pas G en dur
    }

    public static VueApiClient getInstance() {
        if (null == instance)
            instance = new VueApiClient();
        return instance;
    }

    public LiveData<String> getCodeVueLiveData() {
        return mCodeVue;
    }

    public void loadCodeVueAsync(){
       if (getVueRunnable != null){
           getVueRunnable = null;
       }
        getVueRunnable = new GetVueRunnable();
        final Future<?> getVueRunnableFuture = AppExecutors.getInstance().networkIO().submit(getVueRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            getVueRunnableFuture.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void updateCodeVueAsync(String codeVue) {
        if (setVueRunnable != null) {
            setVueRunnable = null;
        }
        setVueRunnable = new SetVueRunnable(codeVue);
        final Future<?> setVueRunnableFuture = AppExecutors.getInstance().networkIO().submit(setVueRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            setVueRunnableFuture.cancel(true);
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
            try {
                LogUtils.d(TAG_NAME, "debut job asynchrone SetVueRunnable - codeVue <" + codeVue + ">");
                LogUtils.d(TAG_NAME, "envoi en shared prefs - <" + KEY_VUE + "> <" + codeVue + ">");
                if (FFCalculatorApplication.instance.getSharedPrefs().setSharedPrefsVue(codeVue)){
                    mCodeVue.postValue(codeVue);
                } else {
                    SwitchVueException sve = new SwitchVueException(FFCalculatorApplication.instance.getResources().getString(R.string.toast_update_vue_ko));
                    sve.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_update_vue_ko));
                    throw sve;
                }
            } catch (SwitchVueException sve) {
                LogUtils.e(TAG_NAME, "probleme sur le job SetVueRunnable", sve);
                sendErrorToBackEnd(TAG_NAME, sve);
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur le job SetVueRunnable", e);
                sendErrorToBackEnd(TAG_NAME, e);
            } finally {
                mCodeVue.postValue(codeVue);
                LogUtils.i(TAG_NAME, "fin du job asynchrone SetVueRunnable - vue updatee vers <" + codeVue + ">");
            }
        }

        private void cancelRequest() {
            LogUtils.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }

    private class GetVueRunnable implements Runnable {
        public GetVueRunnable(){

        }
        @Override
        public void run() {
            LogUtils.d(TAG_NAME, "debut du job asynchrone GetVueRunnable");
            final String currentCodeVue = FFCalculatorApplication.instance.getSharedPrefs().getSharedPrefsVue();
            mCodeVue.postValue(currentCodeVue);
            LogUtils.i(TAG_NAME, "fin du job asynchrone GetVueRunnable - vue <" + currentCodeVue + ">");
        }
    }
}