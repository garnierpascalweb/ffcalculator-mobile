package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.ui.VueViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddResultFragment extends Fragment {
    private static final String TAG_NAME = "ResultFragment";
    TextInputEditText textInputEditTextPlace;
    TextInputLayout textInputLayoutSpinnerClasses;
    AutoCompleteTextView autoCompleteTextViewPos;
    AutoCompleteTextView autoCompleteTextViewPrts;
    AutoCompleteTextView autoCompleteTextViewClasses;
    Button buttonAjouter;

    private ClassesListAdapter classesListAdapter;
    private ClassesRecyclerBaseAdapter classesRecyclerBaseAdapter;
    private IntegerRecyclerBaseAdapter posRecyclerBaseAdapter;
    private IntegerListAdapter posListAdapter;
    private IntegerRecyclerBaseAdapter prtsRecyclerBaseAdapter;
    private IntegerListAdapter prtsListAdapter;
    private SeasonViewModel resultViewModel;
    private AddResultViewModel addResultViewModel;
    private VueViewModel vueViewModel;
    private FragmentResultBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG_NAME, "appel de onCreate");
        resultViewModel = new ViewModelProvider(this).get(SeasonViewModel.class);
        addResultViewModel = new ViewModelProvider(this).get(AddResultViewModel.class);
        vueViewModel = new ViewModelProvider(this).get(VueViewModel.class);
        classesListAdapter = new ClassesListAdapter(new ClassesListAdapter.ClassesDiff());
        classesRecyclerBaseAdapter = new ClassesRecyclerBaseAdapter(classesListAdapter);
        posListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        posRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(posListAdapter);
        prtsListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        prtsRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(prtsListAdapter);
        prtsListAdapter.submitList(IntStream.rangeClosed(1, 200).boxed().collect(Collectors.toList()));

        final IntegerListAdapter posListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
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

        autoCompleteTextViewClasses.setAdapter(classesRecyclerBaseAdapter);
        autoCompleteTextViewPos.setAdapter(posRecyclerBaseAdapter);
        autoCompleteTextViewPrts.setAdapter(prtsRecyclerBaseAdapter);

        // observation de la vue courante
        vueViewModel.getVueLiveData().observe(getViewLifecycleOwner(), vue -> {
            addResultViewModel.updateGridChoices(vue);
        });

        // observation de la liste des grilles
        // update UI
        addResultViewModel.getGridsChoices().observe(getViewLifecycleOwner(), gridsList -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des grilles");
            classesListAdapter.submitList(gridsList);
            Log.i(TAG_NAME, "fin refreshUI sur la liste des grilles");
        });

        // observation de la liste des positions
        // update UI
        addResultViewModel.getPosChoices().observe(getViewLifecycleOwner(), posList -> {
            Log.i(TAG_NAME, "refreshUI sur la liste des positions");
            posListAdapter.submitList(posList);
            Log.i(TAG_NAME, "fin refreshUI sur la liste des positions");
        });

        // observation de la reponse de nouveau resultat
        // update UI
        addResultViewModel.getResultResponse().observe(getViewLifecycleOwner(), resultResponse -> {
            addResultViewModel.updateToastMessage(resultResponse.getMessage());
        });

        // observation du message
        // update UI
        addResultViewModel.getToastMessage().observe(getViewLifecycleOwner(), s -> {
            Log.i(TAG_NAME, "changement au niveau du message Toast : affichage");
           // showToast(s);
        });

        // ecouteur de click sur la liste deroulante des classes (nouvel item selectionne)
        // update des choix de positions
        autoCompleteTextViewClasses.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG_NAME, "selection d'une nouvelle classe de course");
            String itemValue = parent.getItemAtPosition(position).toString();
            addResultViewModel.updatePosChoices(itemValue);
        });

        // ecouteur de click sur le bouton ajouter
        // declenche une action de sauvegarde du resultat
        buttonAjouter.setOnClickListener(vue -> saveResult());
        return root;
    }

    /**
     * Demande de sauvegarde d'un resultat
     *
     * @since 1.0.0
     */
    private void saveResult() {
        // recuperation des datas
        String toastMessage = null;
        boolean isWwwConnected = FFCalculatorApplication.instance.getServicesManager().getNetworkService().isWwwConnected();
        if (isWwwConnected) {
            final String place = String.valueOf(textInputEditTextPlace.getText());
            final String libelle = String.valueOf(autoCompleteTextViewClasses.getText().toString());
            final int pos = Integer.valueOf(autoCompleteTextViewPos.getText().toString());
            final int prts = Integer.valueOf(autoCompleteTextViewPrts.getText().toString());
            addResultViewModel.createNewResult(place,libelle,pos,prts);
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