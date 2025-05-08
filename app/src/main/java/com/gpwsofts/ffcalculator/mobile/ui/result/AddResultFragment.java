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
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentResultBinding;
import com.gpwsofts.ffcalculator.mobile.services.vue.IVueService;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

import java.util.ArrayList;

public class AddResultFragment extends Fragment {
    private static final String TAG_NAME = "AddResultFragment";
    private AddResultViewModel addResultViewModel;
    private VueViewModel vueViewModel;
    private FragmentResultBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.onCreateBegin(TAG_NAME);
        super.onCreate(savedInstanceState);
        addResultViewModel = new ViewModelProvider(requireActivity()).get(AddResultViewModel.class);
        vueViewModel = new ViewModelProvider(requireActivity()).get(VueViewModel.class);
        LogUtils.onCreateEnd(TAG_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.onCreateViewBegin(TAG_NAME);
        binding = FragmentResultBinding.inflate(inflater, container, false);
        // initialisation de la vue
        if (vueViewModel.getCurrentCodeVue() == null) {
            LogUtils.d(TAG_NAME, "onCreateView - chargement asynchrone vue");
            vueViewModel.loadCodeVueAsync();
        }
        // initialisation de la liste des villes
        LogUtils.d(TAG_NAME, "onCreateView - chargement asynchrone towns");
        addResultViewModel.loadTownChoicesAsync();
        // initialisation de la liste des épreuves en fonction de la vue
        // ca va se faire dans le observation du changement de vue addResultViewModel.loadGridChoicesAsync(vueViewModel.getVueLiveData().getValue());
        LogUtils.onCreateViewEnd(TAG_NAME);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.onViewCreatedBegin(TAG_NAME);
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

        // SERVICE DES VUES
        final IVueService vueService = FFCalculatorApplication.instance.getServicesManager().getVueService(getResources().getStringArray(R.array.vues_libelles_array),getResources().getStringArray(R.array.vues_ids_array));

        // INITIALISATION DES ADAPTERS
        // Adapter towns

        // selon l'IA, requireContext() est parfaitement sur dans onViewCreated
        ArrayAdapter<String> townsArrayAdapter  = new ArrayAdapter<>(requireContext(), R.layout.simple_spinner_item, addResultViewModel.getCurrentListTowns());
        autoCompleteTextViewPlace.setAdapter(townsArrayAdapter);
        autoCompleteTextViewPlace.setThreshold(3);
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
        // FIN INITIALISATION DES ADAPTERS

        // OBSERVATION DUN CHANGEMENT DE VUE
        vueViewModel.getCodeVueLiveData().observe(getViewLifecycleOwner(), codeVue -> {
            try{
                LogUtils.d(TAG_NAME, "debut observer codeVue = <" + codeVue + ">");
                if (codeVue != null) {
                    LogUtils.v(TAG_NAME, "  observer codeVue = <" + codeVue + "> - suppression saisie en cours");
                    autoCompleteTextViewClasses.setText(R.string.vide);
                    LogUtils.v(TAG_NAME, "  observer codeVue = <" + codeVue + "> - clear selection des positions");
                    autoCompleteTextViewPos.clearListSelection();
                    LogUtils.v(TAG_NAME, "  observer codeVue = <" + codeVue + "> - rechargement asynchrone liste des classe selon vue");
                    addResultViewModel.loadGridChoicesAsync(codeVue);
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "observer codeVue = <" + codeVue + "> - probleme sur observer codeVue, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.d(TAG_NAME, "fin observer codeVue = <" + codeVue + ">");
            }
        });
        // FIN OBSERVATION DUN CHANGEMENT DE VUE

        // OBSERVATION DUN CHANGEMENT DE LISTE DE VILLES
        addResultViewModel.getTownChoicesLiveData().observe(getViewLifecycleOwner(), towns -> {
            try {
                LogUtils.d(TAG_NAME, "debut observer towns");
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
                LogUtils.d(TAG_NAME, "fin observer towns");
            }
        });
        // FIN OBSERVATION DUN CHANGEMENT DE LISTE DE VILLES

        // OBSERVATION DUN NOUVEAU RESULTAT
        addResultViewModel.getAddedResultLiveData().observe(getViewLifecycleOwner(), addResultRunnableResponse -> {
            try {
                LogUtils.d(TAG_NAME, "debut observer newResult");
                LogUtils.v(TAG_NAME, "observer newResult - reactivation du bouton ajouter resultat");
                buttonAjouter.setEnabled(true);
                buttonAjouter.setText(R.string.button_label_ajouter_resultat);
                final Result result = addResultRunnableResponse.getResult();
                final String message = addResultRunnableResponse.getMessage();
                if (result != null) {
                    LogUtils.v(TAG_NAME, "observer newResult - insertion en base de donnees");
                    addResultViewModel.onNewResultCreated(result);
                    LogUtils.v(TAG_NAME, "observer newResult - bascule sur le fragment season");
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_season);
                } else {
                    LogUtils.w(TAG_NAME, "newResult rendu null");
                }
                if (message != null) {
                    LogUtils.v(TAG_NAME, "observer newResult - affichage toast message");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur observer newResult, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.d(TAG_NAME, "fin observer newResult");
            }
        });
        // FIN OBSERVATION DUN NOUVEAU RESULTAT

        // OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES CLASSES DE COURSES
        addResultViewModel.getGridChoicesLiveData().observe(getViewLifecycleOwner(), gridsList -> {
            try {
                LogUtils.d(TAG_NAME, "debut observer gridsList");
                final String helperText;
                if (gridsList != null) {
                    helperText = getString(R.string.combo_classes_helper_text_ok, vueService.getLibelleFromCodeVue(vueViewModel.getCodeVueLiveData().getValue()), gridsList.size());
                } else {
                    gridsList = new ArrayList<>();
                    textInputLayoutClasse.setHelperText(getString(R.string.combo_classes_helper_text_ko));
                    helperText = getString(R.string.combo_classes_helper_text_ko);
                }
                LogUtils.v(TAG_NAME, "  observer gridsList - mise a jour liste des grilles");
                classesListAdapter.submitList(gridsList);
                LogUtils.v(TAG_NAME, "  observer gridsList - mise a jour du helperText en conséquence");
                textInputLayoutClasse.setHelperText(helperText);
                LogUtils.v(TAG_NAME, "  observer gridsList - remise a vide du helperText des positions");
                textInputLayoutPos.setHelperText(getString(R.string.vide));
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur observer gridsList, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.d(TAG_NAME, "fin observer gridsList");
            }
        });
        // FIN OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES CLASSES DE COURSES

        // OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES POSITIONS
        addResultViewModel.getPosChoicesLiveData().observe(getViewLifecycleOwner(), posList -> {
            try {
                LogUtils.d(TAG_NAME, "debut observer posList");
                final String helperText;
                if (posList != null) {
                    helperText = getString(R.string.combo_pos_helper_text_ok, posList.size(), autoCompleteTextViewClasses.getText());
                } else {
                    posList = addResultViewModel.getIntegerList50();
                    helperText = getString(R.string.combo_pos_helper_text_ko, addResultViewModel.getIntegerList50().size(), autoCompleteTextViewClasses.getText());
                }
                LogUtils.v(TAG_NAME, "  observer posList - mise a jour de la liste des pos");
                posListAdapter.submitList(posList);
                LogUtils.v(TAG_NAME, "  observer posList - mise a jour du helperText en conséquence");
                textInputLayoutPos.setHelperText(helperText);
                LogUtils.v(TAG_NAME, "  observer posList - remise a vide du helperText des positions");
                autoCompleteTextViewPos.setText("");
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "probleme sur observer posList, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.d(TAG_NAME, "fin observer posList");
            }
        });
        // FIN OBSERVATION DUNE NOUVELLE LISTE DE CHOIX DANS LA LISTE DEROULANTE DES POSITIONS

        // ECOUTEUR - SELECTION DUNE NOUVELLE CLASSE DE COURSE
        autoCompleteTextViewClasses.setOnItemClickListener((parent, maview, position, id) -> {
            try {
                LogUtils.d(TAG_NAME, "debut selection itemGrid");
                String itemValue = parent.getItemAtPosition(position).toString();
                LogUtils.v(TAG_NAME, "  selection itemGrid - selection = <" + itemValue + "> - envoi du job asynchrone de chargement des positions");
                addResultViewModel.loadPosChoicesAsync(itemValue);
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "  selection itemGrid - probleme sur observer posList, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.d(TAG_NAME, "fin selection itemGrid");
            }
        });
        // FIN ECOUTEUR - SELECTION DUNE NOUVELLE CLASSE DE COURSE

        // ECOUTEUR - BOUTON AJOUT RESULTAT
        buttonAjouter.setOnClickListener(event -> {
            try {
                LogUtils.d(TAG_NAME, "debut onClick addResultButton");
                LogUtils.v(TAG_NAME, "  onClick addResultButton - desactivation bouton et changement label");
                buttonAjouter.setEnabled(false);
                buttonAjouter.setText(R.string.button_label_calcul_en_cours);
                LogUtils.v(TAG_NAME, "  onClick addResultButton - recuperation et extraction donnees formulaire");
                final Editable placeEditable = autoCompleteTextViewPlace.getText();
                final Editable libelleEditable = autoCompleteTextViewClasses.getText();
                final Editable posEditable = autoCompleteTextViewPos.getText();
                final Editable prtsEditable = autoCompleteTextViewPrts.getText();
                // obtention des 4 variables telles que saisies
                final String place = placeEditable == null ? null : String.valueOf(placeEditable);
                final String libelle = libelleEditable == null ? null : String.valueOf(libelleEditable);
                final String pos = posEditable == null ? null : String.valueOf(posEditable);
                final String prts = prtsEditable == null ? null : String.valueOf(prtsEditable);
                LogUtils.v(TAG_NAME, "  onClick addResultButton - appel job calcul points");
                addResultViewModel.addResultApiAsync(place, libelle, pos, prts);
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "  onClick addResultButton - probleme technique, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.d(TAG_NAME, "fin onClick sur bouton ajouter");
            }
        });
        // FIN ECOUTEUR - BOUTON AJOUT RESULTAT
        LogUtils.onViewCreatedEnd(TAG_NAME);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}