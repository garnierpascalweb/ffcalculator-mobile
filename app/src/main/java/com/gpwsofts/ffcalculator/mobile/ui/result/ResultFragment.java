package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.services.logo.Logo;
import com.gpwsofts.ffcalculator.mobile.services.network.FFCPointsResponse;
import com.gpwsofts.ffcalculator.mobile.services.vues.IVueService;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.shared.SharedPrefsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment {
    private static final String TAG_NAME = "ResultFragment";
    private SeasonViewModel resultViewModel;
    private SharedPrefsViewModel sharedPrefsViewModel;

    private AddResultViewModel addResultViewModel;
    private FragmentResultBinding binding;

    TextInputEditText textInputEditTextPlace;
    TextInputLayout textInputLayoutSpinnerClasses;
    AutoCompleteTextView autoCompleteTextViewPos;
    AutoCompleteTextView autoCompleteTextViewPrts;
    AutoCompleteTextView autoCompleteTextViewClasses;
    Button buttonAjouter;

    ArrayAdapter arrayAdapterForClasses;
    ArrayAdapter arrayAdapterForPos;
    ArrayAdapter arrayAdapterForPrts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        resultViewModel = new ViewModelProvider(requireActivity()).get(SeasonViewModel.class);
        sharedPrefsViewModel = new ViewModelProvider(requireActivity()).get(SharedPrefsViewModel.class);
        addResultViewModel = new ViewModelProvider(requireActivity()).get(AddResultViewModel.class);
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

        // mise a jour de la liste deroulante des classes quand ca change
        addResultViewModel.getClassesChoices().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> classesList) {
                ArrayAdapter<String> classesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, classesList);
                autoCompleteTextViewClasses.setAdapter(classesAdapter);
                classesAdapter.notifyDataSetChanged();
            }
        });

        // mise a jour de la liste des positions quand ca change
        addResultViewModel.getPosChoices().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
                    @Override
                    public void onChanged(List<Integer> integers) {
                        ArrayAdapter<Integer> posAdapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, integers);
                        autoCompleteTextViewPos.setAdapter(posAdapter);
                        posAdapter.notifyDataSetChanged();
                    }
                }
        );

        /*
        addResultViewModel.getSelectedClasse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG_NAME, "nouvel classe selectionnee");

            }
        });
        */


        // ecouteur sur la selection d'une classe dans la liste droulante
        autoCompleteTextViewClasses.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // qui met a jout la classe selectionnee dans le view model
                        Log.i(TAG_NAME, "nouvel item selectionne ");
                        Object o = parent.getItemAtPosition(position);
                        Log.i(TAG_NAME, "selection de " + o + " qui est de classe " + o.toString()) ;
                        addResultViewModel.getSelectedClasse().postValue(o.toString());
                        String str = o.toString().substring(o.toString().indexOf("(")+1,o.toString().indexOf(")"));
                        addResultViewModel.refreshPosChoices(str);
                        //TODO 1.0.0 atroce
                    }
                }
        );





        buttonAjouter = binding.idBTAjouter;

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResult();
            }
        });


        this.sharedPrefsViewModel.getVue().observe(getViewLifecycleOwner(), new Observer<String>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(String vue) {
                //TODO 1.0.0 que faire si la vue a changé
            }
        });
        return root;
    }





    /**
     * Demande de sauvegarde d'un resultat
     *
     * @since 1.0.0
     */
    private void saveResult() {
        // recuperation des datas
        final String place = String.valueOf(textInputEditTextPlace.getText());
        final String libelle = String.valueOf(autoCompleteTextViewClasses.getText().toString());
        final int pos = Integer.valueOf(autoCompleteTextViewPos.getText().toString());
        final int prts = Integer.valueOf(autoCompleteTextViewPrts.getText().toString());
        // TODO 1.0.0 ecrire un service qui extrait le idclasse depuis le libelle plutot que la merde substring ci dessus
        // et si ava.lang.StringIndexOutOfBoundsException
        final String idClasse = libelle.substring(libelle.indexOf("(") + 1, libelle.indexOf(")"));

        Log.i(TAG_NAME, "ajout de la course une fois les pointzs calcules pour " + place);
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
                } else {
                    Log.e(TAG_NAME, "na pas pu se faire , code http " + response.code() + " messgae " + response.message());
                }
            }

            @Override
            public void onFailure(Call<FFCPointsResponse> call, Throwable t) {
                Log.e(TAG_NAME, "echec lors de l'appel a l'api ", t);
                Log.d(TAG_NAME, "preparation du message de toast en echec");
            }
        });
        ;
        //TODO 1.0.0 on dit pas salope
        Toast.makeText(this.getContext(), "salope", Toast.LENGTH_SHORT).show();
        //TODO 1.0.0 gerer les erreurs
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}