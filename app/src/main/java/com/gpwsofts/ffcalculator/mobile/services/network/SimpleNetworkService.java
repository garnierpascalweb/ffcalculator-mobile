package com.gpwsofts.ffcalculator.mobile.services.network;


import android.net.Network;
import android.net.NetworkCapabilities;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;

public class SimpleNetworkService implements INetworkService {
    private static final String TAG_NAME = "SimpleNetworkService";
    @Override
    public boolean isWwwConnected() {
        boolean networkAvailable = false;
        Network network = FFCalculatorApplication.instance.getConnectivityManager().getActiveNetwork();
        if (network != null) {
            NetworkCapabilities networkCapabilities = FFCalculatorApplication.instance.getConnectivityManager().getNetworkCapabilities(network);
            if (networkCapabilities != null){
                boolean wifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                boolean datas = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
                LogUtils.d(TAG_NAME," wifi = <" + wifi + ">, datas = <" + datas + ">");
                networkAvailable =  (wifi || datas);
            } else {
                LogUtils.d(TAG_NAME, "NetworkCapabilities null - pas de reseau");
            }
        }
        LogUtils.d(TAG_NAME," reseau disponible = <" + networkAvailable + ">");
        return networkAvailable;
    }

    @Override
    public void clean() {

    }
}
