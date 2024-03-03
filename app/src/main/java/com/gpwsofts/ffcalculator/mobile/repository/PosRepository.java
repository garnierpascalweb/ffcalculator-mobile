package com.gpwsofts.ffcalculator.mobile.repository;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.services.network.PosApiClient;

public class PosRepository {
    private static PosRepository instance;
    private PosApiClient posApiClient;
    public static PosRepository getInstance(){
        if (null == instance)
            instance = new PosRepository();
        return instance;
    }
    private PosRepository(){
        posApiClient = PosApiClient.getInstance();
    }
    public LiveData<Integer> getPos(){
        return posApiClient.getPos();
    }
    public void searchPosApi(double pts, String classType){
        posApiClient.searchPosApi(pts,classType);
    }
}
