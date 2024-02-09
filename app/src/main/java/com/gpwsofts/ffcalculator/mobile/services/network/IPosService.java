package com.gpwsofts.ffcalculator.mobile.services.network;

import retrofit2.Call;

public interface IPosService {
    public Call<FFCPosResponse> calcPos(double pts, String classType);
}
