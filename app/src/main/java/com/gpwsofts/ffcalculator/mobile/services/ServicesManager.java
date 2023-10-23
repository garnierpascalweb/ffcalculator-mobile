package com.gpwsofts.ffcalculator.mobile.services;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
/**
 * Gestion des Services
 * @since 1.0.0
 */
public class ServicesManager {
    /**
     * Constructeur de ServiceManager
     * @param application
     */
    public ServicesManager(FFCalculatorApplication application) {
        if (application.isServicesManagerAlreadyExist()) {
            throw new ExceptionInInitializerError("ServicesManager deja instancié pour FFCalculatorApplication");
        }
    }
}
