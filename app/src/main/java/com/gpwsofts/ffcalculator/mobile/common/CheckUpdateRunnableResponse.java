package com.gpwsofts.ffcalculator.mobile.common;

import com.gpwsofts.ffcalculator.mobile.model.LatestVersion;

/**
 * @deprecated  1.0.0 inutilisé
 */
public class CheckUpdateRunnableResponse extends AbstractRunnableResponse {
    private LatestVersion latestVersion;

    public LatestVersion getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(LatestVersion latestVersion) {
        this.latestVersion = latestVersion;
    }
}
