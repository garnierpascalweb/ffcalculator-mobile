package com.gpwsofts.ffcalculator.mobile.services.pts;

import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;
import com.gpwsofts.ffcalculator.mobile.sharedprefs.FFCalculatorSharedPrefs;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;
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
        LogUtils.i(TAG_NAME, "demande de calcul des points pour sur la course de <" + place + "> pour une place de <" + pos + "> sur <" + prts + "> sur la course de <" + place + "> en serie <" + classe + ">");
        LogUtils.d(TAG_NAME, "construction de la requete");
        FFCPointsRequest request = new FFCPointsRequest();
        // se premunir d'injections sql - cote backend
        request.place = place;
        request.code = classe;
        request.pos = pos;
        request.prts = prts;
        LogUtils.d(TAG_NAME, "veritable appel http et call en retour");
        return FFCalculatorWebApi.getInstance().getApiService().calcPts(FFCalculatorSharedPrefs.id(),request);
    }

    @Override
    public Call<FFCPointsResponse> calcPts(FFCPointsRequest request) {
        // cest du retrofit !
        LogUtils.d(TAG_NAME, "veritable appel http et call en retour");
        return FFCalculatorWebApi.getInstance().getApiService().calcPts(FFCalculatorSharedPrefs.id(),request);
    }

    @Override
    public void clean() {

    }
}
