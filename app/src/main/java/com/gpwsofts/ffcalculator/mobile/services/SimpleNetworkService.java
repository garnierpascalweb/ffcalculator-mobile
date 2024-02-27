package com.gpwsofts.ffcalculator.mobile.services;

import com.gpwsofts.ffcalculator.mobile.services.network.INetworkService;

public class SimpleNetworkService implements INetworkService {
    @Override
    public boolean isWwwConnected() {
        //TODO 1.0.0 dire la verité concernant ca
        return false;
    }
}
