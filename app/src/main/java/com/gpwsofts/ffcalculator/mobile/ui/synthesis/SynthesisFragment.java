package com.gpwsofts.ffcalculator.mobile.ui.synthesis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.LinearGauge;
import com.anychart.charts.Pyramid;
import com.anychart.core.ui.Legend;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.Layout;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Orientation;
import com.anychart.enums.Position;
import com.anychart.scales.OrdinalColor;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSynthesisBinding;
import com.anychart.enums.MarkerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
/*
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

         */

        LinearGauge linearGauge = AnyChart.linear();

        linearGauge.data(new SingleValueDataSet(new Double[] { 5.3D }));

        linearGauge.layout(Layout.VERTICAL);

        linearGauge.label(0)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("-50px")
                .offsetX("50px")
                .fontColor("black")
                .fontSize(17);
        linearGauge.label(0).text("Total Rainfall");

        linearGauge.label(1)
                .position(Position.LEFT_CENTER)
                .anchor(Anchor.LEFT_CENTER)
                .offsetY("40px")
                .offsetX("50px")
                .fontColor("#777777")
                .fontSize(17);
        linearGauge.label(1).text("Drought Hazard");

        linearGauge.label(2)
                .position(Position.RIGHT_CENTER)
                .anchor(Anchor.RIGHT_CENTER)
                .offsetY("40px")
                .offsetX("50px")
                .fontColor("#777777")
                .fontSize(17);
        linearGauge.label(2).text("Flood Hazard");

        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 0, to: 2, color: ['red 0.5'] }",
                "{ from: 2, to: 3, color: ['yellow 0.5'] }",
                "{ from: 3, to: 7, color: ['green 0.5'] }",
                "{ from: 7, to: 8, color: ['yellow 0.5'] }",
                "{ from: 8, to: 10, color: ['red 0.5'] }"
        });

        linearGauge.scaleBar(0)
                .width("5%")
                .colorScale(scaleBarColorScale);

        linearGauge.marker(0)
                .type(MarkerType.TRIANGLE_DOWN)
                .color("red")
                .offset("-3.5%")
                .zIndex(10);

        linearGauge.scale()
                .minimum(0)
                .maximum(10);
//        linearGauge.scale().ticks

        linearGauge.axis(0)
                .minorTicks(false)
                .width("1%");
        linearGauge.axis(0)
                .offset("-1.5%")
                .orientation(Orientation.TOP)
                .labels("top");

        linearGauge.padding(0, 30, 0, 30);

        anyChartView.setChart(linearGauge);


        synthesisViewModel.getPts().observe(getViewLifecycleOwner(), pts -> {
            Double myPts = Optional.of(pts).orElse(0.0);
            textViewPts.setText(String.valueOf(myPts));
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