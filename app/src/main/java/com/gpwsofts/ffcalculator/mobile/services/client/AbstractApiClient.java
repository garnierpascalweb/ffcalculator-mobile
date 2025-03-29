package com.gpwsofts.ffcalculator.mobile.services.client;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequestFactory;
import com.gpwsofts.ffcalculator.mobile.sharedprefs.FFCalculatorSharedPrefs;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;
import com.gpwsofts.ffcalculator.mobile.www.FFCalculatorWebApi;

/**
 * Méthodes communes a toutes les API CLientes HTTP
 * @since 1.0.0
 */
public abstract class AbstractApiClient implements IApiClient {
    private static final String TAG_NAME = "AbstractApiClient";
    /**
     * Envoi d'éventuelles erreurs au backend
     * @since 1.0.0
     * @param cause cause la cause de l'erreur
     */
    @Override
    public void sendErrorToBackEnd(String tagName, Exception currentException){
        try {
            LogUtils.d(TAG_NAME, "envoi de la cause au backend");
            if (FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected()) {
                FFCalculatorWebApi.getInstance().getApiService().sendReport(FFCalculatorSharedPrefs.id(), BuildConfig.FLAVOR, FFCReportRequestFactory.createFFCReportRequest(TAG_NAME,currentException));
            } else {
                LogUtils.d(TAG_NAME, "pas de reseau");
            }
        } catch (Exception e){
            LogUtils.w(TAG_NAME, "erreur non critique sur l'envoi d'un rapport de crash ", e);
        } finally {
            LogUtils.d(TAG_NAME, "fin d'envoi de la cause au backend");
        }
    }
}
