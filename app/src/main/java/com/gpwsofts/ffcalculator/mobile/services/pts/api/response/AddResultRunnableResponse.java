package com.gpwsofts.ffcalculator.mobile.services.pts.api.response;

import com.gpwsofts.ffcalculator.mobile.common.api.response.AbstractRunnableResponse;
import com.gpwsofts.ffcalculator.mobile.dao.Result;

/**
 * Reponse pour le runnable AddResultRunnable
 * @since 1.0.0
 */
public class AddResultRunnableResponse extends AbstractRunnableResponse {
    protected Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
