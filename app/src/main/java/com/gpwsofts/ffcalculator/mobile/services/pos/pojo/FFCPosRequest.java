package com.gpwsofts.ffcalculator.mobile.services.pos.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @since 1.0.0
 * POJO pour une requete pour donner un classement national
 */
public class FFCPosRequest {
    @SerializedName("pts")
    public Double pts;
    @SerializedName("classType")
    public String classType;
}
