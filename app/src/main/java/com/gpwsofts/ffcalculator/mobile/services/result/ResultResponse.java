package com.gpwsofts.ffcalculator.mobile.services.result;

import com.gpwsofts.ffcalculator.mobile.dao.Result;

/**
 * La reponse Result du service Result
 */
public class ResultResponse {
    protected Result result;
    protected boolean status;
    protected String message;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultResponse(){

    }
}
