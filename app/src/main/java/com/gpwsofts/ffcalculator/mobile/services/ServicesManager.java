package com.gpwsofts.ffcalculator.mobile.services;
import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.result.IResultService;
import com.gpwsofts.ffcalculator.mobile.services.result.MockResultService;
import com.gpwsofts.ffcalculator.mobile.services.season.ISeasonService;
import com.gpwsofts.ffcalculator.mobile.services.season.MockSeasonService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Gestion des Services
 * @since 1.0.0
 */
public class ServicesManager {

    private static final String TAG_NAME = "ServicesManager";

    /**
     * Pool de Thread keepAlive
     * @since 1.0.0
     */
    private ExecutorService keepAliveThreadsExecutor = null;
    /**
     * Service Resultat
     * @since 1.0.0
     */
    private IResultService resultService = null;

    /**
     * Service Season
     */
    private ISeasonService seasonService = null;

    /**
     * Constructeur de ServiceManager
     * @param application
     */
    public ServicesManager(FFCalculatorApplication application) {
        if (application.isServicesManagerAlreadyExist()) {
            throw new ExceptionInInitializerError("ServicesManager deja instancié pour FFCalculatorApplication");
        }
    }

    /**
     *
     * @return l'instance du ResultService
     */
    public final IResultService getResultService() {
        if (null == resultService) {
            resultService = new MockResultService();
            Log.i(TAG_NAME,"creation dune nouvelle instance de MockResultService");
            //TODO 1.0.0 instancier un parametre avec des saveurs
        } else {
            Log.d(TAG_NAME,"recuperation dune instance existante de IResultService");
        }
        return resultService;
    }

    /**
     *
     * @return l'instance du SeasonService
     */
    public final ISeasonService getSeasonService(){
        if (null == seasonService){
            seasonService = new MockSeasonService();
            Log.i(TAG_NAME,"creation dune nouvelle instance de MockSeasonService");
            //TODO 1.0.0 instancier en parametre avec des saveurs
        } else {
            Log.d(TAG_NAME,"recuperation dune instance existante de ISeasonService");
        }
        return seasonService;
    }

    /**
     * @since 1.0.0
     * @return rend l'executorService du pool de Threads
     */
    public final ExecutorService getKeepAliveThreadsExecutor() {
        if (keepAliveThreadsExecutor == null) {
            keepAliveThreadsExecutor = Executors.newFixedThreadPool(12,Executors.defaultThreadFactory() );
        }
        return keepAliveThreadsExecutor;
    }

    /**
     * Flingue le service Manager
     * C'est a dire tous les services, et kille les process en cours dans ExecutorService
     * @since 1.0.0
     */
    public void unbindAndDie() {
        resultService = null;
        seasonService = null;
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
                keepAliveThreadsExecutor=null;
                //Log.e("MyApp", "Probably a memory leak here too");
            }
        }
        keepAliveThreadsExecutor=null;
    }


}
