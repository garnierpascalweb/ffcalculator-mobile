package com.gpwsofts.ffcalculator.mobile.model;

/**
 * POJO pour la requete de check des nouvelles versions
 * @since 1.0.0
 */
public class LatestVersion {
    private int latestVersionCode;
    private String latestVersionName;
    private String downloadUrl;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getLatestVersionCode() {
        return latestVersionCode;
    }

    public void setLatestVersionCode(int latestVersionCode) {
        this.latestVersionCode = latestVersionCode;
    }

    public String getLatestVersionName() {
        return latestVersionName;
    }

    public void setLatestVersionName(String latestVersionName) {
        this.latestVersionName = latestVersionName;
    }
}
