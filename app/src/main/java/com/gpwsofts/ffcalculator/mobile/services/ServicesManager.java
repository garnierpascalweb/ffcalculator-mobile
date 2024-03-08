package com.gpwsofts.ffcalculator.mobile.services;

import android.content.res.Resources;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.grid.IGridService;
import com.gpwsofts.ffcalculator.mobile.services.grid.SimpleGridService;
import com.gpwsofts.ffcalculator.mobile.services.logo.ILogoService;
import com.gpwsofts.ffcalculator.mobile.services.logo.SimpleLogoService;
import com.gpwsofts.ffcalculator.mobile.services.network.INetworkService;
import com.gpwsofts.ffcalculator.mobile.services.network.IPosService;
import com.gpwsofts.ffcalculator.mobile.services.network.IPtsService;
import com.gpwsofts.ffcalculator.mobile.services.network.SimplePosService;
import com.gpwsofts.ffcalculator.mobile.services.network.SimplePtsService;
import com.gpwsofts.ffcalculator.mobile.services.vues.IVueService;
import com.gpwsofts.ffcalculator.mobile.services.vues.SimpleVueService;

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
    private IVueService vueService = null;

    /**
     * Service des logos
     */
    private ILogoService logoService = null;

    /**
     * Service http des points
     */
    private IPtsService ptsService = null;

    /**
     * Service http de classement
     */
    private IPosService posService = null;
    /**
     * Service concernant l'état du reseau
     */
    private INetworkService networkService = null;
    /**
     *
     */
    private IGridService gridService = null;


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

    public final IVueService getVueService() {
        if (null == vueService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de IVueService");
            vueService = new SimpleVueService();
        } else {
            Log.d(TAG_NAME, "recuperation dune instance existante de IVueService");
        }
        return vueService;
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

    public final IPtsService getPtsService() {
        if (null == ptsService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de IPtsService");
            ptsService = new SimplePtsService();
        } else {
            Log.d(TAG_NAME, "recuperation dune instance existante de IPtsService");
        }
        return ptsService;
    }

    public final IPosService getPosService() {
        if (null == posService) {
            Log.i(TAG_NAME, "creation dune nouvelle instance de IPosService");
            posService = new SimplePosService();
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
