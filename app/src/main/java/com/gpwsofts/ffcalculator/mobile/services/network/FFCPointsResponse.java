package com.gpwsofts.ffcalculator.mobile.services.network;

import com.google.gson.annotations.SerializedName;

/**
 * @since 1.0.0
 * POJO pour une reponse de calcul de points
 */
public class FFCPointsResponse {
    @SerializedName("prts")
    public Integer prts;
    @SerializedName("pos")
    public Integer pos;
    @SerializedName("code")
    public String code;
    @SerializedName("pts")
    public Double pts;
    @SerializedName("message")
    public String message;
}
