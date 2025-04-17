package com.gpwsofts.ffcalculator.mobile.services.pts;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;
import com.gpwsofts.ffcalculator.mobile.common.sharedprefs.FFCalculatorSharedPrefs;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.common.www.FFCalculatorWebApi;

import retrofit2.Call;

/**
 * @since 1.0.0
 * emet un appel http POST pour calculer les points rendus pour une course (en fonction de lieu, pos, prts, classe)
 */
public class SimplePtsHttpService  implements IPtsHttpService {
    private static final String TAG_NAME = "SimplePtsService";

    @Override
    public Call<FFCPointsResponse> calcPts(FFCPointsRequest request) {
        // cest du retrofit !
        LogUtils.d(TAG_NAME, "veritable appel http et call en retour");
        return FFCalculatorWebApi.getInstance().getApiService().calcPts(FFCalculatorSharedPrefs.id(),BuildConfig.FLAVOR,request);
    }

    @Override
    public void clean() {

    }
}
