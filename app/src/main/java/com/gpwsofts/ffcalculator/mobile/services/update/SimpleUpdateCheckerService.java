package com.gpwsofts.ffcalculator.mobile.services.update;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.gpwsofts.ffcalculator.mobile.services.update.pojo.FFCUpdateCkeckerResponse;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import retrofit2.Call;

/**
 * Implementation du service de check de version
 * @since 1.0.0
 * @deprecated 1.0.0 pas encore implémenté
 */
public class SimpleUpdateCheckerService implements IUpdateCheckerService {
    private static final String TAG_NAME = "SimpleUpdateCheckerService";
    @Override
    public Call<FFCUpdateCkeckerResponse> checkForUpdates() {
        LogUtils.i(TAG_NAME, "checkForUpdates");
        // return FFCalculatorWebApi.getInstance().getApiService().checkForUpdates();
        throw new UnsupportedOperationException("operation non supportee dans cette version");
    }

    private static void showUpdateDialog(Context context, String downloadUrl) {
        new android.os.Handler(context.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Mise à jour disponible");
            builder.setMessage("Une nouvelle version est disponible. Voulez-vous la télécharger ?");
            builder.setPositiveButton("Télécharger", (dialog, which) -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                context.startActivity(browserIntent);
            });
            builder.setNegativeButton("Plus tard", null);
            builder.show();
        });
    }

    @Override
    public void clean() {

    }
}
