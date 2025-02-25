package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.services.client.TownApiClient;

import java.util.List;

public class TownRepository {
    private static final String TAG_NAME = "TownRepository";
    private static TownRepository instance;
    private final TownApiClient townApiClient;
    private TownRepository() {
        townApiClient = TownApiClient.getInstance();
    }

    public static TownRepository getInstance() {
        if (null == instance)
            instance = new TownRepository();
        return instance;
    }

    public LiveData<List<String>> getTownChoicesLiveData() {
        return townApiClient.getTownChoicesLiveData();
    }

    public void loadTownChoicesAsync() {
        townApiClient.loadTownChoicesAsync();
    }
}
