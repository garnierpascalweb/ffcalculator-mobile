package com.gpwsofts.ffcalculator.mobile.services.client;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.exception.UnreadableBodyException;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @since 1.0.0
 * Api Cliente pour le calcul asynchrone de la position au classement national
 * Appelé par OverAllPosRepository
 */
public class OverAllPosApiClient extends AbstractApiClient {
    private static final String TAG_NAME = "OverAllPosApiClient";
    private static OverAllPosApiClient instance;
    private final SingleLiveEvent<Integer> mPos;
    private GetPosRunnable getPosRunnable;
    private static final int JOB_TIMEOUT = 5000;

    private OverAllPosApiClient() {
        LogUtils.i(TAG_NAME, "instanciation de OverAllPosApiClient");
        mPos = new SingleLiveEvent<>();
        LogUtils.i(TAG_NAME, "fin instanciation de OverAllPosApiClient");
    }

    public static OverAllPosApiClient getInstance() {
        if (null == instance)
            instance = new OverAllPosApiClient();
        return instance;
    }

    public LiveData<Integer> getOverAllPosLiveData() {
        return mPos;
    }

    public void searchPosApiAsync(double pts, String classType) {
        if (getPosRunnable != null) {
            getPosRunnable = null;
        }
        LogUtils.d(TAG_NAME, "instantaition dun GetPosRunnable");
        getPosRunnable = new GetPosRunnable(pts, classType);
        LogUtils.d(TAG_NAME, "submit du runnable GetPosRunnable dans le pool de thread");
        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(getPosRunnable);
        LogUtils.d(TAG_NAME, "appel de cancel dans <" + JOB_TIMEOUT + "> ");
        AppExecutors.getInstance().networkIO().schedule(() -> {
            myHandler.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
        //TODO 1.0.0 revoir ce timeout
    }

    // Calcul de la position avec l'API
    private class GetPosRunnable implements Runnable {
        private final double pts;
        private final String classType;
        boolean cancelRequest;

        public GetPosRunnable(double pts, String classType) {
            this.pts = pts;
            this.classType = classType;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            LogUtils.i(TAG_NAME, "debut du job asynchrone GetPosRunnable");
            Response response;
            Integer pos = null;
            try {
                boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
                if (isWwwConnected) {
                    // reseau disponible
                    LogUtils.d(TAG_NAME, "appel synchrone au service des positions et recuperation de la reponse");
                    LogUtils.d(TAG_NAME, " arguments : pts = <" + pts + ">, type de classement = <" + classType + ">");

                    response = getPos(pts, classType).execute();
                    if (cancelRequest) {
                        LogUtils.d(TAG_NAME, "cancelRequest true, retourne zboub");
                        return;
                    }
                    int responseCode = response.code();
                    LogUtils.d(TAG_NAME, "responseCode du service des positions = <" + responseCode + ">");
                    if (responseCode == 200) {
                        if (response.body() != null) {
                            pos = ((FFCPosResponse) response.body()).pos;
                            LogUtils.d(TAG_NAME, "succes du calcul de la position pour ce capital de points = <" + pos + ">");
                            LogUtils.d(TAG_NAME, "post de la nouvelle position en livedata");
                        } else {
                            throw new UnreadableBodyException("impossible de lire le body de la reponse - code http " + responseCode);
                        }

                    } else {
                        // reponse pas 200
                        String error;
                        if (response.errorBody() != null) {
                            error = response.errorBody().string();
                            LogUtils.e(TAG_NAME, "echec du calcul de la position pour ce capital de points");
                            LogUtils.e(TAG_NAME, "erreur " + error);
                            LogUtils.d(TAG_NAME, "pos rendue et postee = <null>");
                        } else {
                            throw new UnreadableBodyException("impossible de lire le errorbody de la reponse - code http " + responseCode);
                        }
                    }
                } else {
                    // pas de reseau
                    LogUtils.e(TAG_NAME, "echec du calcul de la position - pas de reseau");
                    // already assigned to this value pos = null;
                    //TODO 1.0.0 peut etre dérouler une implementation locale ?
                }
            }  catch (Exception e) {
                LogUtils.e(TAG_NAME, "echec du calcul de la position pour ce capital de points : " + e.getClass().getSimpleName(), e);
                // already assigned to this value pos = null;
                sendErrorToBackEnd(TAG_NAME, e);
            } finally {
                mPos.postValue(pos);
                LogUtils.i(TAG_NAME, "fin du job asynchrone GetPosRunnable");
            }
        }

        private Call<FFCPosResponse> getPos(double pts, String classType) {
            return FFCalculatorApplication.instance.getServicesManager().getPosService().calcPos(pts, classType);
        }

        private void cancelRequest() {
            LogUtils.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }


}
