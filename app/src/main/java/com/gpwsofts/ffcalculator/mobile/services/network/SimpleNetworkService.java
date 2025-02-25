package com.gpwsofts.ffcalculator.mobile.services.network;


import android.net.NetworkInfo;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;

public class SimpleNetworkService implements INetworkService {
    @Override
    public boolean isWwwConnected() {
        return isNetworkAvailable();
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = FFCalculatorApplication.instance.getConnectivityManager() != null ? FFCalculatorApplication.instance.getConnectivityManager().getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        //TODO 1.0.0 methode depreciee
    }

    @Override
    public void clean() {

    }
}
