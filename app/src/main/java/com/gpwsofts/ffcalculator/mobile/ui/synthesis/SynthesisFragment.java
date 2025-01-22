package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

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

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSynthesisBinding;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

public class SynthesisFragment extends Fragment {
    private static final String TAG_NAME = "SynthesisFragment";
    private FragmentSynthesisBinding binding;
    private SynthesisViewModel synthesisViewModel;
    private VueViewModel vueViewModel;
    private TextView textViewPts;
    private TextView textViewPos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synthesisViewModel = new ViewModelProvider(this).get(SynthesisViewModel.class);
        vueViewModel = new ViewModelProvider(this).get(VueViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSynthesisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        textViewPts = binding.textAllpts;
        textViewPos = binding.textMypos;


        // observation du total des points
        // Update UI
        synthesisViewModel.getPts().observe(getViewLifecycleOwner(), pts -> {
            Log.i(TAG_NAME, "debut observer getPts");
            if (null != pts) {
                textViewPts.setText(getString(R.string.label_total_pts, pts));
                final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
                searchPosApi(pts, classType);
            } else {
                textViewPts.setText(getString(R.string.label_aucun_resultat));
                textViewPos.setText("");
                Log.w(TAG_NAME, "la valeur de pts est pas renseignee");
            }
            Log.i(TAG_NAME, "fin observer getPts");
        });
        // observation du total des positions
        // Update UI
        synthesisViewModel.getPos().observe(getViewLifecycleOwner(), pos -> {
            Log.i(TAG_NAME, "debut observer getPos");
            final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
            if (pos != null) {
                // textViewPos.setText("Classement National : " + pos + " eme");
                textViewPos.setText(getString(R.string.label_classement_national, classType, pos));
            } else {
                textViewPos.setText(getString(R.string.label_classement_national_ko));
                Log.w(TAG_NAME, "la valeur de pos est pas renseignee apres appel au service");
            }
            Log.i(TAG_NAME, "fin observer getPos");
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void searchPosApi(double pts, String classType) {
        synthesisViewModel.searchPosApi(pts, classType);
    }


}