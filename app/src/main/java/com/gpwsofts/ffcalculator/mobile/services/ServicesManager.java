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
     * Pool de Thread keepAlive
     *
     * @since 1.0.0
     */
    private ExecutorService keepAliveThreadsExecutor = null;
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

    /**
     * @return rend l'executorService du pool de Threads
     * @since 1.0.0
     */
    public final ExecutorService getKeepAliveThreadsExecutor() {
        if (keepAliveThreadsExecutor == null) {
            keepAliveThreadsExecutor = Executors.newFixedThreadPool(12, Executors.defaultThreadFactory());
        }
        return keepAliveThreadsExecutor;
    }

    /**
     * Flingue le service Manager
     * C'est a dire tous les services, et kille les process en cours dans ExecutorService
     *
     * @since 1.0.0
     */
    public void unbindAndDie() {
        //TODO 1.0.0 mettre tous les services a nill
        if (keepAliveThreadsExecutor != null) {
            killKeepAliveThreadExecutor();
        }
    }

    /**
     * @since 1.0.0
     */
    private void killKeepAliveThreadExecutor() {
        if (keepAliveThreadsExecutor != null) {
            keepAliveThreadsExecutor.shutdown(); // Disable new tasks from being submitted
            try {// as long as your threads hasn't finished
                while (!keepAliveThreadsExecutor.isTerminated()) {
                    // Wait a while for existing tasks to terminate
                    if (!keepAliveThreadsExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                        // Cancel currently executing tasks
                        keepAliveThreadsExecutor.shutdown();
                        // Log.e("MyApp", "Probably a memory leak here");
                    }
                }
            } catch (InterruptedException ie) {
                // (Re-)Cancel if current thread also interrupted
                keepAliveThreadsExecutor.shutdownNow();
                keepAliveThreadsExecutor = null;
                //Log.e("MyApp", "Probably a memory leak here too");
            }
        }
        keepAliveThreadsExecutor = null;
    }


}
