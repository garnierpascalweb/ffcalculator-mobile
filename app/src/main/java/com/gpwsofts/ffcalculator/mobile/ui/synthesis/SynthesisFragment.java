package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.gpwsofts.ffcalculator.mobile.constants.SharedPreferencesConstants;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSynthesisBinding;
import com.gpwsofts.ffcalculator.mobile.ui.result.ResultViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.shared.SharedPrefsViewModel;

public class SynthesisFragment extends Fragment {
    private static final String TAG_NAME = "SynthesisFragment";
    private FragmentSynthesisBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SynthesisViewModel notificationsViewModel = new ViewModelProvider(this).get(SynthesisViewModel.class);
        SharedPrefsViewModel sharedPrefsViewModel = new ViewModelProvider(getActivity()).get(SharedPrefsViewModel.class);
        ResultViewModel resultViewModel = new ViewModelProvider(getActivity()).get(ResultViewModel.class);
        binding = FragmentSynthesisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*
        //PG chart
        final String[] colors = new String[] {"#F5B7B1", "#3498DB", "#85C1E9", "#AED6F1"};
        AnyChartView anyChartView = binding.anyChartView;
        Pyramid pyramid = AnyChart.pyramid();
        List<DataEntry> data = new ArrayList<>();

        // On part de la base dez la pyramide
        data.add(new ValueDataEntry(getString(R.string.label_open3), 45));
        data.add(new ValueDataEntry(getString(R.string.label_open2), 30));
        data.add(new ValueDataEntry(getString(R.string.label_open1), 20));
        data.add(new ValueDataEntry(getString(R.string.label_elite), 5));



        pyramid.data(data);

        Legend legend = pyramid.legend();
        legend.enabled(true);
        legend.position("outside-right");
        legend.itemsLayout(LegendLayout.VERTICAL);
        legend.align(Align.TOP);

        pyramid.labels(false);
        //pyramid.fill(colors);
        anyChartView.setChart(pyramid);
         */


        final TextView textView = binding.textNotifications;
        final TextView textViewPts = binding.textAllpts;
        final TextView textViewPos = binding.textMypos;
        // notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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


        final TextInputEditText textInput = binding.idTIEVue;
        final Button button = binding.idBTVue;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update la vue
                String newVue = textInput.getText().toString();
                Log.i(TAG_NAME, "mise a jour de la vue vers " + newVue);
                sharedPrefsViewModel.updateVue(newVue);
            };
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}