package com.gpwsofts.ffcalculator.mobile;

import android.app.Application;
import android.net.ConnectivityManager;

import com.gpwsofts.ffcalculator.mobile.common.sharedprefs.FFCalculatorSharedPrefs;
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

    /**
     * Le service Manager pour gérer les services
     *
     * @since 1.0.0
     */
    private ServicesManager servicesManager;
    private ConnectivityManager connectivityManager;
    private FFCalculatorSharedPrefs sharedPrefs;


    /**
     * Flag pour l'existence du Service Manager
     *
     * @since 1.0.0
     */
    private boolean servicesManagerAlreadyExist = false;
    private boolean sharedPreferencesAlreadyExist = false;


    // cette methode s'exécute dans l'UI Thread selon M.Seguy
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
        super.onLowMemory();
        if (null != servicesManager) {
           servicesManager.unbindAll();
           servicesManager = null;
           servicesManagerAlreadyExist = false;
        }
    }

    public boolean isServicesManagerAlreadyExist() {
        return servicesManagerAlreadyExist;
    }

    public boolean isSharedPreferencesAlreadyExist() {
        return sharedPreferencesAlreadyExist;
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

    public final FFCalculatorSharedPrefs getSharedPrefs(){
        if (null == sharedPrefs){
            sharedPrefs = new FFCalculatorSharedPrefs(this);
            sharedPreferencesAlreadyExist = true;
        }
        return sharedPrefs;
    }

}
