package com.gpwsofts.ffcalculator.mobile.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentDashboardBinding;
import com.gpwsofts.ffcalculator.mobile.model.IResult;


import androidx.lifecycle.Observer;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Create the observer which updates the UI.
        final Observer<IResult> resultObserver = new Observer<IResult>() {
            @Override
            public void onChanged(IResult iResult) {

            }
        };


        final MaterialCardView cardView = binding.card;

        dashboardViewModel.getResult().observe(getViewLifecycleOwner(), resultObserver);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}