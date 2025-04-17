package com.gpwsofts.ffcalculator.mobile.common.exception;

/**
 * Classe mere de toutes les exceptions
 * @since 1.0.0
 */
public class FFCalculatorException extends Exception {
    protected String toastMessage;

    public FFCalculatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FFCalculatorException(String message) {
        super(message);
    }

    public FFCalculatorException(Throwable cause) {
        super(cause);
    }

    public FFCalculatorException() {

    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
    }

}
