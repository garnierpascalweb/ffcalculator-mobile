package com.gpwsofts.ffcalculator.mobile.services.pos;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosRequest;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.sharedprefs.FFCalculatorSharedPrefs;
import com.gpwsofts.ffcalculator.mobile.www.FFCalculatorWebApi;

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
        // cest du retrofit !
        Log.i(TAG_NAME, "demande de calcul du classement pour <" + pts + ">  points sur le classement <" + classType + ">");
        Log.d(TAG_NAME, "construction de la requete");
        FFCPosRequest request = new FFCPosRequest();
        request.pts = pts;
        request.classType = classType;
        Log.d(TAG_NAME, "veritable appel http et call en retour");
        return FFCalculatorWebApi.getInstance().getApiService().calcPos(FFCalculatorSharedPrefs.id(), request);
    }
}
