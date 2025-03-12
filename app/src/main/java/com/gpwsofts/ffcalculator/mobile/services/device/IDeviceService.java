package com.gpwsofts.ffcalculator.mobile.services.device;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;

public interface IDeviceService extends ICleanableService {
    public int getApiLevel();
    public int getVersionCode();
    public String getVersionName();
}
