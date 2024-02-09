package com.gpwsofts.ffcalculator.mobile.services.network;

import com.google.gson.annotations.SerializedName;

public class FFCPointsResponse {
    @SerializedName("prts")public Integer prts;
    @SerializedName("pos")public Integer pos;
    @SerializedName("code")public String code;
    @SerializedName("pts")public Double pts;
    @SerializedName("message")public String message;
}
