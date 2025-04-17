package com.gpwsofts.ffcalculator.mobile.services.device;

import android.os.Build;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;

/**
 * Service permettant la récupération d'informations sur le device
 * @since 1.0.0
 */
public class SimpleDeviceService implements IDeviceService {
    private static final String TAG_NAME = "SimpleDeviceService";
    private int versionCode;
    private String versionName;
    private String manufacturer;
    private String model;
    private int apiLevel;

    public SimpleDeviceService(){
        try{
            versionCode = BuildConfig.VERSION_CODE;
            LogUtils.i(TAG_NAME, "versionCode = <" + versionCode + ">");
            versionName = BuildConfig.VERSION_NAME;
            LogUtils.i(TAG_NAME, "versionName = <" + versionName + ">");
            apiLevel = Build.VERSION.SDK_INT;
            LogUtils.i(TAG_NAME, "apiLevel = <" + apiLevel + ">");
            manufacturer = Build.MANUFACTURER;
            LogUtils.i(TAG_NAME, "manufacturer = <" + manufacturer + ">");
            model = Build.MODEL;
            LogUtils.i(TAG_NAME, "model = <" + model + ">");
        } catch (Exception e){
            LogUtils.w(TAG_NAME, "probleme sur la recuperation des informations relatives au device");
        }
    }

    @Override
    public int getApiLevel() {
        return apiLevel;
    }
    @Override
    public int getVersionCode() {
        return versionCode;
    }
    @Override
    public String getVersionName() {
        return versionName;
    }
    @Override
    public String getManufacturer() {
        return manufacturer;
    }
    @Override
    public String getModel() {
        return model;
    }
    @Override
    public void clean() {

    }
}
