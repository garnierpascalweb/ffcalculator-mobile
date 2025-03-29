package com.gpwsofts.ffcalculator.mobile.services.report.pojo;

public class FFCReportRequestFactory {
    public static FFCReportRequest createFFCReportRequest(String tagName, Exception e){
        return new FFCReportRequest(tagName, e);
    }
}
