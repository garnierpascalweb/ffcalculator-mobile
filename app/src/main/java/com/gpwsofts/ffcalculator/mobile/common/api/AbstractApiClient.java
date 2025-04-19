package com.gpwsofts.ffcalculator.mobile.common.api;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequestFactory;


/**
 *
 */
public abstract class AbstractApiClient implements IApiClient {


    /**
     * @param tagName
     * @param currentException
     */
    @Override
    public void sendErrorToBackEnd(String tagName, Exception currentException){
        FFCalculatorApplication.instance.getServicesManager().getReportService().sendReport(FFCReportRequestFactory.createFFCReportRequest(tagName, currentException));
    }
}
