package com.gpwsofts.ffcalculator.mobile.exception;

/**
 * Exception levee lors d'un appel http lorsqu'un body ne peut pas etre lu
 * @since 1.0.0
 */
public class UnreadableBodyException extends FFCalculatorException {
    public UnreadableBodyException(String message) {
        super(message);
    }
}
