package com.gpwsofts.ffcalculator.mobile.services.result;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.services.logo.Logo;
import com.gpwsofts.ffcalculator.mobile.services.network.FFCPointsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteResultService extends AbstractResultService {
    private static final String TAG_NAME = "RemoteResultService";

    public RemoteResultService() {
        super();
    }

    @Override
    public void createResult(String place, String libelle, int pos, int prts) {
        ResultResponse resultResponse = new ResultResponse();
        Log.i(TAG_NAME, "ajout de la course une fois les points calcules pour " + place);
        final String idClasse = libelle.substring(libelle.indexOf("(") + 1, libelle.indexOf(")"));
        Call<FFCPointsResponse> call = FFCalculatorApplication.instance.getServicesManager().getPtsService().calcPts(place, pos, prts, idClasse);
        call.enqueue(new Callback<FFCPointsResponse>() {
            @Override
            public void onResponse(Call<FFCPointsResponse> call, Response<FFCPointsResponse> response) {
                if (response.isSuccessful()) {
                    final double pts = response.body().pts;
                    final String message = response.body().message;
                    Log.i(TAG_NAME, "succes lors du calcul des points valant " + pts + ", message = " + message + " , construction de l'objet Result");
                    Result result = new Result();
                    result.setPlace(place);
                    //TODO 1.0.0 : creer un logo service pour rendr eun logo en fonction de la classe de course
                    // TODO 1.0.0 grave foireux
                    Logo logo = FFCalculatorApplication.instance.getServicesManager().getLogoService(FFCalculatorApplication.instance.getResources()).getLogo(idClasse);
                    result.setLogo(logo.getText());
                    result.setLogoColor(logo.getColor());
                    //TODO 1.0.0 faire un appel http pour calculer les points
                    result.setPts(pts);
                    result.setPrts(prts);
                    result.setPos(pos);
                    result.setLibelle(libelle);
                    result.setIdClasse(idClasse);
                    resultRepository.insert(result);
                    resultResponse.setResult(result);
                    resultResponse.setStatus(true);
                    resultResponse.setMessage("Nouveau résultat ajouté");
                    resultResponseLiveData.postValue(resultResponse);
                } else {
                    resultResponse.setResult(null);
                    resultResponse.setStatus(false);
                    resultResponse.setMessage("Echec lors de l'ajout d'un nouveau résultat");
                    resultResponseLiveData.postValue(resultResponse);
                }
            }

            @Override
            public void onFailure(Call<FFCPointsResponse> call, Throwable t) {
                resultResponse.setResult(null);
                resultResponse.setStatus(false);
                resultResponse.setMessage("Echec lors de l'ajout d'un nouveau résultat");
                resultResponseLiveData.postValue(resultResponse);
            }
        });
    }
}
