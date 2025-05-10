package com.gpwsofts.ffcalculator.mobile.services.pts.api;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.SingleLiveEvent;
import com.gpwsofts.ffcalculator.mobile.common.api.AbstractApiClient;
import com.gpwsofts.ffcalculator.mobile.common.exception.AddResultException;
import com.gpwsofts.ffcalculator.mobile.common.exception.InputLibelleFormatException;
import com.gpwsofts.ffcalculator.mobile.common.exception.UnreadableBodyException;
import com.gpwsofts.ffcalculator.mobile.common.executor.AppExecutors;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.model.grid.IGrid;
import com.gpwsofts.ffcalculator.mobile.model.logo.ILogo;
import com.gpwsofts.ffcalculator.mobile.services.pts.api.response.AddResultRunnableResponse;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsRequest;
import com.gpwsofts.ffcalculator.mobile.services.pts.pojo.FFCPointsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @since 1.0.0
 * Api Cliente pour l'ajout asynchrone d'un nouveau resultat en background
 * Appelé par AddResultRepository
 * Rend un LiveData<Result>
 */
public class AddResultApiClient extends AbstractApiClient {
    private static final String TAG_NAME = "AddResultApiClient";
    private static final String LABEL_LIEU = "Lieu";
    private static final String LABEL_LIBELLE = "Libellé";
    private static final String LABEL_POSITION = "Position";
    private static final String LABEL_NB_PARTICIPANTS = "Nombre de participants";
    private static final int JOB_TIMEOUT = 5000;
    private static AddResultApiClient instance;
    private final SingleLiveEvent<AddResultRunnableResponse> mResult;
    private AddResultRunnable addResultRunnable;

    private AddResultApiClient() {
        mResult = new SingleLiveEvent<>();
    }

    public static AddResultApiClient getInstance() {
        if (null == instance)
            instance = new AddResultApiClient();
        return instance;
    }

    public LiveData<AddResultRunnableResponse> getAddedResultLiveData() {
        return mResult;
    }

