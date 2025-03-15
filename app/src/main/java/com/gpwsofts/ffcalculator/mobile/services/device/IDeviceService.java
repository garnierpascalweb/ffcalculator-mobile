package com.gpwsofts.ffcalculator.mobile.services.device;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

public interface IDeviceService extends ICleanableService {
    int getApiLevel();
    int getVersionCode();
    String getVersionName();
    String getManufacturer();
    String getModel();
}
