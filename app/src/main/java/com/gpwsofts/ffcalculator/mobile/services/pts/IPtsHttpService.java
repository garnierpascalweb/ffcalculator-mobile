package com.gpwsofts.ffcalculator.mobile.services.pts;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;

import retrofit2.Call;

/**
 * @since 1.0.0
 * Interface de calcul des points
 */
public interface IPtsHttpService extends ICleanableService {
    Call<FFCPointsResponse> calcPts(String place, int pos, int prts, String classe);
    Call<FFCPointsResponse> calcPts(FFCPointsRequest request);
}
