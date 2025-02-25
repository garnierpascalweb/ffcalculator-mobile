package com.gpwsofts.ffcalculator.mobile.services.pos;

import android.util.Log;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.pos.pojo.FFCPosResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;


public class SimplePosHttpServiceTest {
    private static final String TAG_NAME = "SimplePosHttpServiceTest";
    IPosHttpService service;
    @Before
    public void init(){
        service = new SimplePosHttpService();
    }

    @Test
    public void testCalcPos(){
        double pos = 42.3;
        String classType = "H";
        LogUtils.i(TAG_NAME,"test dun calcul de position");
        Call<FFCPosResponse> response = service.calcPos(pos, classType);
        LogUtils.i(TAG_NAME,"bien fait !");
    }
}
