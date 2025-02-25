package com.gpwsofts.ffcalculator.mobile.services;

import android.content.res.Resources;

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
import com.gpwsofts.ffcalculator.mobile.services.update.IUpdateCheckerService;
import com.gpwsofts.ffcalculator.mobile.services.update.SimpleUpdateCheckerService;
import com.gpwsofts.ffcalculator.mobile.services.vue.IVueService;
import com.gpwsofts.ffcalculator.mobile.services.vue.VueService;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

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

    private IUpdateCheckerService updateCheckerService = null;


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
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de ILogoService");
            logoService = new SimpleLogoService(res);
        } else {
            LogUtils.d(TAG_NAME, "recuperation dune instance existante de ILogoService");
        }
        return logoService;
    }

    public final IPtsHttpService getPtsService() {
        if (null == ptsService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IPtsService");
            ptsService = new SimplePtsHttpService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation dune instance existante de IPtsService");
        }
        return ptsService;
    }

    public final IPosHttpService getPosService() {
        if (null == posService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IPosService");
            posService = new SimplePosHttpService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IPosService");
        }
        return posService;
    }

    public final INetworkService getNetworkService() {
        if (null == networkService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de INetworkService");
            networkService = new SimpleNetworkService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de INetworkService");
        }
        return networkService;
    }

    public final IGridService getGridService() {
        if (null == gridService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IGridService");
            gridService = new SimpleGridService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IGridService");
        }
        return gridService;
    }

    public final ITownService getTownService() {
        if (null == townService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de ITownService");
            townService = new SimpleTownService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IGridService");
        }
        return townService;
    }

    /**
     * @since 1.0.0
     * @return un singleton de service de vue
     */
    public final IVueService getVueService(){
        if (null == vueService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IVueService");
            vueService = new VueService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IVueService");
        }
        return vueService;
    }

    public final IUpdateCheckerService getUpdateCheckerService(){
        if (null == updateCheckerService) {
            LogUtils.i(TAG_NAME, "creation dune nouvelle instance de IUpdateCkeckerService");
            updateCheckerService = new SimpleUpdateCheckerService();
        } else {
            LogUtils.d(TAG_NAME, "recuperation d'une instance existante de IUpdateCkeckerService");
        }
        return updateCheckerService;
    }
}
