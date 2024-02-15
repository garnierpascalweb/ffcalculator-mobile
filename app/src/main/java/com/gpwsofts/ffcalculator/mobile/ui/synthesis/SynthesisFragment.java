package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.constants.SharedPreferencesConstants;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSynthesisBinding;
import com.gpwsofts.ffcalculator.mobile.services.network.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.ui.result.ResultViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.shared.SharedPrefsViewModel;

import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SynthesisFragment extends Fragment {
    private static final String TAG_NAME = "SynthesisFragment";
    private FragmentSynthesisBinding binding;

    private ResultViewModel resultViewModel;
    private SharedPrefsViewModel sharedPrefsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultViewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        sharedPrefsViewModel = new ViewModelProvider(this).get(SharedPrefsViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SynthesisViewModel notificationsViewModel = new ViewModelProvider(this).get(SynthesisViewModel.class);
        binding = FragmentSynthesisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textNotifications;
        final TextView textViewPts = binding.textAllpts;
        final TextView textViewPos = binding.textMypos;
        final TextInputEditText textInput = binding.idTIEVue;
        resultViewModel.getAllResults().observe(getViewLifecycleOwner() , results -> {
            final double allPts = results.stream().mapToDouble(result -> result.getPts()).sorted().limit(15).sum();
            textViewPts.setText("Nombre de POINTS = " + allPts);
            final Call<FFCPosResponse> call = FFCalculatorApplication.instance.getServicesManager().getPosService().calcPos(allPts, "H");
            call.enqueue(new Callback<FFCPosResponse>() {
                @Override
                public void onResponse(Call<FFCPosResponse> call, Response<FFCPosResponse> response) {
                    if (response.isSuccessful()) {
                        int myPos = response.body().pos;
                        //sharedPrefsViewModel.updatePos(myPos);
                        textViewPos.setText(myPos + " eme au classement national FFC ");
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
        });
        sharedPrefsViewModel.getVue().getStringLiveData(SharedPreferencesConstants.KEY_VUE, SharedPreferencesConstants.DEFAULT_VALUE_VUE).observe(getViewLifecycleOwner(), new Observer<String>(){
            @Override
            public void onChanged(String s) {
                Log.i(TAG_NAME, "la vue a change et vaut desormais " + s);
                textView.setText(s);

            }
        });

        sharedPrefsViewModel.getPos().getIntegerLiveData(SharedPreferencesConstants.KEY_POS, Integer.MIN_VALUE).observe(getViewLifecycleOwner(), new Observer<Integer>(){
            @Override
            public void onChanged(Integer pos) {
                Log.i(TAG_NAME, "les pos a change et vaut desormais " + pos);
                textViewPos.setText(String.valueOf(pos.intValue()));
            }
        });

        sharedPrefsViewModel.getPts().getDoubleLiveData(SharedPreferencesConstants.KEY_PTS, Double.MIN_VALUE).observe(getViewLifecycleOwner(), new Observer<Double>(){
            @Override
            public void onChanged(Double pts) {
                Log.i(TAG_NAME, "les pts a change et vaut desormais " + pts);
                textViewPts.setText(String.valueOf(pts.doubleValue()));
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