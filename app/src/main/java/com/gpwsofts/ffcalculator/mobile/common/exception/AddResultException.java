package com.gpwsofts.ffcalculator.mobile.common.exception;

/**
 * Exception à l'ajout d'un resultat
 * @since 1.0.0
 */
public class AddResultException extends FFCalculatorException {
    public AddResultException() {

    }
    public AddResultException(String message) {
        super(message);
    }

    public AddResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
