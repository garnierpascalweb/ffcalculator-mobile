package com.gpwsofts.ffcalculator.mobile.services.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Interface de la couche cliente API
 */
public interface IApiInterface {

    @POST
    Call<FFCPointsResponse> calcPts(@Body FFCPointsRequest request);
}
