package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.services.logo.Logo;
import com.gpwsofts.ffcalculator.mobile.services.network.FFCPointsResponse;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.shared.SharedPrefsViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment {
    private static final String TAG_NAME = "ResultFragment";
    TextInputEditText textInputEditTextPlace;
    TextInputLayout textInputLayoutSpinnerClasses;
    AutoCompleteTextView autoCompleteTextViewPos;
    AutoCompleteTextView autoCompleteTextViewPrts;
    AutoCompleteTextView autoCompleteTextViewClasses;
    Button buttonAjouter;
    private ArrayAdapter arrayAdapterForClasses;
    private ArrayAdapter arrayAdapterForPos;
    private ArrayAdapter arrayAdapterForPrts;
    private SeasonViewModel resultViewModel;
    private SharedPrefsViewModel sharedPrefsViewModel;
    private AddResultViewModel addResultViewModel;
    private FragmentResultBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultViewModel = new ViewModelProvider(this).get(SeasonViewModel.class);
        sharedPrefsViewModel = new ViewModelProvider(this).get(SharedPrefsViewModel.class);
        addResultViewModel = new ViewModelProvider(this).get(AddResultViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG_NAME, "appel de onCreateView");
        binding = FragmentResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //TODO 1.0.0 recuperation de UUID a mettre autre part que la
        //String android_device_id = Settings.Secure.getString(this.requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        //Log.i(TAG_NAME, "device id " + android_device_id);
        //Log.i(TAG_NAME, "nombre de pts = " + this.resultViewModel.getAllPts());
        textInputEditTextPlace = binding.idTIETPlace;
        autoCompleteTextViewClasses = binding.idTVAutoClasses;
        autoCompleteTextViewPos = binding.idTVAutoPos;
        autoCompleteTextViewPrts = binding.idTVAutoPrts;
        buttonAjouter = binding.idBTAjouter;
        arrayAdapterForClasses = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
        autoCompleteTextViewClasses.setAdapter(arrayAdapterForClasses);
        arrayAdapterForPos = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item);
        autoCompleteTextViewPos.setAdapter(arrayAdapterForPos);
        arrayAdapterForPrts = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item);
        autoCompleteTextViewPrts.setAdapter(arrayAdapterForPrts);
        // observation de la liste des classes
        // Update UI
        addResultViewModel.getClassesChoices().observe(getViewLifecycleOwner(), classesList -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des classes");
            Log.d(TAG_NAME, "arrayAdapterForClasses : appel de clear() - contenait <" + arrayAdapterForClasses.getCount() + "> items");
            arrayAdapterForClasses.clear();
            Log.d(TAG_NAME, "arrayAdapterForClasses : appel de addAll() - contendra <" + classesList.size() + "> items");
            arrayAdapterForClasses.addAll(classesList);
            Log.d(TAG_NAME, "arrayAdapterForClasses : contient desormais <" + arrayAdapterForClasses.getCount() + "> items");
            Log.i(TAG_NAME, "fin refreshUI sur la liste des classes");
        });

        // observation de la liste des positions
        // update UI
        addResultViewModel.getPosChoices().observe(getViewLifecycleOwner(), integers -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des positions");
            arrayAdapterForPos.clear();
            arrayAdapterForPos.addAll(integers);
            Log.i(TAG_NAME, "fin refreshUI sur la liste des positions");
        });

        // observation de la liste des partants
        // uodate UI
        addResultViewModel.getPrtsChoices().observe(getViewLifecycleOwner(), integers -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des partants");
            arrayAdapterForPrts.clear();
            arrayAdapterForPrts.addAll(integers);
            Log.i(TAG_NAME, "fin refreshUI sur la liste des partants");
        });

        addResultViewModel.getToastMessage().observe(getViewLifecycleOwner(), s -> {
            Log.i(TAG_NAME, "changement au niveau du message Toast : affichage");
            showToast(s);
        });


        // ecouteur sur la selection d'une classe dans la liste déroulante
        autoCompleteTextViewClasses.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG_NAME, "nouvel item selectionne ");
            String itemValue = parent.getItemAtPosition(position).toString();
            addResultViewModel.updatePosChoices(itemValue);
        });

        buttonAjouter.setOnClickListener(vue -> saveResult());


        sharedPrefsViewModel.getVue().observe(getViewLifecycleOwner(), vue -> {
            Log.i(TAG_NAME, "nouvelle vue selectionnee = <" + vue + ">");
            addResultViewModel.updateClassesChoices(vue);
        });
        return root;
    }

    private void showToast(String message){
        Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Demande de sauvegarde d'un resultat
     *
     * @since 1.0.0
     */
    private void saveResult() {
        // recuperation des datas
        String toastMessage = null;
        final String place = String.valueOf(textInputEditTextPlace.getText());
        final String libelle = String.valueOf(autoCompleteTextViewClasses.getText().toString());
        final int pos = Integer.valueOf(autoCompleteTextViewPos.getText().toString());
        final int prts = Integer.valueOf(autoCompleteTextViewPrts.getText().toString());
        // TODO 1.0.0 ecrire un service qui extrait le idclasse depuis le libelle plutot que la merde substring ci dessus
        // et si ava.lang.StringIndexOutOfBoundsException
        final String idClasse = libelle.substring(libelle.indexOf("(") + 1, libelle.indexOf(")"));
        Log.i(TAG_NAME, "ajout de la course une fois les points calcules pour " + place);
        // TODO 1.0.0 si ya pas de reseau je vais essayer de pas faire d'appel reseau
        boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
        if (isWwwConnected){
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
                        Logo logo = FFCalculatorApplication.instance.getServicesManager().getLogoService(getResources()).getLogo(idClasse);
                        result.setLogo(logo.getText());
                        result.setLogoColor(logo.getColor());
                        //TODO 1.0.0 faire un appel http pour calculer les points
                        result.setPts(pts);
                        result.setPrts(prts);
                        result.setPos(pos);
                        result.setLibelle(libelle);
                        result.setIdClasse(idClasse);
                        Log.i(TAG_NAME, "insertion du resultat en database room");
                        resultViewModel.insert(result);
                        Log.d(TAG_NAME, "preparation du message de toast en succes");
                        addResultViewModel.updateToastMessage("Résultat ajouté avec succès");
                    } else {
                        addResultViewModel.updateToastMessage("Probleme technique");
                        Log.e(TAG_NAME, "na pas pu se faire , code http " + response.code() + " messgae " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<FFCPointsResponse> call, Throwable t) {
                    addResultViewModel.updateToastMessage("Probleme technique");
                    Log.e(TAG_NAME, "echec lors de l'appel a l'api ", t);
                }
            });
        } else {
            addResultViewModel.updateToastMessage("Pas de réseau");
            Log.e(TAG_NAME, "pas de reseau");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}