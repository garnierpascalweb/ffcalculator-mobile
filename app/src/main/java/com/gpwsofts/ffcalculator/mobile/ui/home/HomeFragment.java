package com.gpwsofts.ffcalculator.mobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentHomeBinding;
import com.gpwsofts.ffcalculator.mobile.viewmodel.ResultViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    private ResultViewModel resultViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextInputEditText textInputEditTextPlace = binding.idTIETPlace;
        final TextInputLayout textInputLayoutSpinnerClasses = binding.idTISPClasses;
        final TextInputLayout textInputLayoutSpinnerPos = binding.idTISPPos;
        final TextInputLayout textInputLayoutSpinnerPrts = binding.idTISPPrts;
        final Button buttonAjouter = binding.idBTAjouter;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}