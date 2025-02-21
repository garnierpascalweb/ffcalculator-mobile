package com.gpwsofts.ffcalculator.mobile.common;

/**
 * Couche d'abstraction pour une reponse d'un Runnable
 * Toutes les responses de Runnable ont pour point commun un isOk et un message
 * @since 1.0.0
 */
public class AbstractRunnableResponse implements IRunnableResponse {
    protected String message;
    protected boolean isOk;

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
