package com.gpwsofts.ffcalculator.mobile.www;

import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosRequest;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequest;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportResponse;
import com.gpwsofts.ffcalculator.mobile.services.update.pojo.FFCUpdateCkeckerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @since 1.0.0
 * Interface de la couche cliente API
 */
public interface IApiInterface {
    //TODO 1.0.0 uri et aures a mettre en parametre
    public static final String URI_PTS = "ffcpoints.php";
    public static final String URI_CLASSEMENT = "ffcclass.php";
    public static final String URI_CRASH_REPORT = "ffccrashreport.php";
    public static final String URI_LATEST_VERSION = "latest-version.json";

    @POST(URI_PTS)
    Call<FFCPointsResponse> calcPts(@Header("uuid") String uuid, @Body FFCPointsRequest request);
    @POST(URI_CLASSEMENT)
    Call<FFCPosResponse> calcPos(@Header("uuid") String uuid, @Body FFCPosRequest request);
    @POST(URI_CRASH_REPORT)
    Call<FFCReportResponse> sendReport(@Header("uuid") String uuid, FFCReportRequest request);
    @GET(URI_LATEST_VERSION)
    Call<FFCUpdateCkeckerResponse> checkForUpdates();
}
