package com.gpwsofts.ffcalculator.mobile.services.report.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @since 1.0.0
 * POJO pour une requete de report de bug
 */
public class FFCReportRequest {
    @SerializedName("apiLevel")
    public Integer apiLevel;
    @SerializedName("versionCode")
    public Integer versionCode;
    @SerializedName("versionName")
    public String versionName;
    @SerializedName("tagName")
    public String tagName;
    @SerializedName("cause")
    public String cause;
}
