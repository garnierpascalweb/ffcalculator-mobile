package com.gpwsofts.ffcalculator.mobile;

import android.app.Application;

import com.gpwsofts.ffcalculator.mobile.services.ServicesManager;

public class FFCalculatorApplication extends Application {

    public static FFCalculatorApplication instance;

    /**
     * Le service Manager pour gérer les services
     * @since 1.0.0
     */
    private ServicesManager servicesManager;

    /**
     * Flag pour l'existence du Service Manager
     * @since 1.0.0
     */
    private boolean servicesManagerAlreadyExist = false;

    public FFCalculatorApplication() {

    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        //TODO 1.0.0 repondre a ce tuc
        super.onLowMemory();
    }

    public boolean isServicesManagerAlreadyExist() {
        return servicesManagerAlreadyExist;
    }

    public final ServicesManager getServicesManager() {
        if (null == servicesManager) {
            servicesManager = new ServicesManager(this);
            servicesManagerAlreadyExist = true;
        }
        return servicesManager;
    }
}
