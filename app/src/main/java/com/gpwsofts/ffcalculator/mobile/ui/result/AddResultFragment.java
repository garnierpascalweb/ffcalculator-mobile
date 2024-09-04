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
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddResultFragment extends Fragment {
    private static final String TAG_NAME = "ResultFragment";
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

    private static final String VIDE = "";



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
        //TODO 1.0.0 recuperation de UUID a mettre autre part que la
        //String android_device_id = Settings.Secure.getString(this.requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        //Log.i(TAG_NAME, "device id " + android_device_id);
        //Log.i(TAG_NAME, "nombre de pts = " + this.resultViewModel.getAllPts());
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
            onUpdatedVue(vue);
            Log.i(TAG_NAME, "fin observer getVueLiveData = <" + vue + ">");
        });
        // observation d'un nouveau resultat
        addResultViewModel.getAddedResult().observe(getViewLifecycleOwner(), result -> {
            Log.i(TAG_NAME, "debut observer getAddedResult = <" + result + ">");
            Log.d(TAG_NAME, "  nouveau resultat a ajouter " + result);
            // result peut etre null si un probleme tchnique survient au calcul des points (mResult.postValue(null))
            if (result != null){
                // insertion en database
                addResultViewModel.onNewResultCreated(result);
                // reinitialisation de l'écran
                reinit();
                // navigation au fragment de saison
                Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_season);
                Toast.makeText(getActivity(), getString(R.string.toast_add_result_ok), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.toast_add_result_ko), Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG_NAME, "fin observer getAddedResult = <" + result + ">");
        });
        // observation de la liste des grilles
        // update UI
        addResultViewModel.getGridsChoices().observe(getViewLifecycleOwner(), gridsList -> {
            Log.i(TAG_NAME, "debut observer getGridsChoices");
            classesListAdapter.submitList(gridsList);
            //textInputLayoutClasse.setHelperText("Vue " + vueViewModel.getVueLiveData().getValue().getName() + " - " + gridsList.size() + " types d'épreuves disponibles");
            textInputLayoutClasse.setHelperText(getString(R.string.combo_classes_helper_text, vueViewModel.getVueLiveData().getValue().getName(), gridsList.size()));
            Log.i(TAG_NAME, "fin observer getGridsChoices");
        });

        // observation de la liste des positions
        // update UI
        addResultViewModel.getPosChoices().observe(getViewLifecycleOwner(), posList -> {
            Log.i(TAG_NAME, "debut observer getPosChoices");
            posListAdapter.submitList(posList);
            autoCompleteTextViewPos.setText(VIDE);
            // textInputLayoutPos.setHelperText("Points attribués aux " + posList.size()+ " premiers pour une épreuve de type " + autoCompleteTextViewClasses.getText());
            textInputLayoutPos.setHelperText(getString(R.string.combo_pos_helper_text, posList.size(),  autoCompleteTextViewClasses.getText() ));
            Log.i(TAG_NAME, "fin observer getPosChoices");
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
        Log.i(TAG_NAME, "fin appel de onCreateView");
        return root;
    }

    private void onUpdatedVue(Vue vue){
        addResultViewModel.updateGridChoices(vue);
        reinit();
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
            // connecte a internet
            final Editable placeEditable = textInputEditTextPlace.getText();
            final Editable libelleEditable = autoCompleteTextViewClasses.getText();
            final Editable posEditable = autoCompleteTextViewPos.getText();
            final Editable prtsEditable = autoCompleteTextViewPrts.getText();
            if (validateEditable(hintPlace, placeEditable) && validateEditable(hintType, libelleEditable) && validateEditable(hintPos, posEditable) && validateEditable(hintPrts, prtsEditable)){
                // champs correctement remplis
                final String place = String.valueOf(placeEditable);
                final String libelle = String.valueOf(libelleEditable);
                final int pos = Integer.valueOf(posEditable.toString());
                final int prts = Integer.valueOf(prtsEditable.toString());
                if (validateResultInput(place, libelle, pos, prts)){
                    addResultViewModel.createNewResult(place,libelle,pos,prts);
                }
            }
        } else {
            // pas connecte a internet
            Log.e(TAG_NAME, "pas de reseau");
            Toast.makeText(getActivity(), getString(R.string.toast_no_network), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Valide le contenu des champs du formulaire (non vide)
     * @since 1.0.0
     * @param name
     * @param editable
     * @return
     */
    private boolean validateEditable(String name, Editable editable){
        boolean isEmpty = (null == editable || editable.length() == 0);
        if (isEmpty)
            Toast.makeText(getActivity(), getString(R.string.toast_form_incomplet, name), Toast.LENGTH_SHORT).show();
        return !isEmpty;
    }

    /**
     * Valide les valeurs données dans le formulaire
     * @param place
     * @param libelle
     * @param pos
     * @param prts
     * @return
     */
    private boolean validateResultInput(String place, String libelle, int pos, int prts){
        boolean isValid = pos <= prts;
        if (!isValid)
            Toast.makeText(getActivity(), getString(R.string.toast_invalid_pos_prts), Toast.LENGTH_SHORT).show();
        return isValid;
    }

    /**
     * Reinitialise l'écran (apres la validation d'un resultat)
     * @since 1.0.0
     */
    private void reinit(){
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