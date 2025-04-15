package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.os.Bundle;
import android.text.Editable;
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
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

import java.util.ArrayList;

public class AddResultFragment extends Fragment {
    private static final String TAG_NAME = "AddResultFragment";
    private AddResultViewModel addResultViewModel;
    private VueViewModel vueViewModel;
    private FragmentResultBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onCreate");
        super.onCreate(savedInstanceState);
        addResultViewModel = new ViewModelProvider(requireActivity()).get(AddResultViewModel.class);
        vueViewModel = new ViewModelProvider(requireActivity()).get(VueViewModel.class);
        LogUtils.i(TAG_NAME, "fin appel de onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onCreateView");
        binding = FragmentResultBinding.inflate(inflater, container, false);
        // initialisation de la vue
        LogUtils.d(TAG_NAME, "onCreateView - chargement asynchrone vue");
        vueViewModel.loadVueAsync();
        // initialisation de la liste des villes
        LogUtils.d(TAG_NAME, "onCreateView - chargement asynchrone towns");
        addResultViewModel.loadTownChoicesAsync();
        // initialisation de la liste des épreuves en fonction de la vue
        // ca va se faire dans le observation du changement de vue addResultViewModel.loadGridChoicesAsync(vueViewModel.getVueLiveData().getValue());
        LogUtils.i(TAG_NAME, "fin appel de onCreateView");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        // final TextInputLayout textInputLayoutPlace = binding.idTILPlace;
        final TextInputLayout textInputLayoutClasse = binding.idTILClasses;
        final TextInputLayout textInputLayoutPos = binding.idTILPos;
        // final TextInputLayout textInputLayoutPrts = binding.idTILPrts;
        final AutoCompleteTextView autoCompleteTextViewPlace = binding.idTIETPlaceAutoComplete;
        final AutoCompleteTextView autoCompleteTextViewClasses = binding.idTVAutoClasses;
        final AutoCompleteTextView autoCompleteTextViewPos = binding.idTVAutoPos;
        final AutoCompleteTextView autoCompleteTextViewPrts = binding.idTVAutoPrts;
        final Button buttonAjouter = binding.idBTAjouter;


