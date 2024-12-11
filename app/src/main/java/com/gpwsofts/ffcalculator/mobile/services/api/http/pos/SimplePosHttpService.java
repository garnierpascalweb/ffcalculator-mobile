package com.gpwsofts.ffcalculator.mobile.services.api.http.pos;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.services.api.http.AbstractHttpService;
import com.gpwsofts.ffcalculator.mobile.services.api.http.IApiInterface;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo.FFCPosRequest;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo.FFCPosResponse;

import retrofit2.Call;

public class SimplePosHttpService extends AbstractHttpService implements IPosHttpService {
    private static final String TAG_NAME = "SimplePosService";

    public SimplePosHttpService() {
        retrofit = getClient();
    }

    @Override
    public Call<FFCPosResponse> calcPos(double pts, String classType) {
        // cest du retrofit !
        Log.i(TAG_NAME, "demande de calcul du classement pour <" + pts + ">  points sur le classement <" + classType + ">");
        //TODO 1.0.0 nul de recreer une instance
        IApiInterface apiService = retrofit.create(IApiInterface.class);
        Log.d(TAG_NAME, "construction de la requete");
        FFCPosRequest request = new FFCPosRequest();
        request.pts = pts;
        request.classType = classType;
        Log.d(TAG_NAME, "veritable appel http et call en retour");
        return apiService.calcPos(request);
    }
}
