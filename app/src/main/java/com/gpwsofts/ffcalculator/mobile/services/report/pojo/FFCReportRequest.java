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
    @SerializedName("model")
    public String model;
    @SerializedName("osVersion")
    public String osVersion;
    @SerializedName("appVersion")
    public String appVersion;
    @SerializedName("tagName")
    public String tagName;
    @SerializedName("typeError")
    public String typeError;
    @SerializedName("causeError")
    public String causeError;

    protected FFCReportRequest(String intagname, Exception e){
        model = Build.MODEL;
        osVersion = Build.VERSION.RELEASE;
        appVersion = BuildConfig.VERSION_NAME;
        tagName = intagname;
        final String incause = e.getMessage();
        if (incause != null){
            Gson gson = new Gson();
            causeError = gson.toJson(incause);
        }
        typeError = e.getClass().getSimpleName();
    }
}
