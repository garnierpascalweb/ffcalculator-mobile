package com.gpwsofts.ffcalculator.mobile.services.network;


import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

public class SimpleNetworkService implements INetworkService {
    private static final String TAG_NAME = "SimpleNetworkService";
    @Override
    public boolean isWwwConnected() {
        return isNetworkAvailable();
    }

    private boolean isNetworkAvailable() {
        boolean networkAvailable = false;
        Network network = FFCalculatorApplication.instance.getConnectivityManager().getActiveNetwork();
        if (network != null) {
            NetworkCapabilities networkCapabilities = FFCalculatorApplication.instance.getConnectivityManager().getNetworkCapabilities(network);
            boolean wifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            boolean datas = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
            LogUtils.d(TAG_NAME," wifi = <" + wifi + ">, datas = <" + datas + ">");
            networkAvailable =  networkCapabilities != null && (wifi || datas);
        }
        LogUtils.d(TAG_NAME," reseau disponible = <" + networkAvailable + ">");
        return networkAvailable;
    }

    @Override
    public void clean() {

    }
}
