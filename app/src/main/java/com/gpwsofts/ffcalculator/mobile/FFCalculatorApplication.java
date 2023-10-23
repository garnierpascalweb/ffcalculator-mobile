package com.gpwsofts.ffcalculator.mobile;

import android.app.Application;

public class FFCalculatorApplication extends Application {

    public static FFCalculatorApplication instance;

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
}
