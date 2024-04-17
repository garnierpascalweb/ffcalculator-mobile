package com.gpwsofts.ffcalculator.mobile.services.api.http.pts;

import com.gpwsofts.ffcalculator.mobile.services.api.http.pts.pojo.FFCPointsResponse;

import retrofit2.Call;

/**
 * @since 1.0.0
 * Interface de calcul des points
 */
public interface IPtsHttpService {
    Call<FFCPointsResponse> calcPts(String place, int pos, int prts, String classe);
}
