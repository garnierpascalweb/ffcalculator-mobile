package com.gpwsofts.ffcalculator.mobile.services.update.pojo;

import com.google.gson.annotations.SerializedName;

public class FFCUpdateCkeckerResponse {
    @SerializedName("latest_version_code")
    public int latestVersionCode;
    @SerializedName("latest_version_name")
    public String latestVersionName;
    @SerializedName("download_url")
    public String downloadUrl;


}