    public void addResultApiAsync(String place, String libelle, String pos, String prts) {
        if (addResultRunnable != null) {
            addResultRunnable = null;
        }
        addResultRunnable = new AddResultRunnable(place, libelle, pos, prts);
        final Future<?> addResultRunnableFuture = AppExecutors.getInstance().networkIO().submit(addResultRunnable);
        AppExecutors.getInstance().networkIO().schedule(() -> {
            // annuler l'appel a l'API
            addResultRunnableFuture.cancel(true);
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
            AddResultRunnableResponse runnableResponse;
            Result newResult = null;
            String message = null;
            boolean isOk = false;
            FFCPointsRequest request;
            try {
                LogUtils.d(TAG_NAME, "debut job asynchrone AddResultRunnable - place = <" + place + "> libelle = <" + libelle + "> - <" + pos + "> - <" + prts + ">");
                // construction de la requete a partir des donnees en entree
                request = createRequestFromInput(place,libelle,pos,prts);
                boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
                if (isWwwConnected) {
                    final Response<FFCPointsResponse> response = FFCalculatorApplication.instance.getServicesManager().getPtsService().calcPts(request).execute();
                    if (cancelRequest) {
                        LogUtils.d(TAG_NAME, "cancelRequest true");
                        return;
                    }
                    final boolean isSuccessful = response.isSuccessful();
                    final int responseCode = response.code();
                    if (isSuccessful) {
                        final FFCPointsResponse body = response.body();
                        if (body != null) {
                            Double pts = body.pts;
                            newResult = createResultFromDatas(request.code, request.place, libelle, request.pos, request.prts, pts);
                            message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_ok);
                            isOk = true;
                        } else {
                            AddResultException ade = new AddResultException("probleme sur le calcul des points - reponse serveur non exploitable (http " + responseCode + ")");
                            ade.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem));
                            throw ade;
                        }
                    } else {
                        final ResponseBody body = response.errorBody();
                        if (body != null){
                            final String errorBodyContent = body.string();
                            AddResultException ade = new AddResultException(errorBodyContent);
                            ade.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_ko));
                            throw ade;
                        } else {
                            throw new UnreadableBodyException("impossible de lire errorBody");
                        }
                    }
                } else {
                    // pas de reseau
                    LogUtils.e(TAG_NAME, "echec du calcul du resultat - pas de reseau");
                    message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_no_network);
                    //TODO 1.0.0 Mettre en place une implementation locale de calcul
                }
            } catch (AddResultException e) {
                LogUtils.e(TAG_NAME, "AddResultException ", e);
                message = e.getToastMessage();
                sendErrorToBackEnd(TAG_NAME, e);
            } catch (IOException ioe) {
                LogUtils.e(TAG_NAME, "IOException ", ioe);
                message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem);
                sendErrorToBackEnd(TAG_NAME, ioe);
            } catch (UnreadableBodyException ube) {
                LogUtils.e(TAG_NAME, "UnreadableBodyException ", ube);
                message = FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem);
                sendErrorToBackEnd(TAG_NAME, ube);
            } finally {
                runnableResponse = new AddResultRunnableResponse();
                runnableResponse.setResult(newResult);
                runnableResponse.setMessage(message);
                runnableResponse.setOk(isOk);
                mResult.postValue(runnableResponse);
                LogUtils.i(TAG_NAME, "fin job asynchrone AddResultRunnable - place = <" + place + "> libelle = <" + libelle + "> - <" + pos + "> - <" + prts + "> - nouveau resultat calculé = <" + isOk + ">");
            }
        }

        /**
         * creation de la requete depuis l'input sur le formulaire
         * @param inplace lieu de lepreuve
         * @param inlibelle libelle de lepreuve
         * @param inpos position obtenue
         * @param inprts partants
         * @return la liste des champs qui ont été saisis vides
         * @since 1.0.0
         */
        protected FFCPointsRequest createRequestFromInput(String inplace, String inlibelle, String inpos, String inprts) throws AddResultException {
            List<String> emptyFields = new ArrayList<>();
            FFCPointsRequest request = new FFCPointsRequest();
            if (null == inplace || inplace.isEmpty())
                emptyFields.add(LABEL_LIEU);
            if (null == inlibelle || inlibelle.isEmpty())
                emptyFields.add(LABEL_LIBELLE);
            if (null == inpos || inpos.isEmpty())
                emptyFields.add(LABEL_POSITION);
            if (null == inprts || inprts.isEmpty())
                emptyFields.add(LABEL_NB_PARTICIPANTS);
            if (emptyFields.isEmpty()) {
                try {
                    int pos;
                    if (TextUtils.isDigitsOnly(inpos)) {
                        pos = Integer.parseInt(inpos);
                    } else {
                        AddResultException adex = new AddResultException("pas de type numérique : " + inpos);
                        adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_data, "position"));
                        throw adex;
                    }
                    int prts;
                    if (TextUtils.isDigitsOnly(inprts)) {
                        prts = Integer.parseInt(inprts);
                    } else {
                        AddResultException adex = new AddResultException("Une donnée nest pas de type numérique : " + inprts);
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
                    AddResultException adex = new AddResultException("Une donnée nest pas de type numérique : " + inpos + " ou " + inprts , ne);
                    adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_data, "position"));
                    throw adex;
                } catch (InputLibelleFormatException ie) {
                    AddResultException adex = new AddResultException("Format de libelle incorrect : " + inlibelle, ie);
                    adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem));
                    throw adex;
                } catch (Exception e){
                    AddResultException adex = new AddResultException("Probleme technique", e);
                    adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem));
                    throw adex;
                }
            } else {
                AddResultException adex = new AddResultException("champs vides " + emptyFields);
                adex.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_add_result_invalid_form, String.join(",", emptyFields)));
                throw adex;
            }
            return request;
        }

        /**
         * Factorisation du code de creation d'un resultat
         * @param inIdClasse classe de course
         * @param inPlace lieu
         * @param inLibelle libelle
         * @param inPos position obtenue
         * @param inPrts partants
         * @param inPts points recoltes
         * @return une instance de Result
         */
        protected Result createResultFromDatas(String inIdClasse, String inPlace, String inLibelle, int inPos, int inPrts, double inPts) throws AddResultException {
            Result newResult;
            try {
                newResult = new Result();
                LogUtils.v(TAG_NAME, "affectation des differents champs place, libelle, pos, prts");
                newResult.setIdClasse(inIdClasse);
                newResult.setPlace(inPlace);
                newResult.setLibelle(inLibelle);
                newResult.setPos(inPos);
                newResult.setPrts(inPrts);
                LogUtils.v(TAG_NAME, "affectation des pts calcules = <" + inPts + ">");
                newResult.setPts(inPts);
                final String idLogo = FFCalculatorApplication.instance.getServicesManager().getGridService().getGrids().stream().filter(grid -> grid.getCode().equals(inIdClasse)).map(IGrid::getLogo).findAny().orElse(null);
                LogUtils.v(TAG_NAME, "calcul de Logo pour un idLogo <" + idLogo + ">");
                // et si idLogo null, faudrait au moins mettre un logo par defaut
                // cette fonctionnalité est supportée par le service des logos, qui rend un logo par defaut "unknown" si idLogo est null ou si idLogo existe pas dans la map
                final ILogo logo = FFCalculatorApplication.instance.getServicesManager().getLogoService(FFCalculatorApplication.instance.getResources()).getLogo(idLogo);
                newResult.setLogo(logo.getText());
                newResult.setLogoColor(logo.getColor());
            } catch (IOException e) {
                AddResultException are = new AddResultException("probleme sur la creation d'un resultat", e);
                are.setToastMessage(FFCalculatorApplication.instance.getResources().getString(R.string.toast_technical_problem));
                throw are;
            } finally {
                LogUtils.v(TAG_NAME, "fin affectation des differents champs place, libelle, pos, prts");
            }
            return newResult;
        }
    }
}
