package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSeasonBinding;
import com.gpwsofts.ffcalculator.mobile.services.network.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.ui.result.ResultListAdapter;
import com.gpwsofts.ffcalculator.mobile.ui.result.ResultViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.shared.SharedPrefsViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonFragment extends Fragment {

    private static final String TAG_NAME = "SeasonFragment";
    private FragmentSeasonBinding binding;
    private ResultViewModel resultViewModel;
    private SharedPrefsViewModel sharedPrefsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultViewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        sharedPrefsViewModel = new ViewModelProvider(this).get(SharedPrefsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // affecte le binding
        binding = FragmentSeasonBinding.inflate(inflater, container, false);
        // binding donne la vue
        View root = binding.getRoot();
        // RecyclerView
        RecyclerView resultRV = root.findViewById(R.id.idRVCourse);
        final ResultListAdapter adapter = new ResultListAdapter(new ResultListAdapter.ResultDiff());
        resultRV.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        resultRV.setLayoutManager(linearLayoutManager);
        resultRV.setHasFixedSize(true);
        resultViewModel.getAllResults().observe(getViewLifecycleOwner() , results -> {
            adapter.submitList(results);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}