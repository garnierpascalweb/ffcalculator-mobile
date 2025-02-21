package com.gpwsofts.ffcalculator.mobile;

import com.gpwsofts.ffcalculator.mobile.exception.FFCalculatorException;

public class AddResultException extends FFCalculatorException {
    public AddResultException() {
    }

    public AddResultException(Throwable cause) {
        super(cause);
    }

    public AddResultException(String message) {
        super(message);
    }

    public AddResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
