package com.gpwsofts.ffcalculator.mobile.services.report.pojo;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.gpwsofts.ffcalculator.mobile.BuildConfig;

/**
 * @since 1.0.0
 * POJO pour une requete de report de bug
 */
public class FFCReportRequest {
    @SerializedName("versionRelease")
    public String versionRelease;
    @SerializedName("appVersion")
    public String appVersion;
    @SerializedName("model")
    public String model;
    @SerializedName("cause")
    public String cause;

    protected FFCReportRequest(String incause){
        versionRelease = Build.VERSION.RELEASE;
        appVersion = BuildConfig.VERSION_NAME;
        model = Build.MODEL;
        if (incause != null){
            Gson gson = new Gson();
            cause = gson.toJson(incause);
            gson = null;
        }
    }
}
