package com.gpwsofts.ffcalculator.mobile.services.report.pojo;

public class FFCReportRequestFactory {
    public static FFCReportRequest createFFCReportRequest(String inCause){
        return new FFCReportRequest(inCause);
    }
}
