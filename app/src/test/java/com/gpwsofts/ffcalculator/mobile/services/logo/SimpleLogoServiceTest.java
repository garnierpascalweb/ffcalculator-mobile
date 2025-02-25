package com.gpwsofts.ffcalculator.mobile.services.logo;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.ServicesManager;

import org.junit.Test;

public class SimpleLogoServiceTest {
    ILogoService service;
    @Test
    public void init(){
       service = FFCalculatorApplication.instance.getServicesManager().getLogoService(FFCalculatorApplication.instance.getResources());
    }
}
