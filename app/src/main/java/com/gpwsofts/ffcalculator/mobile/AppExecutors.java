package com.gpwsofts.ffcalculator.mobile;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
//TODO 1.0.0 devrait etre rattaché a FFCalculatorApplication
/**
 * Pool d'exécution du programme
 * @since 1.0.0
 */
public class AppExecutors {
    private static AppExecutors instance;
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public static AppExecutors getInstance() {
        if (null == instance)
            instance = new AppExecutors();
        return instance;
    }

    public ScheduledExecutorService networkIO() {
        return mNetworkIO;
    }
}
