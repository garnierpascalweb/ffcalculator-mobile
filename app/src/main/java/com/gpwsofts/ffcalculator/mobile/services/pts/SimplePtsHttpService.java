package com.gpwsofts.ffcalculator.mobile.services.pts;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;
import com.gpwsofts.ffcalculator.mobile.www.FFCalculatorWebApi;

import retrofit2.Call;

/**
 * @since 1.0.0
 * emet un appel http POST pour calculer les points rendus pour une course (en fonction de lieu, pos, prts, classe)
 */
public class SimplePtsHttpService  implements IPtsHttpService {
    private static final String TAG_NAME = "SimplePtsService";

    public SimplePtsHttpService() {

    }

    @Override
    public Call<FFCPointsResponse> calcPts(String place, int pos, int prts, String classe) {
        // cest du retrofit !
        Log.i(TAG_NAME, "demand ede calcul des points pour une place de <" + pos + "> sur <" + prts + "> sur la course de <" + place + "> en serie <" + classe + ">");
        Log.d(TAG_NAME, "construction de la requete");
        FFCPointsRequest request = new FFCPointsRequest();
        request.code = classe;
        request.pos = pos;
        request.prts = prts;
        Log.d(TAG_NAME, "veritable appel http et call en retour");
        return FFCalculatorWebApi.getInstance().getApiService().calcPts(request);
    }
}
