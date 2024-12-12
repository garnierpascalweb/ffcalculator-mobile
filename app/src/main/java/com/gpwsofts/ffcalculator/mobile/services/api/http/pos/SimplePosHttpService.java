package com.gpwsofts.ffcalculator.mobile.services.api.http.pos;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.services.api.http.AbstractHttpService;
import com.gpwsofts.ffcalculator.mobile.services.api.http.IApiInterface;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo.FFCPosRequest;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo.FFCPosResponse;

import retrofit2.Call;

/**
 * @since 1.0.0
 * emet un appel http POST pour calculer la position au classement national, en fonction de points et dun classType
 */
public class SimplePosHttpService extends AbstractHttpService implements IPosHttpService {
    private static final String TAG_NAME = "SimplePosService";

    public SimplePosHttpService() {
        initApiService();
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
        return apiService.calcPos(request);
    }
}
