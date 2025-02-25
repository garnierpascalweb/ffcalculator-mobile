package com.gpwsofts.ffcalculator.mobile.exception;

public class InputLibelleFormatException extends Exception {
    public InputLibelleFormatException(String inMessage){
        super(inMessage);
    }
    public InputLibelleFormatException(String inMessage, Throwable in){
        super(inMessage,in);
    }
}
