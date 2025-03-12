package com.gpwsofts.ffcalculator.mobile.services.device;

import android.os.Build;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

/**
 * Service permettant la récupération d'informations sur le device
 * @since 1.0.0
 */
public class SimpleDeviceService implements IDeviceService {
    private static final String TAG_NAME = "SimpleDeviceService";
    private int versionCode;
    private String versionName;
    private int apiLevel;

    public SimpleDeviceService(){
        try{
            versionCode = BuildConfig.VERSION_CODE;
            LogUtils.i(TAG_NAME, "versionCode = <" + versionCode + ">");
            versionName = BuildConfig.VERSION_NAME;
            LogUtils.i(TAG_NAME, "versionName = <" + versionName + ">");
            apiLevel = Build.VERSION.SDK_INT;
            LogUtils.i(TAG_NAME, "apiLevel = <" + apiLevel + ">");
        } catch (Exception e){

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
    public void clean() {

    }
}
