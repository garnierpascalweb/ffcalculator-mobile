package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private Double lastPtsValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synthesisViewModel = new ViewModelProvider(requireActivity()).get(SynthesisViewModel.class);
        vueViewModel = new ViewModelProvider(requireActivity()).get(VueViewModel.class);
        lastPtsValue = Double.valueOf(0);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSynthesisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        textViewPts = binding.textAllpts;
        textViewPos = binding.textMypos;


        // observation du total des points
        // Update UI
        synthesisViewModel.getAllPtsLiveData().observe(getViewLifecycleOwner(), pts -> {
            Log.i(TAG_NAME, "debut observer getPts hh");
            if (null != pts) {
                int compare = Double.compare(pts, lastPtsValue);
                Log.d(TAG_NAME, "la comparaison de <" + pts + "> et <" + lastPtsValue + "> a rendu <" + compare + ">");
                if (compare != 0){
                    textViewPts.setText(getString(R.string.label_total_pts_ok, pts));
                    final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
                    searchPosApi(pts, classType);
                    lastPtsValue = pts;
                } else {
                    Log.d(TAG_NAME, "non non rien n'a changé tout tout a continué");
                }
            } else {
                textViewPts.setText(getString(R.string.label_aucun_resultat));
                textViewPos.setText("");
                Log.w(TAG_NAME, "la valeur de pts est pas renseignee");
            }
            Log.i(TAG_NAME, "fin observer getPts");
        });
        // observation du total des positions
        // Update UI
        synthesisViewModel.getOverAllPosLiveData().observe(getViewLifecycleOwner(), pos -> {
            Log.i(TAG_NAME, "debut observer getPos");
            final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
            if (pos != null) {
                // textViewPos.setText("Classement National : " + pos + " eme");
                textViewPos.setText(getString(R.string.label_classement_national_ok, classType, pos));
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