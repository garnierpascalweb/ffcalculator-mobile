package com.gpwsofts.ffcalculator.mobile.services.vue.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.common.api.AbstractApiClient;
import com.gpwsofts.ffcalculator.mobile.common.exception.SwitchVueException;
import com.gpwsofts.ffcalculator.mobile.common.executor.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.model.vue.IVue;

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
    private final SingleLiveEvent<IVue> mVue;
    //private final MutableLiveData<IVue> mVue;
    private SetVueRunnable setVueRunnable;
    private GetVueRunnable getVueRunnable;


    private VueApiClient() {
        mVue = new SingleLiveEvent<IVue>();
        // on initialise a G, on veut pas avoir a gérer de null sur getVueLiveData
        mVue.setValue(FFCalculatorApplication.instance.getServicesManager().getVueService().getVueInstance("G"));
    }

    public static VueApiClient getInstance() {
        if (null == instance)
            instance = new VueApiClient();
        return instance;
    }

    public LiveData<IVue> getVueLiveData() {
        return mVue;
    }

    public void loadVueAsync(){
       if (getVueRunnable != null){
           getVueRunnable = null;
       }
        getVueRunnable = new GetVueRunnable();
        final Future<?> getVueRunnableFuture = AppExecutors.getInstance().networkIO().submit(getVueRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            getVueRunnableFuture.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void updateVueFromMenuAsync(int itemId) {
        if (setVueRunnable != null) {
            setVueRunnable = null;
        }
        setVueRunnable = new SetVueRunnable(itemId);
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
        private final int itemId;
        boolean cancelRequest;

        public SetVueRunnable(int itemId) {
            this.itemId = itemId;
        }

        @Override
        public void run() {
            IVue newVue = null;
            String codeVue = "";
            try {
                LogUtils.d(TAG_NAME, "debut job asynchrone SetVueRunnable - itemId <" + itemId + ">");
                codeVue = FFCalculatorApplication.instance.getServicesManager().getVueService().getCodeVueFromMenuItem(itemId);
                LogUtils.d(TAG_NAME, "envoi en shared prefs du code vue associée au idemId <" + itemId + "> - <" + KEY_VUE + "> <" + codeVue + ">");
                if (FFCalculatorApplication.instance.getSharedPrefs().setSharedPrefsVue(codeVue)){
                    newVue = FFCalculatorApplication.instance.getServicesManager().getVueService().getVueInstance(codeVue);
                    mVue.postValue(newVue);
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
                mVue.postValue(newVue);
                LogUtils.i(TAG_NAME, "fin du job asynchrone SetVueRunnable - itemId <" + itemId + "> - vue updatee vers <" + codeVue + ">");
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
            final IVue currentVue = FFCalculatorApplication.instance.getServicesManager().getVueService().getVueInstance(currentCodeVue);
            mVue.postValue(currentVue);
            LogUtils.i(TAG_NAME, "fin du job asynchrone GetVueRunnable - vue <" + currentVue + ">");
        }
    }
}
