package com.gpwsofts.ffcalculator.mobile.services.result;

public class ResultServiceException extends Exception {
    public ResultServiceException(String message){
        super(message);
    }

    public ResultServiceException(String message, Throwable t){
        super(message,t);
    }
}
