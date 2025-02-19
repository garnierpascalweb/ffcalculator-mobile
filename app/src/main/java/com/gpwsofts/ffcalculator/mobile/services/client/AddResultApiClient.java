package com.gpwsofts.ffcalculator.mobile.services.client;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.AddResultException;
import com.gpwsofts.ffcalculator.mobile.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.InputLibelleFormatException;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.Logo;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private static final String LABEL_LIEU = "Lieu";
    private static final String LABEL_LIBELLE = "Libellé";
    private static final String LABEL_POSITION = "Position";
    private static final String LABEL_NB_PARTICIPANTS = "Nombre de participants";
    private static final int JOB_TIMEOUT = 5000;
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

    public LiveData<String> getAddedResultMessageLiveData() {
        return mMessage;
    }

    public void addResultApiAsync(String place, String libelle, String pos, String prts) {
        if (addResultRunnable != null) {
            addResultRunnable = null;
        }
        addResultRunnable = new AddResultRunnable(place, libelle, pos, prts);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(addResultRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            // annuler l'appel a l'API
            myHandler.cancel(true);
        }, JOB_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    /**
     * @since 1.0.0
     * Job en tache de fond pour le calcul d'un nouveau resultat
     * - verification de la saisie
     * - verification du reseau
     * - parsing du libelle pour deduire classeId
     * - appel a l'API HTTP pour le calcul des points
     * - construction du Logo
     */
    private class AddResultRunnable implements Runnable {
        private final String place;
        private final String libelle;
        private final String pos;
        private final String prts;
        boolean cancelRequest;

        public AddResultRunnable(String place, String libelle, String pos, String prts) {
            this.place = place;
            this.libelle = libelle;
            this.pos = pos;
            this.prts = prts;
        }


        @Override
        public void run() {
            Result newResult = null;
            String message = null;
            FFCPointsRequest request;
            try {
                Log.i(TAG_NAME, "debut du job asynchrone AddResultRunnable");
                // construction de la requete a partir des donnees en entree
                request = createRequestFromInput(place,libelle,pos,prts);
                boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
                if (isWwwConnected) {
                    // reseau disponible
                    Response response = null;
                    response = getPts(request).execute();
                    if (cancelRequest) {
                        Log.d(TAG_NAME, "cancelRequest true, retourne zboub");
                        return;
                    }
                    int responseCode = response.code();
                    Log.d(TAG_NAME, "responseCode du service des points = <" + responseCode + ">");
                    if (response.code() == 200) {
                        Double pts = ((FFCPointsResponse) response.body()).pts;
                        newResult = createResultFromDatas(request.code, request.place, libelle,request.pos, request.prts, pts);
                        message = "Nouveau résultat ajouté (+" + pts + " pts)";
                    } else {
                        // code retour http pas 200
                        Log.e(TAG_NAME, "echec du calcul des points pour ce nouveau resultat");
                        String error = response.errorBody().string();
                        Log.e(TAG_NAME, "erreur " + error);
                        newResult = null;
                        message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_ko);
                        //TODO 1.0.0 faudrait envoyer au backend la cause
                    }
                } else {
                    // pas de reseau
                    newResult = null;
                    message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_no_network);
                    //TODO 1.0.0 peut etre dérouler une implementation locale ?
                }
            } catch (AddResultException e) {
                newResult = null;
                message = e.getToastMessage();
            } catch (IOException e) {
                newResult = null;
                message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem);
            } catch (Exception e) {
                newResult = null;
                message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem);
            } finally {
                mResult.postValue(newResult);
                mMessage.postValue(message);
                Log.i(TAG_NAME, "fin du job asynchrone AddResultRunnable");
            }
        }

        private Call<FFCPointsResponse> getPts(FFCPointsRequest request) {
            return FFCalculatorApplication.instance.getServicesManager().getPtsService().calcPts(request);
        }

        private void cancelRequest() {
            Log.v("TAGNAME", "annulation de la requete");
            cancelRequest = true;
        }

        /**
         * creation de la requete depuis l'input sur le formulaire
         * @param inplace
         * @param inlibelle
         * @param inpos
         * @param inprts
         * @return la liste des champs qui ont été saisis vides
         * @since 1.0.0
         */
        protected FFCPointsRequest createRequestFromInput(String inplace, String inlibelle, String inpos, String inprts) throws AddResultException {
            List<String> emptyFields = new ArrayList<String>();
            FFCPointsRequest request = new FFCPointsRequest();
            if (null == inplace || inplace.length() == 0)
                emptyFields.add(LABEL_LIEU);
            if (null == inlibelle || inlibelle.length() == 0)
                emptyFields.add(LABEL_LIBELLE);
            if (null == inpos || inpos.length() == 0)
                emptyFields.add(LABEL_POSITION);
            if (null == inprts || inprts.length() == 0)
                emptyFields.add(LABEL_NB_PARTICIPANTS);
            if (emptyFields.isEmpty()) {
                try {
                    int pos;
                    if (TextUtils.isDigitsOnly(inpos)) {
                        pos = Integer.valueOf(inpos);
                    } else {
                        AddResultException adex = new AddResultException("pas de type numérique : " + inpos + "");
                        adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_data, "position"));
                        throw adex;
                    }
                    int prts;
                    if (TextUtils.isDigitsOnly(inprts)) {
                        prts = Integer.valueOf(inprts);
                    } else {
                        AddResultException adex = new AddResultException("Une donnée nest pas de type numérique : " + inprts + "");
                        adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_data, "Nombre de participants"));
                        throw adex;
                    }
                    if (pos > prts) {
                        final String toastMessage = FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_pos_sup_prts);
                        AddResultException adex = new AddResultException();
                        adex.setToastMessage(toastMessage);
                        throw adex;
                    }
                    request.place = inplace;
                    request.pos = pos;
                    request.prts = prts;
                    request.code = FFCalculatorApplication.instance.getServicesManager().getGridService().getIdClasseFromLibelle(inlibelle);
                } catch (AddResultException adex) {
                    throw adex;
                } catch (NumberFormatException ne) {
                    AddResultException adex = new AddResultException("Une donnée nest pas de type numérique : " + inpos + " ou " + inprts + "", ne);
                    adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_data, "position"));
                    throw adex;
                } catch (InputLibelleFormatException ie) {
                    AddResultException adex = new AddResultException("Format de libelle incorrect : " + inlibelle + "", ie);
                    adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem));
                    throw adex;
                } catch (Exception e){
                    AddResultException adex = new AddResultException("Probleme technique", e);
                    adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem));
                    throw adex;
                }
            } else {
                AddResultException adex = new AddResultException("champs vides " + emptyFields);
                adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_form, emptyFields.stream().collect(Collectors.joining(","))));
                throw adex;
            }
            return request;
        }

        /**
         * Factorisation du code de creation d'un resultat
         * @param inIdClasse
         * @param inPlace
         * @param inLibelle
         * @param inPos
         * @param inPrts
         * @param inPts
         * @return une instance de Result
         */
        protected Result createResultFromDatas(String inIdClasse, String inPlace, String inLibelle, int inPos, int inPrts, double inPts) throws IOException {
            Result newResult = new Result();
            Log.v(TAG_NAME, "affectation des differents champs place, libelle, pos, prts");
            newResult.setIdClasse(inIdClasse);
            newResult.setPlace(inPlace);
            newResult.setLibelle(inLibelle);
            newResult.setPos(inPos);
            newResult.setPrts(inPrts);
            Log.v(TAG_NAME, "affectation des pts calcules = <" + inPts + ">");
            newResult.setPts(inPts);
            final String idLogo = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids().stream().filter(grid -> grid.getCode().equals(inIdClasse)).map(grid -> grid.getLogo()).findAny().orElse(null);
            Log.v(TAG_NAME, "calcul de Logo pour un idLogo <" + idLogo + ">");
            // et si idLogo null, faudrait au moins mettre un logo par defaut
            // cette fonctionnalité est supportée par le service des logos, qui rend un logo par defaut "unknown" si idLogo est null ou si idLogo existe pas dans la map
            final Logo logo = FFCalculatorApplication.instance.getServicesManager().getLogoService(FFCalculatorApplication.instance.getResources()).getLogo(idLogo);
            newResult.setLogo(logo.getText());
            newResult.setLogoColor(logo.getColor());
            return newResult;
        }
    }
}
