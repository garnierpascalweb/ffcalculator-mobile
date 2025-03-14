package com.gpwsofts.ffcalculator.mobile.services.client;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.CheckUpdateRunnableResponse;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.model.LatestVersion;
import com.gpwsofts.ffcalculator.mobile.services.update.pojo.FFCUpdateCkeckerResponse;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class CheckForUpdatesApiClient {
    private static final String TAG_NAME = "CheckForUpdatesApiClient";
    private static CheckForUpdatesApiClient instance;
    private final SingleLiveEvent<CheckUpdateRunnableResponse> mCheckUpdateRunnableResponse;
    boolean cancelRequest;

    private CheckForUpdatesApiClient() {
        LogUtils.i(TAG_NAME, "instanciation de CheckForUpdatesApiClient");
        mCheckUpdateRunnableResponse = new SingleLiveEvent<CheckUpdateRunnableResponse>();
        LogUtils.i(TAG_NAME, "fin instanciation de CheckForUpdatesApiClient");
    }

    public static CheckForUpdatesApiClient getInstance() {
        if (null == instance)
            instance = new CheckForUpdatesApiClient();
        return instance;
    }

    public LiveData<CheckUpdateRunnableResponse> getLatestVersionLiveData() {
        return mCheckUpdateRunnableResponse;
    }

    private class CheckForUpdateRunnable implements Runnable {
        public CheckForUpdateRunnable(){

        }

        @Override
        public void run() {
            boolean isOk = false;
            String message = null;
            LatestVersion latestVersion = null;
            try{
                LogUtils.i(TAG_NAME, "debut du job asynchrone CheckForUpdateRunnable");
                boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
                if (isWwwConnected) {
                    Response response = checkForUpdate().execute();
                    if (cancelRequest) {
                        LogUtils.d(TAG_NAME, "cancelRequest true");
                        return;
                    }
                    int responseCode = response.code();
                    LogUtils.d(TAG_NAME, "responseCode du service des update = <" + responseCode + ">");
                    if (responseCode == 200) {
                        FFCUpdateCkeckerResponse updateResponse = (FFCUpdateCkeckerResponse)response.body();
                        isOk = true;
                        message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_ko);
                        latestVersion = createLastestVersionFromDatas(updateResponse.latestVersionCode, updateResponse.latestVersionName, updateResponse.downloadUrl);
                    } else {
                        // code retour http pas 200
                        LogUtils.e(TAG_NAME, "echec de la recuperation");
                        String error = response.errorBody().string();
                        LogUtils.e(TAG_NAME, "erreur " + error);
                        message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_ko);
                    }
                } else {
                    // pas de reseau
                    LogUtils.e(TAG_NAME, "echec du checkUpdate - pas de reseau");
                    message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_no_network);
                }
            } catch (IOException e) {
                LogUtils.e(TAG_NAME, "IOException ", e);
                message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem);
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "Exception ", e);
                message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem);
            } finally {
                CheckUpdateRunnableResponse checkUpdateRunnableResponse = new CheckUpdateRunnableResponse();
                checkUpdateRunnableResponse.setOk(isOk);
                checkUpdateRunnableResponse.setMessage(message);
                checkUpdateRunnableResponse.setLatestVersion(latestVersion);
                mCheckUpdateRunnableResponse.postValue(checkUpdateRunnableResponse);
                LogUtils.i(TAG_NAME, "fin du job asynchrone CheckForUpdateRunnable");
            }
        }

        private Call<FFCUpdateCkeckerResponse> checkForUpdate() {
            return FFCalculatorApplication.instance.getServicesManager().getUpdateCheckerService().checkForUpdates();
        }

        private void cancelRequest() {
            LogUtils.v("TAGNAME", "annulation de la requete");
            cancelRequest = true;
        }

        protected LatestVersion createLastestVersionFromDatas(int versionCode, String versionName, String downloadUrl){
            LatestVersion latestVersion = new LatestVersion();
            latestVersion.setLatestVersionCode(versionCode);
            latestVersion.setLatestVersionName(versionName);
            latestVersion.setDownloadUrl(downloadUrl);
            return latestVersion;
        }
    }
}
