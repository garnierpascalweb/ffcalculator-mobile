package com.gpwsofts.ffcalculator.mobile.services.pos.api;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.common.api.AbstractApiClient;
import com.gpwsofts.ffcalculator.mobile.common.exception.OverAllPosException;
import com.gpwsofts.ffcalculator.mobile.common.exception.UnreadableBodyException;
import com.gpwsofts.ffcalculator.mobile.common.executor.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
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
        mPos = new SingleLiveEvent<>();
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
        final Future<?> getPosRunnableFuture = AppExecutors.getInstance().networkIO().submit(getPosRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            getPosRunnableFuture.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
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
            Integer pos = null;
            try {
                LogUtils.d(TAG_NAME, "debut du job asynchrone GetPosRunnable - pts = <" + pts + "> classType = <" + classType + ">");
                boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
                if (isWwwConnected) {
                    final Response<FFCPosResponse> response = FFCalculatorApplication.instance.getServicesManager().getPosService().calcPos(pts, classType).execute();
                    if (cancelRequest) {
                        LogUtils.d(TAG_NAME, "cancelRequest true, retourne zboub");
                        return;
                    }
                    final boolean isSuccessful = response.isSuccessful();
                    final int responseCode = response.code();
                    if (isSuccessful) {
                        final FFCPosResponse body = response.body();
                        if (body != null) {
                            pos = body.pos;
                        } else {
                            throw new UnreadableBodyException("impossible de lire le body de la reponse - code http " + responseCode);
                        }
                    } else {
                        final ResponseBody body = response.errorBody();
                        if (body != null) {
                            final String errorBodyContent = body.string();
                            throw new OverAllPosException("probleme sur le calcul de la position sur le classement " + classType + " pour le capital " + pts + " - " + errorBodyContent);
                        } else {
                            throw new UnreadableBodyException("impossible de lire le errorbody de la reponse - code http " + responseCode);
                        }
                    }
                } else {
                    // pas de reseau
                    LogUtils.e(TAG_NAME, "echec du calcul de la position - pas de reseau");
                    //TODO 1.0.0 peut etre dérouler une implementation locale ?
                }
            }  catch (Exception e) {
                LogUtils.e(TAG_NAME, "echec du calcul de la position pour ce capital de points : " + e.getClass().getSimpleName(), e);
                // already assigned to this value pos = null;
                sendErrorToBackEnd(TAG_NAME, e);
            } finally {
                mPos.postValue(pos);
                LogUtils.i(TAG_NAME, "fin du job asynchrone GetPosRunnable - pts = <" + pts + "> classType = <" + classType + "> - valeur rendue <" + pos + ">");
            }
        }

        private void cancelRequest() {
            LogUtils.v(TAG_NAME, "annulation de la requete");
            cancelRequest = true;
        }
    }


}
