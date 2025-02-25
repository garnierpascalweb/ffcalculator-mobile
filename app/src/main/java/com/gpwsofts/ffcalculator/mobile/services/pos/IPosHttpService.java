package com.gpwsofts.ffcalculator.mobile.services.pos;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;

import retrofit2.Call;

public interface IPosHttpService extends ICleanableService {
    Call<FFCPosResponse> calcPos(double pts, String classType);
}
