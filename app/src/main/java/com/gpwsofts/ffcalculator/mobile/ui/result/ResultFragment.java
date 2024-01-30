package com.gpwsofts.ffcalculator.mobile.ui.result;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
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
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentHomeBinding;
import com.gpwsofts.ffcalculator.mobile.viewmodel.SharedPrefsViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class ResultFragment extends Fragment {
    private static final String TAG_NAME = "HomeFragment";
    private ResultViewModel resultViewModel;
    private SharedPrefsViewModel sharedPrefsViewModel;
    private FragmentHomeBinding binding;

    TextInputEditText textInputEditTextPlace;
    TextInputLayout textInputLayoutSpinnerClasses;
    TextInputLayout textInputLayoutSpinnerPos;
    TextInputLayout textInputLayoutSpinnerPrts;

    AutoCompleteTextView autoCompleteTextViewClasses;
    Button buttonAjouter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        resultViewModel = new ViewModelProvider(requireActivity()).get(ResultViewModel.class);
        sharedPrefsViewModel = new ViewModelProvider(requireActivity()).get(SharedPrefsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //TODO 1.0.0 recuperation de UUID a mettre autre part que la
        String android_device_id = Settings.Secure.getString(this.requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(TAG_NAME, "device id " + android_device_id);
        textInputEditTextPlace = binding.idTIETPlace;
        textInputLayoutSpinnerClasses = binding.idTISPClasses;
        textInputLayoutSpinnerPos = binding.idTISPPos;
        autoCompleteTextViewClasses = binding.idTVAutoClasses;
        // https://stackoverflow.com/questions/18685898/android-clear-in-costom-arrayadapter-java-lang-unsupportedoperationexception
        //ArrayList<String> defaultClasses = new ArrayList<>();
        //defaultClasses.addAll(Arrays.asList(getResources().getStringArray(R.array.classes_for_elite)));
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(),  android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.classes_for_gen))));
        //arrayAdapter.setDropDownViewResource();
        //arrayAdapter.setDropDownViewResource(R.array.planets_array);
        autoCompleteTextViewClasses.setAdapter(arrayAdapter);
        //autoCompleteTextViewClasses.get

        autoCompleteTextViewClasses.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(TAG_NAME, "nouvel item selectionne ");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );
        textInputLayoutSpinnerPrts = binding.idTISPPrts;
        buttonAjouter = binding.idBTAjouter;

        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResult();
            }
        });
       // this.getString(R.string.vue_elite);
        this.sharedPrefsViewModel.getVue().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onChanged(String vue) {
                        Log.i(TAG_NAME, "mise a jour de la liste deroulante des classes de course en fonction de la vue " + vue);
                        Log.d(TAG_NAME, "clear des eventuels choix deja sectiones");
                        autoCompleteTextViewClasses.clearListSelection();
                        Log.d(TAG_NAME, "clear du arrayAdapter");
                        arrayAdapter.clear();
                        Log.d(TAG_NAME, "appel du service VueService pour recuperer les classes associees a la vue " + vue);
                        ArrayList<String> listOfClasses = FFCalculatorApplication.instance.getServicesManager().getVueService(getResources()).getComboboxClassesForVue(vue);
                        Log.d(TAG_NAME, "addAll " + listOfClasses.size() + " nouvelles classes");
                        listOfClasses.stream().forEach(classe -> Log.v(TAG_NAME, classe.toString()));
                        arrayAdapter.addAll(listOfClasses);
                        Log.d(TAG_NAME, "notification que arrayAdapter a été réalimenté");
                        //TODO 1.0.0 verifier si ce notify est vraiment necessiare, depuis qu'on amis en place le getFilter()
                        arrayAdapter.notifyDataSetChanged();
                        Log.d(TAG_NAME, "filter sur autoCompleteTextViewClasses");
                        arrayAdapter.getFilter().filter(autoCompleteTextViewClasses.getText(), null);
                        //autoCompleteTextViewClasses.showDropDown();
                    }
                });


        // selon https://stackoverflow.com/questions/63548323/how-to-use-viewmodel-in-a-fragment
        /*
        resultViewModel = new ViewModelProvider(requireActivity()).get(ResultViewModel.class);
        resultViewModel.getAllResults().observe(getActivity(), new Observer<List<Result>>(){
                    @Override
                    public void onChanged(List<Result> results) {
                        Toast.makeText(getActivity(), "a changé", Toast.LENGTH_SHORT).show();
                        homeViewModel.setText(results.size() + " elements");
                    }
                }
                );
         */
        return root;
    }

    private void saveResult() {
        String place = String.valueOf(this.textInputEditTextPlace.getText());
        //String classe = String.valueOf(this.textInputLayoutSpinnerClasses.getEditText().getText());
        String classe = "1.25.1";
        Log.i(TAG_NAME, "ajout de la course " + place);
        Result result = new Result();
        result.setPlace("Trigance");
        result.setLogo("Open 1/2/3");
        result.setPts(10.29);
        result.setPrts(147);
        result.setPos(16);
        result.setLibelle("Open 1/2/3 Access");
        result.setIdClasse("1.24.0");
        resultViewModel.insert(result);
        Toast.makeText(this.getContext(), "Result Save", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}