package com.gpwsofts.ffcalculator.mobile.services.client;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.Logo;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @since 1.0.0
 * Api Cliente pour l'ajout asynchrone d'un nouveau resultat en background
 * Appelé par AddResultRepository
 * Rend un LiveData<Result>
 */
public class AddResultApiClient {
    private static final String TAG_NAME = "AddResultApiClient";
    private static AddResultApiClient instance;
    private final SingleLiveEvent<Result> mResult;
    private final SingleLiveEvent<String> mMessage;
    private AddResultRunnable addResultRunnable;

    private AddResultApiClient() {
        Log.i(TAG_NAME, "instanciation de AddResultApiClient");
        mResult = new SingleLiveEvent<Result>();
        mMessage = new SingleLiveEvent<String>();
        Log.i(TAG_NAME, "fin instanciation de AddResultApiClient");
    }

    public static AddResultApiClient getInstance() {
        if (null == instance)
            instance = new AddResultApiClient();
        return instance;
    }

    public LiveData<Result> getAddedResultLiveData() {
        return mResult;
    }

    public LiveData<String> getAddedResultMessageLiveData(){
        return mMessage;
    }

    public void addResultApiAsync(String place, String libelle, int pos, int prts) {
        if (addResultRunnable != null) {
            addResultRunnable = null;
        }
        addResultRunnable = new AddResultRunnable(place, libelle, pos, prts);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(addResultRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            // annuler l'appel a l'API
            myHandler.cancel(true);
        }, 5000, TimeUnit.MILLISECONDS);
    }

    /**
     * @since 1.0.0
     * Job en tache de fond pour le calcul d'un nouveau resultat
     * - parsing du libelle pour deduire classeId
     * - appel a l'API HTTP pour le calcul des points
     * - construction du Logo
     */
    private class AddResultRunnable implements Runnable {
        private final String place;
        private final String libelle;
        private final int pos;
        private final int prts;
        boolean cancelRequest;

        public AddResultRunnable(String place, String libelle, int pos, int prts) {
            this.place = place;
            this.libelle = libelle;
            this.pos = pos;
            this.prts = prts;
        }


        @Override
        public void run() {
            Log.i(TAG_NAME, "debut du job asynchrone AddResultRunnable");
            Response response = null;
            final Result newResult;
            try {
                final String idClasse = libelle.substring(libelle.indexOf("(") + 1, libelle.indexOf(")"));
                Log.v(TAG_NAME, "libelle <" + libelle + "> donne idClasse <" + idClasse + ">");
                Log.d(TAG_NAME, "appel synchrone au service des points et recuperation de la reponse");
                Log.d(TAG_NAME, " arguments : place = <" + place + ">, idClasse = <" + idClasse + ">, pos = <" + pos + ">, prts = <" + prts + ">");
                response = getPts(place, idClasse, pos, prts).execute();
                if (cancelRequest) {
                    Log.d(TAG_NAME, "cancelRequest true, retourne zboub");
                    return;
                }
                int responseCode = response.code();
                Log.d(TAG_NAME, "responseCode du service des points = <" + responseCode + ">");
                if (response.code() == 200) {
                    Double pts = ((FFCPointsResponse) response.body()).pts;
                    Log.d(TAG_NAME, "succes du calcul des points pour ce nouveau resultat = <" + pts + ">");
                    Log.d(TAG_NAME, "instantiataion dun nouveau resultat");
                    newResult = new Result();
                    Log.v(TAG_NAME, "affectation des differents champs place, libelle, pos, prts");
                    newResult.setIdClasse(idClasse);
                    newResult.setPlace(place);
                    newResult.setLibelle(libelle);
                    newResult.setPos(pos);
                    newResult.setPrts(prts);
                    Log.v(TAG_NAME, "affectation des pts calcules = <" + pts + ">");
                    newResult.setPts(pts);
                    Log.v(TAG_NAME, "calcul de idLogo pour une epreuve de classe <" + idClasse + ">");
                    final String idLogo = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids().stream().filter(grid -> grid.getCode().equals(idClasse)).map(grid -> grid.getLogo()).findAny().orElse(null);
                    // TODO 1.0.0 et si idLogo null
                    Log.v(TAG_NAME, "calcul de Logo pour un idLogo <" + idLogo + ">");
                    Logo logo = FFCalculatorApplication.instance.getServicesManager().getLogoService(FFCalculatorApplication.instance.getResources()).getLogo(idLogo);
                    newResult.setLogo(logo.getText());
                    newResult.setLogoColor(logo.getColor());
                    Log.d(TAG_NAME, "post du nouveau resultat en livedata");
                    mResult.postValue(newResult);
                    mMessage.postValue("Nouveau résultat ajouté (+" + pts + " pts)");
                } else {
                    Log.e(TAG_NAME, "echec du calcul des points pour ce nouveau resultat");
                    String error = response.errorBody().string();
                    Log.e(TAG_NAME, "erreur " + error);
                    mResult.postValue(null);
                    mMessage.postValue("Probleme lors de l'ajout d'un nouveau résultat");
                }
            } catch (IOException e) {
                Log.e(TAG_NAME, "echec du calcul des points pour ce nouveau resultat", e);
                mMessage.postValue("Probleme lors de l'ajout d'un nouveau résultat");
            } finally {
                Log.i(TAG_NAME, "fin du job asynchrone AddResultRunnable");
            }
        }

        private Call<FFCPointsResponse> getPts(String place, String classe, int pos, int prts) {
            return FFCalculatorApplication.instance.getServicesManager().getPtsService().calcPts(place, pos, prts, classe);
        }

        private void cancelRequest() {
            Log.v("TAGNAME", "annulation de la requete");
            cancelRequest = true;
        }
    }
}
