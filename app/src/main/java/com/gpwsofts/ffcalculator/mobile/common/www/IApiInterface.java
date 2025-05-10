package com.gpwsofts.ffcalculator.mobile.common.www;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosRequest;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @since 1.0.0
 * Interface de la couche cliente API
 */
public interface IApiInterface {
    String URI_PTS = BuildConfig.API_URI_PTS;
    String URI_CLASSEMENT = BuildConfig.API_URI_CLASS;
    String URI_CRASH_REPORT = BuildConfig.API_URI_REPORT;

    @POST(URI_PTS)
    Call<FFCPointsResponse> calcPts(@Header("uuid") String uuid, @Header("env") String env, @Body FFCPointsRequest request);
    @POST(URI_CLASSEMENT)
    Call<FFCPosResponse> calcPos(@Header("uuid") String uuid, @Header("env") String env, @Body FFCPosRequest request);
    @POST(URI_CRASH_REPORT)
    Call<Void> sendReport(@Header("uuid") String uuid, @Header("env") String env, @Body FFCReportRequest request);
}
