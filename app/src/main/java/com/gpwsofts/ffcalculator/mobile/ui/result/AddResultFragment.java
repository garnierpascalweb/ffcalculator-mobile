package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddResultFragment extends Fragment {
    private static final String TAG_NAME = "ResultFragment";
    private static final List<Integer> INTEGER_LIST_1_200 = IntStream.rangeClosed(1, 200).boxed().collect(Collectors.toList());
    private static final List<Integer> INTEGER_LIST_1_50 = IntStream.rangeClosed(1, 50).boxed().collect(Collectors.toList());
    private static final List<Grid> EMPTY_GRID_LIST = new ArrayList<Grid>();
    private static final String VIDE = "";


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
        Log.i(TAG_NAME, "fin appel de onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG_NAME, "appel de onCreateView");
        binding = FragmentResultBinding.inflate(inflater, container, false);
        if (addResultViewModel.getCurrentListTowns() == null || addResultViewModel.getCurrentListTowns().isEmpty()) {
            Log.v(TAG_NAME, "chargement de la liste des villes, pas encore fait");
            addResultViewModel.loadTownChoicesAsync();
        } else {
            Log.v(TAG_NAME, "pas de chargement de la liste des villes, deja fait");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextInputLayout textInputLayoutPlace = binding.idTILPlace;
        final TextInputLayout textInputLayoutClasse = binding.idTILClasses;
        final TextInputLayout textInputLayoutPos = binding.idTILPos;
        final TextInputLayout textInputLayoutPrts = binding.idTILPrts;
        final AutoCompleteTextView autoCompleteTextViewPlace = binding.idTIETPlaceAutoComplete;
        final AutoCompleteTextView autoCompleteTextViewClasses = binding.idTVAutoClasses;
        final AutoCompleteTextView autoCompleteTextViewPos = binding.idTVAutoPos;
        final AutoCompleteTextView autoCompleteTextViewPrts = binding.idTVAutoPrts;
        final Button buttonAjouter = binding.idBTAjouter;
        Log.i(TAG_NAME, "instantiotion adapter de la liste des villes a vide");
        ArrayAdapter<String> townsListAdapter  = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<String>());
        ClassesListAdapter classesListAdapter = new ClassesListAdapter(new ClassesListAdapter.ClassesDiff());
        ClassesRecyclerBaseAdapter classesRecyclerBaseAdapter = new ClassesRecyclerBaseAdapter(classesListAdapter);
        IntegerListAdapter posListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        IntegerRecyclerBaseAdapter posRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(posListAdapter);
        IntegerListAdapter prtsListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        IntegerRecyclerBaseAdapter prtsRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(prtsListAdapter);
        prtsListAdapter.submitList(INTEGER_LIST_1_200);
        //TODO 1.0.0 chargement des villes ici ?


        textInputLayoutClasse.setHelperText(addResultViewModel.getCurrentListGridHelperText());
        autoCompleteTextViewPlace.setAdapter(townsListAdapter);
        autoCompleteTextViewPlace.setThreshold(3);

        autoCompleteTextViewClasses.setAdapter(classesRecyclerBaseAdapter);
        autoCompleteTextViewPos.setAdapter(posRecyclerBaseAdapter);
        autoCompleteTextViewPrts.setAdapter(prtsRecyclerBaseAdapter);
        classesListAdapter.submitList(addResultViewModel.getCurrentListGrid());

        //addResultViewModel.getGridChoicesLiveData();
        // initialisation de l'écran par la vue
        //TODO 1.0.0 a mon avis ca se fait tout seul onUpdatedVue(vueViewModel.getVueLiveData().getValue());
        // observation de la vue courante
        vueViewModel.getVueLiveData().observe(getViewLifecycleOwner(), vue -> {
            Log.i(TAG_NAME, "debut observer getVueLiveData = <" + vue + ">");
            if (vue != null) {
                addResultViewModel.loadGridChoicesAsync(vue);
                Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ok, vue.getName()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ko), Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG_NAME, "fin observer getVueLiveData = <" + vue + ">");
        });

        addResultViewModel.getTownChoicesLiveData().observe(getViewLifecycleOwner(), towns -> {
            Log.i(TAG_NAME, "debut observer towns");
                townsListAdapter.clear();
                townsListAdapter.addAll(towns);
                townsListAdapter.notifyDataSetChanged();
            Log.i(TAG_NAME, "fin debut observer towns");
        });

        // observation d'un nouveau resultat
        addResultViewModel.getAddedResultLiveData().observe(getViewLifecycleOwner(), addResultRunnableResponse -> {
            Log.i(TAG_NAME, "debut observer addResultRunnableResponse");
            // reactivation du bouton ajouter quoi qu'il en soit
            buttonAjouter.setEnabled(true);
            buttonAjouter.setText(R.string.button_label_ajouter_resultat);
            final Result result = addResultRunnableResponse.getResult();
            final String message = addResultRunnableResponse.getMessage();
            Log.d(TAG_NAME, "  nouveau resultat a ajouter " + result);
            // result peut etre null si un probleme technique survient au calcul des points (mResult.postValue(null))
            if (result != null) {
                // insertion en database (mais si ca merde on n'en sait rien ! )
                addResultViewModel.onNewResultCreated(result);
                // reinitialisation de l'écran
                // reinit();
                // navigation au fragment de saison
                Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_season);
            } else {
                // le live data result est null (probleme dans le job sous jacent)
                Log.e(TAG_NAME, "probleme sur l'ajout d'un nouveau resultat");
            }
            if (message != null)
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            Log.i(TAG_NAME, "fin observer addResultRunnableResponse");
        });

        // observation de la liste des grilles
        // update UI
        addResultViewModel.getGridChoicesLiveData().observe(getViewLifecycleOwner(), gridsList -> {
            Log.i(TAG_NAME, "debut observer getGridsChoices");
            final String helperText;
            if (gridsList != null) {
                helperText = getString(R.string.combo_classes_helper_text_ok, vueViewModel.getVueLiveData().getValue().getName(), gridsList.size());
            } else {
                // le live data gridsList est null (probleme dans le job sous jacent)
                Log.w(TAG_NAME, "probleme sur le chargement des grilles");
                gridsList = EMPTY_GRID_LIST;
                textInputLayoutClasse.setHelperText(getString(R.string.combo_classes_helper_text_ko));
                helperText = getString(R.string.combo_classes_helper_text_ko);
            }
            classesListAdapter.submitList(gridsList);
            classesListAdapter.notifyDataSetChanged();
            textInputLayoutClasse.setHelperText(helperText);
            //addResultViewModel.setCurrentListGrid(gridsList);
            addResultViewModel.setCurrentListGridHelperText(helperText);
            Log.i(TAG_NAME, "fin observer getGridsChoices");
        });

        // observation de la liste des positions
        // update UI
        addResultViewModel.getPosChoicesLiveData().observe(getViewLifecycleOwner(), posList -> {
            Log.i(TAG_NAME, "debut observer getPosChoices");
            if (posList != null) {
                posListAdapter.submitList(posList);
                autoCompleteTextViewPos.setText(VIDE);
                textInputLayoutPos.setHelperText(getString(R.string.combo_pos_helper_text_ok, posList.size(), autoCompleteTextViewClasses.getText()));
            } else {
                // le live data posList est null (probleme dans le job sous jacent)
                Log.w(TAG_NAME, "probleme sur le chargement des positions possibles pour ce type de classe, valeur par defaut rendue");
                posListAdapter.submitList(INTEGER_LIST_1_50);
                autoCompleteTextViewPos.setText(VIDE);
                textInputLayoutPos.setHelperText(getString(R.string.combo_pos_helper_text_ko, INTEGER_LIST_1_50.size(), autoCompleteTextViewClasses.getText()));
            }
            Log.i(TAG_NAME, "fin observer getPosChoices");
        });


        // ecouteur de click sur la liste deroulante des classes (nouvel item selectionne)
        // update des choix de positions
        autoCompleteTextViewClasses.setOnItemClickListener((parent, maview, position, id) -> {
            Log.i(TAG_NAME, "selection d'une nouvelle classe de course");
            String itemValue = parent.getItemAtPosition(position).toString();
            addResultViewModel.loadPosChoicesAsync(itemValue);
        });

        // ecouteur de click sur le bouton ajouter
        // declenche une action de sauvegarde du resultat
        buttonAjouter.setOnClickListener(vue -> {
            buttonAjouter.setEnabled(false);
            buttonAjouter.setText(R.string.button_label_calcul_en_cours);
            final Editable placeEditable = autoCompleteTextViewPlace.getText();
            final Editable libelleEditable = autoCompleteTextViewClasses.getText();
            final Editable posEditable = autoCompleteTextViewPos.getText();
            final Editable prtsEditable = autoCompleteTextViewPrts.getText();
            // obtention des 4 variables telles que saisies
            final String place = placeEditable == null ? null : String.valueOf(placeEditable);
            final String libelle = libelleEditable == null ? null : String.valueOf(libelleEditable);
            final String pos = posEditable == null ? null : String.valueOf(posEditable);
            final String prts = prtsEditable == null ? null : String.valueOf(prtsEditable);
            addResultViewModel.addResultApiAsync(place, libelle, pos, prts);
        });
        Log.i(TAG_NAME, "fin appel de onCreateView");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}