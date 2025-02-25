package com.gpwsofts.ffcalculator.mobile.services.report;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequest;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportResponse;

import retrofit2.Call;

/**
 * Service de report de crash
 * @since 1.0.0
 */
public interface IReportHttpService extends ICleanableService {
    Call<FFCReportResponse> sendReport(FFCReportRequest request);
}
