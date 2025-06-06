package com.gpwsofts.ffcalculator.mobile.services.pos;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.common.sharedprefs.FFCalculatorSharedPrefs;
import com.gpwsofts.ffcalculator.mobile.common.www.FFCalculatorWebApi;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosRequest;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;

import retrofit2.Call;

/**
 * @since 1.0.0
 * emet un appel http POST pour calculer la position au classement national, en fonction de points et dun classType
 */
public class SimplePosHttpService  implements IPosHttpService {
    private static final String TAG_NAME = "SimplePosService";

    public SimplePosHttpService() {

    }

    @Override
    public Call<FFCPosResponse> calcPos(double pts, String classType) {
        FFCPosRequest request = new FFCPosRequest();
        request.pts = pts;
        request.classType = classType;
        return FFCalculatorWebApi.getInstance().getApiService().calcPos(FFCalculatorSharedPrefs.id(), BuildConfig.FLAVOR, request);
    }

    @Override
    public void clean() {

    }
}
