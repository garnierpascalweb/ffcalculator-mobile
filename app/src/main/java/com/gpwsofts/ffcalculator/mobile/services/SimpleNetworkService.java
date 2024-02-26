package com.gpwsofts.ffcalculator.mobile.services;

import com.gpwsofts.ffcalculator.mobile.services.network.INetworkService;

public class SimpleNetworkService implements INetworkService {
    @Override
    public boolean isWwwConnected() {
        return false;
    }
}
