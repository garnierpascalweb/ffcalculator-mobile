package com.gpwsofts.ffcalculator.mobile.services.api.http.pos.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @since 1.0.0
 * POJO pour une reponse de classement national
 */
public class FFCPosResponse {
    @SerializedName("pts")
    public Double pts;
    @SerializedName("pos")
    public Integer pos;
    @SerializedName("classType")
    public String classType;
    @SerializedName("message")
    public String message;


}
