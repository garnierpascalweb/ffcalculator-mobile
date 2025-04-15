package com.gpwsofts.ffcalculator.mobile.services.report;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequest;

/**
 * Service de report de crash
 * @since 1.0.0
 */
public interface IReportHttpService extends ICleanableService {
    void sendReport(FFCReportRequest request);
    void sendReportAsync(String tagName, Exception e);
}
