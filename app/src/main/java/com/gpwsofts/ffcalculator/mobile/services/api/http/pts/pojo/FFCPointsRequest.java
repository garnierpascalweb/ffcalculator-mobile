package com.gpwsofts.ffcalculator.mobile.services.api.http.pts.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @since 1.0.0
 * POJO pour une requete de calcul de points
 */
public class FFCPointsRequest {

    @SerializedName("prts")
    public Integer prts;
    @SerializedName("pos")
    public Integer pos;
    @SerializedName("code")
    public String code;

}
