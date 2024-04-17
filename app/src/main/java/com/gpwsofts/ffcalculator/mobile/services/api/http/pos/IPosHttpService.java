package com.gpwsofts.ffcalculator.mobile.services.api.http.pos;

import com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo.FFCPosResponse;

import retrofit2.Call;

public interface IPosHttpService {
    Call<FFCPosResponse> calcPos(double pts, String classType);
}
