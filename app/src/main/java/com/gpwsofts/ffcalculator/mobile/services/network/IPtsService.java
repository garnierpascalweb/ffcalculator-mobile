package com.gpwsofts.ffcalculator.mobile.services.network;

import retrofit2.Call;

/**
 * @since 1.0.0
 * Interface de calcul des points
 */
public interface IPtsService {
    public Call<FFCPointsResponse> calcPts(String place, int pos, int prts, String classe);
}
