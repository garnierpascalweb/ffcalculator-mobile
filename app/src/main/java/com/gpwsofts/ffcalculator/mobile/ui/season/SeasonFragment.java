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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        resultRV.setLayoutManager(linearLayoutManager);
        resultRV.setHasFixedSize(true);
        // Adapter
        final ResultAdapter resultAdapter = new ResultAdapter();
        resultRV.setAdapter(resultAdapter);
        resultViewModel.getAllResults().observe(getViewLifecycleOwner(), new Observer<List<Result>>(){
            @Override
            public void onChanged(List<Result> results) {
                Log.i(TAG_NAME, "observation d'un changement sur la liste des resultats");
                // mise a jour sur la UI
                Log.d(TAG_NAME, "mise a jour de la UI");
                resultAdapter.setResults(results);
                Log.d(TAG_NAME, "mise a jour de la UI ok pour " + results.size() + " resultats");
                // peut etre faire ce merdier en asynchrone
                // calculer la somme des points
                Log.d(TAG_NAME, "recalcul de la somme de tous les points et mise en sharedPrefs");
                double allPts = results.stream().mapToDouble(result -> result.getPts()).sum();
                // la mettre en shared preferences
                sharedPrefsViewModel.updatePts(allPts);
                Log.d(TAG_NAME, "recalcul de la somme de tous les points ok : <" + allPts + ">");
                // calculer le classement selon ces points
                //TODO 1.0.0 U17 pas en dur bien sur
                Log.d(TAG_NAME, "recalcul de la position au classement national et mise en sharedPrefs (appel http)");
                Call<FFCPosResponse> call = FFCalculatorApplication.instance.getServicesManager().getPosService().calcPos(allPts, "U17");
                call.enqueue(new Callback<FFCPosResponse>() {
                    @Override
                    public void onResponse(Call<FFCPosResponse> call, Response<FFCPosResponse> response) {
                        if (response.isSuccessful()) {
                            int myPos = response.body().pos;
                            sharedPrefsViewModel.updatePos(myPos);
                            Log.d(TAG_NAME,"recalcul de la position ok : <" + myPos + ">");
                        } else {
                            Log.w(TAG_NAME, "probleme sur le calcul de la position " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<FFCPosResponse> call, Throwable t) {
                        Log.w(TAG_NAME, "probleme sur l'appel au calcul de la position");
                    }
                });
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