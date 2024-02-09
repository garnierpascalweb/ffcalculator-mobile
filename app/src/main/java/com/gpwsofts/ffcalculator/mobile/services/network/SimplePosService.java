package com.gpwsofts.ffcalculator.mobile.services.network;

import android.util.Log;

import retrofit2.Call;

public class SimplePosService extends AbstractHttpService implements IPosService{
    private static final String TAG_NAME = "SimplePosService";
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
        return apiService.calcPts(request);
    }
}
