package com.gpwsofts.ffcalculator.mobile.services.api.http;

import com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo.FFCPosRequest;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pts.pojo.FFCPointsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @since 1.0.0
 * Interface de la couche cliente API
 */
public interface IApiInterface {
    //TODO 1.0.0 uri et aures a mettre en parametre
    String URI_PTS = "ffcpoints.php";
    String URI_CLASSEMENT = "ffcclass.php";

    @POST(URI_PTS)
    Call<FFCPointsResponse> calcPts(@Body FFCPointsRequest request);

    @POST(URI_CLASSEMENT)
    Call<FFCPosResponse> calcPos(@Body FFCPosRequest request);
}
