package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentDashboardBinding;
import com.gpwsofts.ffcalculator.mobile.ui.result.ResultViewModel;

import java.util.List;

public class SeasonFragment extends Fragment {

    private static final String TAG_NAME = "SeasonFragment";
    private FragmentDashboardBinding binding;
    private ResultViewModel resultViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // affecte le binding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        // binding donne la vue
        View root = binding.getRoot();
        // RecyclerView
        RecyclerView resultRV = root.findViewById(R.id.idRVCourse);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        resultRV.setLayoutManager(linearLayoutManager);
        resultRV.setHasFixedSize(true);
        // Adapter
        final ResultAdapter resultAdapter = new ResultAdapter();
        resultRV.setAdapter(resultAdapter);
       // View Model
        resultViewModel = new ViewModelProvider(getActivity()).get(ResultViewModel.class);
        resultViewModel.getAllResults().observe(getViewLifecycleOwner(), new Observer<List<Result>>(){
            @Override
            public void onChanged(List<Result> results) {
                resultAdapter.setResults(results);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}