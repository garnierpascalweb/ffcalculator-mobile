package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.textfield.TextInputLayout;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.model.Grid;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class AddResultFragment extends Fragment {
    private static final String TAG_NAME = "ResultFragment";
    private SeasonViewModel resultViewModel;
    private AddResultViewModel addResultViewModel;
    private VueViewModel vueViewModel;
    private FragmentResultBinding binding;
    private AutoCompleteAdapter autoCompleteAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG_NAME, "appel de onCreate");
        resultViewModel = new ViewModelProvider(requireActivity()).get(SeasonViewModel.class);
        addResultViewModel = new ViewModelProvider(requireActivity()).get(AddResultViewModel.class);
        vueViewModel = new ViewModelProvider(requireActivity()).get(VueViewModel.class);
        LogUtils.i(TAG_NAME, "fin appel de onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onCreateView");
        binding = FragmentResultBinding.inflate(inflater, container, false);
        // initialisation de la liste des villes
        addResultViewModel.loadTownChoicesAsync();
        // initialisation de la liste des épreuves en fonction de la vue
        addResultViewModel.loadGridChoicesAsync(vueViewModel.getVueLiveData().getValue());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "debut appel de onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        /**
         * Recuperation des éléments graphiques
         */
        final TextInputLayout textInputLayoutPlace = binding.idTILPlace;
        final TextInputLayout textInputLayoutClasse = binding.idTILClasses;
        final TextInputLayout textInputLayoutPos = binding.idTILPos;
        final TextInputLayout textInputLayoutPrts = binding.idTILPrts;
        final AutoCompleteTextView autoCompleteTextViewPlace = binding.idTIETPlaceAutoComplete;
        final AutoCompleteTextView autoCompleteTextViewClasses = binding.idTVAutoClasses;
        final AutoCompleteTextView autoCompleteTextViewPos = binding.idTVAutoPos;
        final AutoCompleteTextView autoCompleteTextViewPrts = binding.idTVAutoPrts;
        final Button buttonAjouter = binding.idBTAjouter;
        /**
         * Fin de récupération des éléments graphiques
         */

        /**
         * Initialisation des Adapters
         */
        // Adapter towns
        LogUtils.d(TAG_NAME, "adapter towns - construction avec une liste de <" + addResultViewModel.getCurrentListTowns().size() + "> elements");
        ArrayAdapter<String> townsArrayAdapter  = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, addResultViewModel.getCurrentListTowns());
        autoCompleteTextViewPlace.setAdapter(townsArrayAdapter);
        autoCompleteTextViewPlace.setThreshold(3);
        //StringListAdapter townsListAdapter = new StringListAdapter(new StringListAdapter.StringDiff());
        //StringRecyclerBaseAdapter stringRecyclerBaseAdapter = new StringRecyclerBaseAdapter(townsListAdapter);
        //autoCompleteTextViewPlace.setAdapter(stringRecyclerBaseAdapter);
        //autoCompleteTextViewPlace.setThreshold(3);


        // Adapter Classes
        ClassesListAdapter classesListAdapter = new ClassesListAdapter(new ClassesListAdapter.ClassesDiff());
        ClassesRecyclerBaseAdapter classesRecyclerBaseAdapter = new ClassesRecyclerBaseAdapter(classesListAdapter);
        autoCompleteTextViewClasses.setAdapter(classesRecyclerBaseAdapter);
        // Adapter pos
        IntegerListAdapter posListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        IntegerRecyclerBaseAdapter posRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(posListAdapter);
        autoCompleteTextViewPos.setAdapter(posRecyclerBaseAdapter);
        // Adapter prts
        IntegerListAdapter prtsListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        IntegerRecyclerBaseAdapter prtsRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(prtsListAdapter);
        prtsListAdapter.submitList(addResultViewModel.getIntegerList200());
        autoCompleteTextViewPrts.setAdapter(prtsRecyclerBaseAdapter);
        /**
         * Fin initialisation des Adapters
         */

        //textInputLayoutClasse.setHelperText(addResultViewModel.getCurrentListGridHelperText());
        //classesListAdapter.submitList(addResultViewModel.getCurrentListGrid());

        /**
         * Observation d'un changement de vue
         */
        vueViewModel.getVueLiveData().observe(getViewLifecycleOwner(), vue -> {
            LogUtils.i(TAG_NAME, "debut observer vue");
            if (vue != null) {
                LogUtils.d(TAG_NAME, "observer vue - la vue rendue n'est pas nulle - déclenchement du chargement des grilles associées a la vue <" + vue +"> et notification Toast ok");
                addResultViewModel.loadGridChoicesAsync(vue);
                Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ok, vue.getName()), Toast.LENGTH_SHORT).show();
            } else {
                LogUtils.d(TAG_NAME, "observer vue - la vue rendue est nulle - notification Toast ko");
                Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ko), Toast.LENGTH_SHORT).show();
            }
            LogUtils.i(TAG_NAME, "fin observer vue");
        });

        /**
         * Observation d'un chargement de liste de villes
         */
        addResultViewModel.getTownChoicesLiveData().observe(getViewLifecycleOwner(), towns -> {
            LogUtils.i(TAG_NAME, "debut observer towns");
            if (towns != null){
                LogUtils.d(TAG_NAME, "observer towns - liste chargee non nulle - contient <" + towns.size() + "> elements");
                if (addResultViewModel.getCurrentListTowns().isEmpty()){
                    LogUtils.d(TAG_NAME, "observer towns - liste en cache vide - mise a jour de l'adapter");
                    // LogUtils.d(TAG_NAME, "observer towns - appel de notifyDataSetChanged pour rechargement");
                    townsArrayAdapter.clear();
                    townsArrayAdapter.addAll(towns);
                    townsArrayAdapter.notifyDataSetChanged();
                    LogUtils.d(TAG_NAME, "observer towns - mise a jour addResultViewModel <" + towns.size() + "> elements");
                    addResultViewModel.setCurrentListTowns(towns);
                } else {
                    LogUtils.d(TAG_NAME, "observer towns - la liste en cache est consistante de <" + addResultViewModel.getCurrentListTowns() + "> elements - pas de mise a jour de l'adapter");
                }
            }
            LogUtils.i(TAG_NAME, "fin debut observer towns");
        });

        /**
         * Observation d'un nouveau résultat
         */
        addResultViewModel.getAddedResultLiveData().observe(getViewLifecycleOwner(), addResultRunnableResponse -> {
            LogUtils.i(TAG_NAME, "debut observer newResult");
            LogUtils.d(TAG_NAME, "observer newResult - reactivation du bouton ajouter et reprise de son label initial");
            buttonAjouter.setEnabled(true);
            buttonAjouter.setText(R.string.button_label_ajouter_resultat);
            final Result result = addResultRunnableResponse.getResult();
            final String message = addResultRunnableResponse.getMessage();
            if (result != null) {
                LogUtils.d(TAG_NAME, "observer newResult - le nouveau resultat nest pas null");
                LogUtils.d(TAG_NAME, "observer newResult - déclenchement de l'insertion en base de données et bascule sur le fragment Season");
                addResultViewModel.onNewResultCreated(result);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_season);
            } else {
                LogUtils.e(TAG_NAME, "observer newResult - le nouveau resultat est null");
            }
            if (message != null) {
                LogUtils.d(TAG_NAME, "observer newResult - le message associé au resultat nest pas null - affichage en toast");
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
            LogUtils.i(TAG_NAME, "fin observer newResult");
        });

        /**
         * Observation d'un nouveau choix de grilles
         */
        addResultViewModel.getGridChoicesLiveData().observe(getViewLifecycleOwner(), gridsList -> {
            LogUtils.i(TAG_NAME, "debut observer gridsList");
            final String helperText;
            if (gridsList != null) {
                LogUtils.d(TAG_NAME, "observer gridsList - la liste rendue n'est pas nulle");
                helperText = getString(R.string.combo_classes_helper_text_ok, vueViewModel.getVueLiveData().getValue().getName(), gridsList.size());
            } else {
                LogUtils.w(TAG_NAME, "observer gridsList - la liste rendue est nulle (probleme lors du calcul)");
                gridsList = new ArrayList<Grid>();
                textInputLayoutClasse.setHelperText(getString(R.string.combo_classes_helper_text_ko));
                helperText = getString(R.string.combo_classes_helper_text_ko);
            }
            LogUtils.d(TAG_NAME, "observer gridsList - mise a jour de la liste des grilles par une nouvelle liste de taille <" + gridsList + ">");
            classesListAdapter.submitList(gridsList);
            LogUtils.d(TAG_NAME, "observer gridsList - appel de notifyDataSetChanged pour rechargement");
            classesListAdapter.notifyDataSetChanged();
            LogUtils.d(TAG_NAME, "observer gridsList - mise a jour du helperText en conséquence");
            textInputLayoutClasse.setHelperText(helperText);
            LogUtils.d(TAG_NAME, "observer gridsList - envoi du helperText en cache dans le viewmodel");
            addResultViewModel.setCurrentListGridHelperText(helperText);
            LogUtils.i(TAG_NAME, "fin observer gridsList");
        });

        /**
         * Observation d'un nouveau choix de positions
         */
        addResultViewModel.getPosChoicesLiveData().observe(getViewLifecycleOwner(), posList -> {
            LogUtils.i(TAG_NAME, "debut observer posList");
            final String helperText;
            if (posList != null) {
                LogUtils.d(TAG_NAME, "observer posList - la liste rendue n'est pas nulle");
                helperText = getString(R.string.combo_pos_helper_text_ok, posList.size(), autoCompleteTextViewClasses.getText());
            } else {
                LogUtils.w(TAG_NAME, "observer posList - la liste rendue est nulle (probleme lors du calcul)");
                // le live data posList est null (probleme dans le job sous jacent)
                posList = addResultViewModel.getIntegerList50();
                helperText = getString(R.string.combo_pos_helper_text_ko, addResultViewModel.getIntegerList50().size(), autoCompleteTextViewClasses.getText());
            }
            LogUtils.d(TAG_NAME, "observer posList - mise a jour de la liste des pos par une nouvelle liste de taille <" + posList.size() + ">");
            posListAdapter.submitList(posList);
            LogUtils.d(TAG_NAME, "observer posList - appel de notifyDataSetChanged pour rechargement");
            posListAdapter.notifyDataSetChanged();
            LogUtils.d(TAG_NAME, "observer posList - mise a jour du helperText en conséquence");
            autoCompleteTextViewPos.setText("");
            textInputLayoutPos.setHelperText(helperText);
            LogUtils.i(TAG_NAME, "fin observer posList");
        });

        /**
         * Ecouteur sur le clic d'une nouvelle classe de course
         *   - déclenche la mise a jour de la liste des positions
         */
        autoCompleteTextViewClasses.setOnItemClickListener((parent, maview, position, id) -> {
            LogUtils.i(TAG_NAME, "debut onItemClick sur grid");
            String itemValue = parent.getItemAtPosition(position).toString();
            LogUtils.d(TAG_NAME, "onItemClick sur grid - valeur selectionnee = <" + itemValue + "> - envoi du job asynchrone de chargement des positions");
            addResultViewModel.loadPosChoicesAsync(itemValue);
            LogUtils.i(TAG_NAME, "fin onItemClick sur grid");
        });

        /**
         * Ecouteur sur le clic du bouton Ajouter Résultat
         *   - déclenche l'action d'ajout de résultat
         *     - label "Calcul en cours"
         *     - récupération des infos du formulaire
         *     - envoi du Job asynchrone d'ajout de résultat
         */
        buttonAjouter.setOnClickListener(event -> {
            LogUtils.i(TAG_NAME, "debut onClick sur bouton ajouter");
            LogUtils.d(TAG_NAME, "onClick sur bouton ajouter - desactivation du bouton et changement du label pour en cours");
            buttonAjouter.setEnabled(false);
            buttonAjouter.setText(R.string.button_label_calcul_en_cours);
            LogUtils.d(TAG_NAME, "onClick sur bouton ajouter - recuperation et extraction des donnees du formulaire");
            final Editable placeEditable = autoCompleteTextViewPlace.getText();
            final Editable libelleEditable = autoCompleteTextViewClasses.getText();
            final Editable posEditable = autoCompleteTextViewPos.getText();
            final Editable prtsEditable = autoCompleteTextViewPrts.getText();
            // obtention des 4 variables telles que saisies
            final String place = placeEditable == null ? null : String.valueOf(placeEditable);
            final String libelle = libelleEditable == null ? null : String.valueOf(libelleEditable);
            final String pos = posEditable == null ? null : String.valueOf(posEditable);
            final String prts = prtsEditable == null ? null : String.valueOf(prtsEditable);
            LogUtils.d(TAG_NAME, "onClick sur bouton ajouter - envoi du job asynchrone de calcul des points");
            addResultViewModel.addResultApiAsync(place, libelle, pos, prts);
            LogUtils.i(TAG_NAME, "fin onClick sur bouton ajouter");
        });


        LogUtils.i(TAG_NAME, "fin appel de onViewCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}