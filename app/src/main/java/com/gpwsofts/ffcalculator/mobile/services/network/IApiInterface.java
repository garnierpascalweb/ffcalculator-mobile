package com.gpwsofts.ffcalculator.mobile.services.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @since 1.0.0
 * Interface de la couche cliente API
 */
public interface IApiInterface {

    @POST("ffcpoints.php")
    Call<FFCPointsResponse> calcPts(@Body FFCPointsRequest request);

    @POST("ffcclass.php")
    Call<FFCPosResponse> calcPos(@Body FFCPosRequest request);
}
