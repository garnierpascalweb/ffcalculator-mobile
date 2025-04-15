package com.gpwsofts.ffcalculator.mobile.services.client;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.exception.SwitchVueException;
import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

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
    private final SingleLiveEvent<Vue> mVue;
    private SetVueRunnable setVueRunnable;


    private VueApiClient() {
        LogUtils.i(TAG_NAME, "instanciation de VueApiClient");
        mVue = new SingleLiveEvent<>();
        LogUtils.v(TAG_NAME, "recherche dans les shared prefs de la valeur de <" + KEY_VUE + ">");
        final String currentCodeVue = FFCalculatorApplication.instance.getSharedPrefs().getSharedPrefsVue();
        LogUtils.v(TAG_NAME, "set en liveData instance Vue = <" + currentCodeVue + ">");
        final Vue currentVue = FFCalculatorApplication.instance.getServicesManager().getVueService().getVueInstance(currentCodeVue);
        mVue.setValue(currentVue);
        LogUtils.i(TAG_NAME, "fin instanciation de VueApiClient");
    }

    public static VueApiClient getInstance() {
        if (null == instance)
            instance = new VueApiClient();
        return instance;
    }

    public LiveData<Vue> getVueLiveData() {
        return mVue;
    }

    public void setVueApiAsync(String codeVue) {
        if (setVueRunnable != null) {
            setVueRunnable = null;
        }
        setVueRunnable = new SetVueRunnable(codeVue);
        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(setVueRunnable);
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
                LogUtils.i(TAG_NAME, "debut du job asynchrone SetVueRunnable");
                LogUtils.d(TAG_NAME, "envoi de la cle valeur en shared prefs - <" + KEY_VUE + "> <" + codeVue + ">");
                // sharedPrefsEditor.putString(KEY_VUE, vue);
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
                LogUtils.i(TAG_NAME, "fin du job asynchrone SetVueRunnable");
            }
        }

        private void cancelRequest() {
            LogUtils.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }
}
