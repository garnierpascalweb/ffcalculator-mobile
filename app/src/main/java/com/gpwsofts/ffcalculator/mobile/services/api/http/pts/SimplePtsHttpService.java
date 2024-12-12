package com.gpwsofts.ffcalculator.mobile.services.api.http.pts;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.services.api.http.AbstractHttpService;
import com.gpwsofts.ffcalculator.mobile.services.api.http.IApiInterface;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.api.http.pts.pojo.FFCPointsResponse;

import retrofit2.Call;

/**
 * @since 1.0.0
 * emet un appel http POST pour calculer les points rendus pour une course (en fonction de lieu, pos, prts, classe)
 */
public class SimplePtsHttpService extends AbstractHttpService implements IPtsHttpService {
    private static final String TAG_NAME = "SimplePtsService";

    public SimplePtsHttpService() {
        initApiService();
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
        return apiService.calcPts(request);
    }
}
