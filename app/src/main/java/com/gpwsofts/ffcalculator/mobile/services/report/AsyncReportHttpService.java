package com.gpwsofts.ffcalculator.mobile.services.report;

import com.gpwsofts.ffcalculator.mobile.common.executor.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.BuildConfig;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequest;
import com.gpwsofts.ffcalculator.mobile.services.report.pojo.FFCReportRequestFactory;
import com.gpwsofts.ffcalculator.mobile.common.sharedprefs.FFCalculatorSharedPrefs;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.common.www.FFCalculatorWebApi;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class AsyncReportHttpService implements IReportHttpService {
    private static final String TAG_NAME = "AsyncReportService";
    private SendReportRunnable sendReportRunnable;
    private static final int JOB_TIMEOUT = 5000;

    @Override
    public void clean() {

    }

    @Override
    public void sendReport(FFCReportRequest request) {
        throw new UnsupportedOperationException("cette operation ne s'applique pas a cette instance ");
    }

    @Override
    public void sendReportAsync(String tagName, Exception e) {
        if (sendReportRunnable != null) {
            sendReportRunnable = null;
        }
        LogUtils.d(TAG_NAME, "instantaition dun SendReportRunnable");
        sendReportRunnable = new SendReportRunnable(tagName, e);
        LogUtils.d(TAG_NAME, "submit du runnable SendReportRunnable dans le pool de thread");
        final Future<?> myHandler = AppExecutors.getInstance().networkIO().submit(sendReportRunnable);
        LogUtils.d(TAG_NAME, "appel de cancel dans <" + JOB_TIMEOUT + "> ");
        AppExecutors.getInstance().networkIO().schedule(() -> {
            myHandler.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private static class SendReportRunnable implements Runnable {
        Exception exceptionToReport;
        String initialTagName;
        boolean cancelRequest;

        public SendReportRunnable(String inInitialTagName, Exception inExceptionToReport){
            this.initialTagName = inInitialTagName;
            this.exceptionToReport = inExceptionToReport;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            FFCReportRequest request;
            Call<Void> call;
            try {
                LogUtils.d(TAG_NAME, "envoi de la cause au backend");
                request = FFCReportRequestFactory.createFFCReportRequest(initialTagName, exceptionToReport);
                if (FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected()) {
                    call = FFCalculatorWebApi.getInstance().getApiService().sendReport(FFCalculatorSharedPrefs.id(), BuildConfig.FLAVOR, request);
                    final Response<Void> response = call.execute();
                } else {
                    LogUtils.d(TAG_NAME, "pas de reseau");
                }
            } catch (Exception e) {
                LogUtils.w(TAG_NAME, "erreur non critique sur l'envoi d'un rapport de crash ", e);
            } finally {
                LogUtils.d(TAG_NAME, "fin d'envoi de la cause au backend");
            }
        }
    }
}
