package com.gpwsofts.ffcalculator.mobile.services.network;

import retrofit2.Call;

public class SimplePtsService extends AbstractHttpService implements IPtsService {

    @Override
    public Call<FFCPointsResponse> calcPts(String place, int pos, int prts, String classe) {
        // cest du retrofit !
        IApiInterface apiService = retrofit.create(IApiInterface.class);
        FFCPointsRequest request = new FFCPointsRequest();
        request.code = classe;
        request.pos = pos;
        request.prts = prts;
         return apiService.calcPts(request);

    }
}
