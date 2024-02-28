package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private List<String> currentClasses;
    private ArrayAdapter arrayAdapterForPos;
    private List<Integer> currentPos;
    private ArrayAdapter arrayAdapterForPrts;
    private List<Integer> currentPrts;
    private SeasonViewModel resultViewModel;
    private SharedPrefsViewModel sharedPrefsViewModel;
    private AddResultViewModel addResultViewModel;
    private FragmentResultBinding binding;

    public ResultFragment() {
        Log.i(TAG_NAME, "appel du constructeur");
        currentClasses = new ArrayList<String>();
        currentPos = new ArrayList<Integer>();
        currentPrts = IntStream.rangeClosed(1, 200).boxed().collect(Collectors.toList());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG_NAME, "appel de onCreate");
        resultViewModel = new ViewModelProvider(this).get(SeasonViewModel.class);
        sharedPrefsViewModel = new ViewModelProvider(this).get(SharedPrefsViewModel.class);
        addResultViewModel = new ViewModelProvider(this).get(AddResultViewModel.class);
        Log.i(TAG_NAME, "fin appel de onCreate");
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
        Log.i(TAG_NAME, "reinstanciation de ArrayAdapter avec <" + currentClasses.size() + "> classes");
        //autoCompleteTextViewClasses.setAdapter(arrayAdapterForClasses);
        //RecyclerView classesRV = root.findViewById(R.id.idRVClasses);
        final ClassesListAdapter classesListAdapter = new ClassesListAdapter(new ClassesListAdapter.ClassesDiff());
        //classesRV.setAdapter(adapter);
        autoCompleteTextViewClasses.setAdapter(new RecyclerBaseAdapter(classesListAdapter));
        //autoCompleteTextViewClasses.setAdapter(classesListAdapter);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        //classesRV.setLayoutManager(linearLayoutManager);
        //classesRV.setHasFixedSize(true);

        arrayAdapterForPos = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, currentPos);
        autoCompleteTextViewPos.setAdapter(arrayAdapterForPos);
        arrayAdapterForPrts = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, currentPrts);
        autoCompleteTextViewPrts.setAdapter(arrayAdapterForPrts);

        sharedPrefsViewModel.getVue().observe(getViewLifecycleOwner(), vue -> {
            Log.i(TAG_NAME, "nouvelle vue selectionnee = <" + vue + ">");
            //addResultViewModel.updateClassesChoices(vue);
            addResultViewModel.updateGridChoices(vue);
        });
        // observation de la liste des classes
        // Update UI
        /*
        addResultViewModel.getClassesChoices().observe(getViewLifecycleOwner(), classesList -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des classes");
            //Log.d(TAG_NAME, "arrayAdapterForClasses : appel de clear() - contenait <" + arrayAdapterForClasses.getCount() + "> items");
            //arrayAdapterForClasses.clear();
            //Log.d(TAG_NAME, "arrayAdapterForClasses : appel de addAll() - contendra <" + classesList.size() + "> items");
            //arrayAdapterForClasses.addAll(classesList);
            //Log.d(TAG_NAME, "arrayAdapterForClasses : contient desormais <" + arrayAdapterForClasses.getCount() + "> items");
            Log.d(TAG_NAME, "currentClasses : appel de clear() - contenait <" + currentClasses.size() + "> items");
            currentClasses.clear();
            Log.d(TAG_NAME, "currentClasses : appel de addAll() - contendra <" + classesList.size() + "> items");
            currentClasses.addAll(classesList);
            Log.d(TAG_NAME, "currentClasses : contient desormais <" + currentClasses.size() + "> items et arrayAdapter <" + arrayAdapterForClasses.getCount() + ">");
            Log.i(TAG_NAME, "fin refreshUI sur la liste des classes");
        });
        */

        addResultViewModel.getGridsChoices().observe(getViewLifecycleOwner(), gridsList -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des grilles");
            classesListAdapter.submitList(gridsList);
            Log.i(TAG_NAME, "fin refreshUI sur la liste des grilles");
        });

        // observation de la liste des positions
        // update UI
        addResultViewModel.getPosChoices().observe(getViewLifecycleOwner(), integers -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des positions");
            //arrayAdapterForPos.clear();
            //arrayAdapterForPos.addAll(integers);
            currentPos.clear();
            currentPos.addAll(integers);
            Log.i(TAG_NAME, "fin refreshUI sur la liste des positions");
        });


        addResultViewModel.getToastMessage().observe(getViewLifecycleOwner(), s -> {
            Log.i(TAG_NAME, "changement au niveau du message Toast : affichage");
            showToast(s);
        });


        // ecouteur sur la selection d'une classe dans la liste déroulante
        /*
        autoCompleteTextViewClasses.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG_NAME, "nouvel item selectionne ");
            String itemValue = parent.getItemAtPosition(position).toString();
            addResultViewModel.updatePosChoices(itemValue);
        });
        */
        /*
        classesRV.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG_NAME, "nouvel item selectionne ");
            String itemValue = parent.getItemAtPosition(position).toString();
            addResultViewModel.updatePosChoices(itemValue);
        });
        */


        buttonAjouter.setOnClickListener(vue -> saveResult());


        return root;
    }

    private void showToast(String message) {
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
        //TODO 1.0.0 a restaurer
        final String libelle = "toto";
        //final String libelle = String.valueOf(autoCompleteTextViewClasses.getText().toString());
        final int pos = Integer.valueOf(autoCompleteTextViewPos.getText().toString());
        final int prts = Integer.valueOf(autoCompleteTextViewPrts.getText().toString());
        // TODO 1.0.0 ecrire un service qui extrait le idclasse depuis le libelle plutot que la merde substring ci dessus
        // et si ava.lang.StringIndexOutOfBoundsException
        final String idClasse = libelle.substring(libelle.indexOf("(") + 1, libelle.indexOf(")"));
        Log.i(TAG_NAME, "ajout de la course une fois les points calcules pour " + place);
        // TODO 1.0.0 si ya pas de reseau je vais essayer de pas faire d'appel reseau
        boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
        if (isWwwConnected) {
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