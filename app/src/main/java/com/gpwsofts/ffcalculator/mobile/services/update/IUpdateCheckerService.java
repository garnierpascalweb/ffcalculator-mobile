package com.gpwsofts.ffcalculator.mobile.services.update;

import com.gpwsofts.ffcalculator.mobile.services.ICleanableService;
import com.gpwsofts.ffcalculator.mobile.services.update.pojo.FFCUpdateCkeckerResponse;

import retrofit2.Call;

/**
 * @since 1.0.0
 * Service permettant de checker l'existence d'une nouvelle version
 * @deprecated 1.0.0 pas encore implémenté
 */
public interface IUpdateCheckerService extends ICleanableService {
    Call<FFCUpdateCkeckerResponse> checkForUpdates();
}
