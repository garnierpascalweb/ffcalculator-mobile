package com.gpwsofts.ffcalculator.mobile.services.report;

import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.common.sharedprefs.FFCalculatorSharedPrefs;
import com.gpwsofts.ffcalculator.mobile.common.www.FFCalculatorWebApi;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequest;

import retrofit2.Call;

public class SimpleReportHttpService implements IReportHttpService {
    private static final String TAG_NAME = "SimpleReportHttpService";
    public SimpleReportHttpService() {

    }

    @Override
    public void sendReport(FFCReportRequest request) {
        Call<Void> call;
        try {
            LogUtils.d(TAG_NAME, "envoi de la cause au backend");
            if (FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected()) {
                call = FFCalculatorWebApi.getInstance().getApiService().sendReport(FFCalculatorSharedPrefs.id(), BuildConfig.FLAVOR, request);
                // final Response<Void> response = call.execute();
                call.execute();
            } else {
                LogUtils.d(TAG_NAME, "pas de reseau");
            }
        } catch (Exception e) {
            LogUtils.w(TAG_NAME, "erreur non critique sur l'envoi d'un rapport de crash ", e);
        } finally {
            LogUtils.d(TAG_NAME, "fin d'envoi de la cause au backend");
        }
    }

    @Override
    public void sendReportAsync(String tagName, Exception e) {

    }

    @Override
    public void clean() {

    }
}
