package com.gpwsofts.ffcalculator.mobile.services;

import android.content.res.Resources;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;
import com.gpwsofts.ffcalculator.mobile.services.grid.SimpleGridService;
import com.gpwsofts.ffcalculator.mobile.services.logo.ILogoService;
import com.gpwsofts.ffcalculator.mobile.services.logo.SimpleLogoService;
import com.gpwsofts.ffcalculator.mobile.services.network.INetworkService;
import com.gpwsofts.ffcalculator.mobile.services.network.SimpleNetworkService;
import com.gpwsofts.ffcalculator.mobile.services.pos.IPosHttpService;
import com.gpwsofts.ffcalculator.mobile.services.pos.SimplePosHttpService;
import com.gpwsofts.ffcalculator.mobile.services.pts.IPtsHttpService;
import com.gpwsofts.ffcalculator.mobile.services.pts.SimplePtsHttpService;
import com.gpwsofts.ffcalculator.mobile.services.town.ITownService;
import com.gpwsofts.ffcalculator.mobile.services.town.SimpleTownService;
import com.gpwsofts.ffcalculator.mobile.services.vue.IVueService;
import com.gpwsofts.ffcalculator.mobile.services.vue.VueService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Gestion des Services
 *
 * @since 1.0.0
 */
public class ServicesManager {

    private static final String TAG_NAME = "ServicesManager";

    /**
     * Service des vues
     */


    /**
     * Service des logos
     */
    private ILogoService logoService = null;

    /**
     * Service http des points
     */
    private IPtsHttpService ptsService = null;

    /**
     * Service http de classement
     */
    private IPosHttpService posService = null;
    /**
     * Service concernant l'état du reseau
     */
    private INetworkService networkService = null;
    /**
     *
     */
    private IGridService gridService = null;
    private ITownService townService = null;
    /**
     * Service de vue
     * @since 1.0.0
     */
    private IVueService vueService;


    /**
     * Constructeur de ServiceManager
     *
     * @param application
     */
    public ServicesManager(FFCalculatorApplication application) {
        if (application.isServicesManagerAlreadyExist()) {
            throw new ExceptionInInitializerError("ServicesManager deja instancié pour FFCalculatorApplication");
        }
    }

    public final ILogoService getLogoService(Resources res) {
        if (null == logoService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de ILogoService");
            logoService = new SimpleLogoService(res);
        } else {
            Log.d(TAG_NAME, "recuperation dune instance existante de ILogoService");
        }
        return logoService;
    }

    public final IPtsHttpService getPtsService() {
        if (null == ptsService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de IPtsService");
            ptsService = new SimplePtsHttpService();
        } else {
            Log.d(TAG_NAME, "recuperation dune instance existante de IPtsService");
        }
        return ptsService;
    }

    public final IPosHttpService getPosService() {
        if (null == posService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de IPosService");
            posService = new SimplePosHttpService();
        } else {
            Log.d(TAG_NAME, "recuperation d'une instance existante de IPosService");
        }
        return posService;
    }

    public final INetworkService getNetworkService() {
        if (null == networkService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de INetworkService");
            networkService = new SimpleNetworkService();
        } else {
            Log.d(TAG_NAME, "recuperation d'une instance existante de INetworkService");
        }
        return networkService;
    }

    public final IGridService getGridService() {
        if (null == gridService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de IGridService");
            gridService = new SimpleGridService();
        } else {
            Log.d(TAG_NAME, "recuperation d'une instance existante de IGridService");
        }
        return gridService;
    }

    public final ITownService getTownService() {
        if (null == townService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de ITownService");
            townService = new SimpleTownService();
        } else {
            Log.d(TAG_NAME, "recuperation d'une instance existante de IGridService");
        }
        return townService;
    }

    /**
     * @since 1.0.0
     * @return un singleton de service de vue
     */
    public final IVueService getVueService(){
        if (null == vueService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de IVueService");
            vueService = new VueService();
        } else {
            Log.d(TAG_NAME, "recuperation d'une instance existante de IVueService");
        }
        return vueService;
    }
}