        // INITIALISATION DES ADAPTERS
        // Adapter towns
        LogUtils.d(TAG_NAME, "onViewCreated - construction liste des villes");
        ArrayAdapter<String> townsArrayAdapter  = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, addResultViewModel.getCurrentListTowns());
        autoCompleteTextViewPlace.setAdapter(townsArrayAdapter);
        autoCompleteTextViewPlace.setThreshold(3);
        // Adapter Classes
        LogUtils.d(TAG_NAME, "onViewCreated - construction liste des classes");
        ClassesListAdapter classesListAdapter = new ClassesListAdapter(new ClassesListAdapter.ClassesDiff());
        ClassesRecyclerBaseAdapter classesRecyclerBaseAdapter = new ClassesRecyclerBaseAdapter(classesListAdapter);
        autoCompleteTextViewClasses.setAdapter(classesRecyclerBaseAdapter);
        // Adapter pos
        LogUtils.d(TAG_NAME, "onViewCreated - construction liste des pos");
        IntegerListAdapter posListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        IntegerRecyclerBaseAdapter posRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(posListAdapter);
        autoCompleteTextViewPos.setAdapter(posRecyclerBaseAdapter);
        // Adapter prts
        LogUtils.d(TAG_NAME, "onViewCreated - construction liste des prts");
        IntegerListAdapter prtsListAdapter = new IntegerListAdapter(new IntegerListAdapter.IntDiff());
        IntegerRecyclerBaseAdapter prtsRecyclerBaseAdapter = new IntegerRecyclerBaseAdapter(prtsListAdapter);
        prtsListAdapter.submitList(addResultViewModel.getIntegerList200());
        autoCompleteTextViewPrts.setAdapter(prtsRecyclerBaseAdapter);
        // FIN INITIALISATION DES ADAPTERS

        // OBSERVATION DUN CHANGEMENT DE VUE
        vueViewModel.getVueLiveData().observe(getViewLifecycleOwner(), vue -> {
            try{
                LogUtils.i(TAG_NAME, "debut observer vue");
                if (vue != null) {
                    LogUtils.d(TAG_NAME, "observer vue - suppression saisie en cours");
                    autoCompleteTextViewClasses.setText(R.string.vide);
                    LogUtils.d(TAG_NAME, "observer vue - clear selection des positions");
                    autoCompleteTextViewPos.clearListSelection();
                    LogUtils.d(TAG_NAME, "observer vue - rechargement asynchrone liste des classe selon vue");
                    addResultViewModel.loadGridChoicesAsync(vue);
                    LogUtils.d(TAG_NAME, "observer vue - affichage toast bascule vue");
                    Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ok, vue.getName()), Toast.LENGTH_SHORT).show();
                } else {
                    LogUtils.w(TAG_NAME, "observer vue - vue rendue null");
                    Toast.makeText(getActivity(), getString(R.string.toast_update_vue_ko), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "observer vue - probleme sur observer vue, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer vue");
            }

        });
        // FIN OBSERVATION DUN CHANGEMENT DE VUE

        // OBSERVATION DUN CHANGEMENT DE LISTE DE VILLES
        addResultViewModel.getTownChoicesLiveData().observe(getViewLifecycleOwner(), towns -> {
            try {
                LogUtils.i(TAG_NAME, "debut observer towns");
                if (towns != null) {
                    if (addResultViewModel.getCurrentListTowns().isEmpty()) {
                        LogUtils.d(TAG_NAME, "observer towns - liste en cache vide - mise a jour de l'adapter");
                        townsArrayAdapter.clear();
                        townsArrayAdapter.addAll(towns);
                        townsArrayAdapter.notifyDataSetChanged();
                        LogUtils.d(TAG_NAME, "observer towns - mise a jour addResultViewModel par <" + towns.size() + "> elements");
                        addResultViewModel.setCurrentListTowns(towns);
                    } else {
                        LogUtils.d(TAG_NAME, "observer towns - utilisation de la liste en cache consistante");
                    }
                } else {
                    LogUtils.w(TAG_NAME, "towns rendue null");
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur observer towns, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer towns");
            }
        });
        // FIN OBSERVATION DUN CHANGEMENT DE LISTE DE VILLES

        // OBSERVATION DUN NOUVEAU RESULTAT
        addResultViewModel.getAddedResultLiveData().observe(getViewLifecycleOwner(), addResultRunnableResponse -> {
            try {
                LogUtils.i(TAG_NAME, "debut observer newResult");
                LogUtils.d(TAG_NAME, "observer newResult - reactivation du bouton ajouter resultat");
                buttonAjouter.setEnabled(true);
                buttonAjouter.setText(R.string.button_label_ajouter_resultat);
                final Result result = addResultRunnableResponse.getResult();
                final String message = addResultRunnableResponse.getMessage();
                if (result != null) {
                    LogUtils.d(TAG_NAME, "observer newResult - insertion en base de donnees");
                    addResultViewModel.onNewResultCreated(result);
                    LogUtils.d(TAG_NAME, "observer newResult - bascule sur le fragment season");
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_season);
                } else {
                    LogUtils.w(TAG_NAME, "newResult rendu null");
                }
                if (message != null) {
                    LogUtils.d(TAG_NAME, "observer newResult - affichage toast message");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur observer newResult, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer newResult");
            }
        });
        // FIN OBSERVATION DUN NOUVEAU RESULTAT

        // OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES CLASSES DE COURSES
        addResultViewModel.getGridChoicesLiveData().observe(getViewLifecycleOwner(), gridsList -> {
            try {
                LogUtils.i(TAG_NAME, "debut observer gridsList");
                final String helperText;
                if (gridsList != null) {
                    helperText = getString(R.string.combo_classes_helper_text_ok, vueViewModel.getVueLiveData().getValue().getName(), gridsList.size());
                } else {
                    gridsList = new ArrayList<>();
                    textInputLayoutClasse.setHelperText(getString(R.string.combo_classes_helper_text_ko));
                    helperText = getString(R.string.combo_classes_helper_text_ko);
                }
                LogUtils.d(TAG_NAME, "observer gridsList - mise a jour liste des grilles");
                classesListAdapter.submitList(gridsList);
                LogUtils.d(TAG_NAME, "observer gridsList - mise a jour du helperText en conséquence");
                textInputLayoutClasse.setHelperText(helperText);
                LogUtils.d(TAG_NAME, "observer gridsList - remise a vide du helperText des positions");
                textInputLayoutPos.setHelperText(getString(R.string.vide));
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur observer gridsList, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer gridsList");
            }
        });
        // FIN OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES CLASSES DE COURSES

        // OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES POSITIONS
        addResultViewModel.getPosChoicesLiveData().observe(getViewLifecycleOwner(), posList -> {
            try {
                LogUtils.i(TAG_NAME, "debut observer posList");
                final String helperText;
                if (posList != null) {
                    helperText = getString(R.string.combo_pos_helper_text_ok, posList.size(), autoCompleteTextViewClasses.getText());
                } else {
                    posList = addResultViewModel.getIntegerList50();
                    helperText = getString(R.string.combo_pos_helper_text_ko, addResultViewModel.getIntegerList50().size(), autoCompleteTextViewClasses.getText());
                }
                LogUtils.d(TAG_NAME, "observer posList - mise a jour de la liste des pos");
                posListAdapter.submitList(posList);
                LogUtils.d(TAG_NAME, "observer posList - mise a jour du helperText en conséquence");
                textInputLayoutPos.setHelperText(helperText);
                LogUtils.d(TAG_NAME, "observer posList - remise a vide du helperText des positions");
                autoCompleteTextViewPos.setText("");
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur observer posList, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer posList");
            }
        });
        // FIN OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES POSITIONS

        // ECOUTEUR - SELECTION DUNE NOUVELLE CLASSE DE COURSE
        autoCompleteTextViewClasses.setOnItemClickListener((parent, maview, position, id) -> {
            try {
                LogUtils.i(TAG_NAME, "debut selection itemGrid");
                String itemValue = parent.getItemAtPosition(position).toString();
                LogUtils.d(TAG_NAME, "selection itemGrid - selection = <" + itemValue + "> - envoi du job asynchrone de chargement des positions");
                addResultViewModel.loadPosChoicesAsync(itemValue);
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "selection itemGrid - probleme sur observer posList, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin selection itemGrid");
            }
        });
        // FIN ECOUTEUR - SELECTION DUNE NOUVELLE CLASSE DE COURSE

        // ECOUTEUR - BOUTON AJOUT RESULTAT
        buttonAjouter.setOnClickListener(event -> {
            try {
                LogUtils.i(TAG_NAME, "debut onClick addResultButton");
                LogUtils.d(TAG_NAME, "onClick addResultButton - desactivation bouton et changement label");
                buttonAjouter.setEnabled(false);
                buttonAjouter.setText(R.string.button_label_calcul_en_cours);
                LogUtils.d(TAG_NAME, "onClick addResultButton - recuperation et extraction donnees formulaire");
                final Editable placeEditable = autoCompleteTextViewPlace.getText();
                final Editable libelleEditable = autoCompleteTextViewClasses.getText();
                final Editable posEditable = autoCompleteTextViewPos.getText();
                final Editable prtsEditable = autoCompleteTextViewPrts.getText();
                // obtention des 4 variables telles que saisies
                final String place = placeEditable == null ? null : String.valueOf(placeEditable);
                final String libelle = libelleEditable == null ? null : String.valueOf(libelleEditable);
                final String pos = posEditable == null ? null : String.valueOf(posEditable);
                final String prts = prtsEditable == null ? null : String.valueOf(prtsEditable);
                LogUtils.d(TAG_NAME, "onClick addResultButton - appel job calcul points");
                addResultViewModel.addResultApiAsync(place, libelle, pos, prts);
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "onClick addResultButton - probleme technique, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin onClick sur bouton ajouter");
            }
        });
        // FIN ECOUTEUR - BOUTON AJOUT RESULTAT
        LogUtils.i(TAG_NAME, "fin appel de onViewCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}