package com.gpwsofts.ffcalculator.mobile.services.client;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @since 1.0.0
 * Api Cliente pour le calcul asynchrone de la position au classement national
 * Appelé par OverAllPosRepository
 */
public class OverAllPosApiClient {
    private static final String TAG_NAME = "OverAllPosApiClient";
    private static OverAllPosApiClient instance;
    private final SingleLiveEvent<Integer> mPos;
    private GetPosRunnable getPosRunnable;
    private static final int JOB_TIMEOUT = 5000;

    private OverAllPosApiClient() {
        Log.i(TAG_NAME, "instanciation de OverAllPosApiClient");
        mPos = new SingleLiveEvent<Integer>();
        Log.i(TAG_NAME, "fin instanciation de OverAllPosApiClient");
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
        getPosRunnable = new GetPosRunnable(pts, classType);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(getPosRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            // annuler l'appel a l'API
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
            Log.i(TAG_NAME, "debut du job asynchrone GetPosRunnable");
            Response response = null;
            Integer pos = null;
            try {
                boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
                if (isWwwConnected) {
                    // reseau disponible
                    Log.d(TAG_NAME, "appel synchrone au service des positions et recuperation de la reponse");
                    Log.d(TAG_NAME, " arguments : pts = <" + pts + ">, type de classement = <" + classType + ">");

                    response = getPos(pts, classType).execute();
                    if (cancelRequest) {
                        Log.d(TAG_NAME, "cancelRequest true, retourne zboub");
                        return;
                    }
                    int responseCode = response.code();
                    Log.d(TAG_NAME, "responseCode du service des positions = <" + responseCode + ">");
                    if (responseCode == 200) {
                        pos = ((FFCPosResponse) response.body()).pos;
                        Log.d(TAG_NAME, "succes du calcul de la position pour ce capital de points = <" + pos + ">");
                        Log.d(TAG_NAME, "post de la nouvelle position en livedata");
                    } else {
                        // reponse pas 200
                        String error = response.errorBody().string();
                        Log.e(TAG_NAME, "echec du calcul de la position pour ce capital de points");
                        Log.e(TAG_NAME, "erreur " + error);
                        Log.d(TAG_NAME, "pos rendue et postee = <null>");
                        pos = null;
                        //TODO 1.0.0 envoyer au backend ?
                    }
                } else {
                    // pas de reseau
                    Log.e(TAG_NAME, "echec du calcul de la position - pas de reseau");
                    pos = null;
                    //TODO 1.0.0 peut etre dérouler une implementation locale ?
                }
            } catch (IOException e) {
                Log.e(TAG_NAME, "echec du calcul de la position pour ce capital de points");
                pos = null;
            } catch (Exception e) {
                Log.e(TAG_NAME, "echec du calcul de la position pour ce capital de points", e);
                pos = null;
            } finally {
                mPos.postValue(pos);
                Log.i(TAG_NAME, "fin du job asynchrone GetPosRunnable");
            }
        }

        private Call<FFCPosResponse> getPos(double pts, String classType) {
            return FFCalculatorApplication.instance.getServicesManager().getPosService().calcPos(pts, classType);
        }

        private void cancelRequest() {
            Log.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }
}
