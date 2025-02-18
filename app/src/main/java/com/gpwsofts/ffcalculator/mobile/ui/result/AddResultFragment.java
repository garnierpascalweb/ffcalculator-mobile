package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.os.Bundle;
import android.text.Editable;
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
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddResultFragment extends Fragment {
    private static final String TAG_NAME = "ResultFragment";
    private static final String VIDE = "";
    TextInputEditText textInputEditTextPlace;
    TextInputLayout textInputLayoutPlace;
    TextInputLayout textInputLayoutClasse;
    TextInputLayout textInputLayoutPos;
    TextInputLayout textInputLayoutPrts;
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
    private String hintPlace;
    private String hintType;
    private String hintPos;
    private String hintPrts;

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
        hintPlace = getResources().getString(R.string.hint_lieu_epreuve);
        hintType = getResources().getString(R.string.hint_type_epreuve);
        hintPos = getResources().getString(R.string.hint_place_obtenue);
        hintPrts = getResources().getString(R.string.hint_partants);
        Log.i(TAG_NAME, "fin appel de onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG_NAME, "appel de onCreateView");
        binding = FragmentResultBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        textInputLayoutPlace = binding.idTILPlace;
        textInputLayoutClasse = binding.idTILClasses;
        textInputLayoutPos = binding.idTILPos;
        textInputLayoutPrts = binding.idTILPrts;
        textInputEditTextPlace = binding.idTIETPlace;
        autoCompleteTextViewClasses = binding.idTVAutoClasses;
        autoCompleteTextViewPos = binding.idTVAutoPos;
        autoCompleteTextViewPrts = binding.idTVAutoPrts;
        buttonAjouter = binding.idBTAjouter;
        autoCompleteTextViewClasses.setAdapter(classesRecyclerBaseAdapter);
        autoCompleteTextViewPos.setAdapter(posRecyclerBaseAdapter);
        autoCompleteTextViewPrts.setAdapter(prtsRecyclerBaseAdapter);
        // initialisation de l'écran par la vue
        onUpdatedVue(vueViewModel.getVueLiveData().getValue());
        // observation de la vue courante
        vueViewModel.getVueLiveData().observe(getViewLifecycleOwner(), vue -> {
            Log.i(TAG_NAME, "debut observer getVueLiveData = <" + vue + ">");
            if (vue != null) {
                onUpdatedVue(vue);
                Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ok, vue.getName()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ko), Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG_NAME, "fin observer getVueLiveData = <" + vue + ">");
        });
        // observation d'un nouveau resultat
        addResultViewModel.getAddedResultLiveData().observe(getViewLifecycleOwner(), result -> {
            Log.i(TAG_NAME, "debut observer getAddedResult = <" + result + ">");
            Log.d(TAG_NAME, "  nouveau resultat a ajouter " + result);
            // result peut etre null si un probleme technique survient au calcul des points (mResult.postValue(null))
            if (result != null) {
                // insertion en database
                addResultViewModel.onNewResultCreated(result);
                // reinitialisation de l'écran
                reinit();
                // navigation au fragment de saison
                Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_season);
            } else {
                Log.e(TAG_NAME, "probleme sur l'ajout d'un nouveau resultat");
                Toast.makeText(getActivity(), getString(R.string.toast_add_result_ko), Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG_NAME, "fin observer getAddedResult = <" + result + ">");
        });
        // observation d'un message associé a un nouveau resultat
        addResultViewModel.getAddedResultMessageLiveData().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG_NAME, "probleme sur l'ajout d'un nouveau resultat");
                Toast.makeText(getActivity(), getString(R.string.toast_add_result_ko), Toast.LENGTH_SHORT).show();
            }
        });
        // observation de la liste des grilles
        // update UI
        addResultViewModel.getGridChoicesLiveData().observe(getViewLifecycleOwner(), gridsList -> {
            Log.i(TAG_NAME, "debut observer getGridsChoices");
            if (gridsList != null) {
                classesListAdapter.submitList(gridsList);
                textInputLayoutClasse.setHelperText(getString(R.string.combo_classes_helper_text, vueViewModel.getVueLiveData().getValue().getName(), gridsList.size()));
            } else {
                //TODO 1.0.0  gerer cas erreur
            }
            Log.i(TAG_NAME, "fin observer getGridsChoices");
        });

        // observation de la liste des positions
        // update UI
        addResultViewModel.getPosChoicesLiveData().observe(getViewLifecycleOwner(), posList -> {
            Log.i(TAG_NAME, "debut observer getPosChoices");
            if (posList != null) {
                posListAdapter.submitList(posList);
                autoCompleteTextViewPos.setText(VIDE);
                textInputLayoutPos.setHelperText(getString(R.string.combo_pos_helper_text, posList.size(), autoCompleteTextViewClasses.getText()));
            } else {
                //TODO 1.0.0  gerer cas erreur
            }
            Log.i(TAG_NAME, "fin observer getPosChoices");
        });


        // ecouteur de click sur la liste deroulante des classes (nouvel item selectionne)
        // update des choix de positions
        autoCompleteTextViewClasses.setOnItemClickListener((parent, view, position, id) -> {
            Log.i(TAG_NAME, "selection d'une nouvelle classe de course");
            String itemValue = parent.getItemAtPosition(position).toString();
            addResultViewModel.loadPosChoicesAsync(itemValue);
        });

        // ecouteur de click sur le bouton ajouter
        // declenche une action de sauvegarde du resultat
        buttonAjouter.setOnClickListener(vue -> saveResult());
        Log.i(TAG_NAME, "fin appel de onCreateView");
        return root;
    }

    private void onUpdatedVue(Vue vue) {
        addResultViewModel.loadGridChoicesAsync(vue);
        reinit();
    }

    /**
     * Demande de sauvegarde d'un resultat
     *
     * @since 1.0.0
     */
    private void saveResult() {
        // recuperation des datas
        final Editable placeEditable = textInputEditTextPlace.getText();
        final Editable libelleEditable = autoCompleteTextViewClasses.getText();
        final Editable posEditable = autoCompleteTextViewPos.getText();
        final Editable prtsEditable = autoCompleteTextViewPrts.getText();
        // obtention des 4 variables telles que saisies
        final String place = placeEditable == null ? null : String.valueOf(placeEditable);
        final String libelle = libelleEditable == null ? null : String.valueOf(libelleEditable);
        final String pos = posEditable == null ? null : String.valueOf(posEditable);
        final String prts = prtsEditable == null ? null : String.valueOf(prtsEditable);
        addResultViewModel.addResultApiAsync(place, libelle, pos, prts);
    }





    /**
     * Reinitialise l'écran (apres la validation d'un resultat)
     *
     * @since 1.0.0
     */
    private void reinit() {
        textInputEditTextPlace.setText(VIDE);
        autoCompleteTextViewClasses.setText(VIDE);
        autoCompleteTextViewPos.setText(VIDE);
        autoCompleteTextViewPrts.setText(VIDE);
        hintPlace = getResources().getString(R.string.hint_lieu_epreuve);
        hintType = getResources().getString(R.string.hint_type_epreuve);
        hintPos = getResources().getString(R.string.hint_place_obtenue);
        hintPrts = getResources().getString(R.string.hint_partants);
        textInputLayoutPos.setHelperText(hintPos);
        // Remettre les helper text a leur niveau
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}