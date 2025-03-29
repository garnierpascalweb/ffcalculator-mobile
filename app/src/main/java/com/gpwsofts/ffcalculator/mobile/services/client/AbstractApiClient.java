package com.gpwsofts.ffcalculator.mobile.services.client;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequestFactory;

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
        FFCalculatorApplication.instance.getServicesManager().getReportService().sendReport(FFCReportRequestFactory.createFFCReportRequest(tagName, currentException));
    }
}
