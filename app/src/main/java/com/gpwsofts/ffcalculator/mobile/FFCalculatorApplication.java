package com.gpwsofts.ffcalculator.mobile;

import android.app.Application;
import android.net.ConnectivityManager;

import com.gpwsofts.ffcalculator.mobile.dao.FFCalculatorDatabase;
import com.gpwsofts.ffcalculator.mobile.services.ServicesManager;

/**
 * Classe Application
 *
 * @since 1.0.0
 */
public class FFCalculatorApplication extends Application {

    /**
     * Singleton application
     *
     * @since 1.0.0
     */
    public static FFCalculatorApplication instance;
    public FFCalculatorDatabase database;


    /**
     * Le service Manager pour gérer les services
     *
     * @since 1.0.0
     */
    private ServicesManager servicesManager;
    private ConnectivityManager connectivityManager;

    /**
     * Flag pour l'existence du Service Manager
     *
     * @since 1.0.0
     */
    private boolean servicesManagerAlreadyExist = false;


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
    /**
     * On reagit au callback onLowMemory
     * @since 1.0.0
     */
    public void onLowMemory() {
        super.onLowMemory();
        if (null != servicesManager) {
            servicesManager.unbindAndDie();
            servicesManager = null;
            servicesManagerAlreadyExist = false;
        }
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

    public final ConnectivityManager getConnectivityManager() {
        if (null == connectivityManager) {
            connectivityManager = getSystemService(ConnectivityManager.class);
        }
        return connectivityManager;
    }
}
