package com.gpwsofts.ffcalculator.mobile.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentDashboardBinding;
import com.gpwsofts.ffcalculator.mobile.model.IResult;


import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardFragment extends Fragment {

    private static final String TAG_NAME = "DashboardFragment";
    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // instancie le viewModel par ViewModelProvider
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        // affecte le binding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        // binding donne la vue
        View root = binding.getRoot();
        // la vue donne le recyclerView
        RecyclerView resultRV = root.findViewById(R.id.idRVCourse);
        // instantiotion de l'adaper
        ResultAdapter resultAdapter = new ResultAdapter(root.getContext(), dashboardViewModel.getResult());
        Log.i(TAG_NAME, "affichage des resultats au nombre de " + resultAdapter.getItemCount());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        resultRV.setLayoutManager(linearLayoutManager);
        resultRV.setAdapter(resultAdapter);
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}