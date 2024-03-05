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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pyramid;
import com.anychart.core.ui.Legend;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSynthesisBinding;
import com.gpwsofts.ffcalculator.mobile.repository.ResultRepository;
import com.gpwsofts.ffcalculator.mobile.services.network.FFCPosResponse;
import com.gpwsofts.ffcalculator.mobile.ui.season.SeasonViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SynthesisFragment extends Fragment {
    private static final String TAG_NAME = "SynthesisFragment";
    private FragmentSynthesisBinding binding;
    private SynthesisViewModel synthesisViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synthesisViewModel = new ViewModelProvider(this).get(SynthesisViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSynthesisBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textViewPts = binding.textAllpts;
        final TextView textViewPos = binding.textMypos;

        AnyChartView anyChartView = binding.anyChartView;//findViewById(R.id.any_chart_view);
        //anyChartView.setProgressBar(binding.progressBar);

        Pyramid pyramid = AnyChart.pyramid();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("TV promotion", 6371664));
        data.add(new ValueDataEntry("Radio promotion", 7216301));
        data.add(new ValueDataEntry("Advertising leaflets", 1486621));
        data.add(new ValueDataEntry("Before advertising started", 1386622));
        pyramid.data(data);
        Legend legend = pyramid.legend();
        legend.enabled(true);
        legend.position("outside-right");
        legend.itemsLayout(LegendLayout.VERTICAL);
        legend.align(Align.TOP);

        pyramid.labels(false);

        anyChartView.setChart(pyramid);


        synthesisViewModel.getPts().observe(getViewLifecycleOwner(), pts -> {
            textViewPts.setText(String.valueOf(pts));
            searchPosApi(pts, "H");
        });
        synthesisViewModel.getPos().observe(getViewLifecycleOwner(), pos -> {
            textViewPos.setText(String.valueOf(pos));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void searchPosApi(double pts, String classType){
        synthesisViewModel.searchPosApi(pts, classType);
    }


}