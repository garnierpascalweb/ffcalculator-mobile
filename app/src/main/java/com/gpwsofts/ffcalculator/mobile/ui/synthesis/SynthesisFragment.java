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
        AnyChartView anyChartView = binding.anyChartView;
        LinearGauge linearGauge = AnyChart.linear();
        linearGauge.layout(Layout.HORIZONTAL);
        OrdinalColor scaleBarColorScale = OrdinalColor.instantiate();
        scaleBarColorScale.ranges(new String[]{
                "{ from: 0, to: 1885, color: ['green 0.5'] }",
                "{ from: 1885, to: 4730, color: ['#3296ff 0.5'] }",
                "{ from: 4730, to: 6115, color: ['#0064ff 0.5'] }",
                "{ from: 6115, to: 6600, color: ['#0000ff 0.5'] }",
                "{ from: 6600, to: 7000, color: ['#ff3300 0.5'] }"
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
                .minimum(1)
                .maximum(7000);
        linearGauge.legend("{ position:left, itemsLayout: horizontal, align: top}");
        linearGauge.padding(0, 30, 0, 30);
        anyChartView.setChart(linearGauge);
        // observation du total des points
        // Update UI
        synthesisViewModel.getPts().observe(getViewLifecycleOwner(), pts -> {
            Log.i(TAG_NAME, "debut observer getPts");
            if (null != pts){
                textViewPts.setText("Total des points : " + pts +" pts");
                //TODO 1.0.0 le classement national doit etre bati sur le type de vue
                searchPosApi(pts, "H");
            } else {
                textViewPts.setText("Aucun résultat");
                textViewPos.setText("");
                Log.w(TAG_NAME, "la valeur de pts est pas renseignee");
            }
            Log.i(TAG_NAME, "fin observer getPts");
        });
        // observation du total des positions
        // Update UI
        synthesisViewModel.getPos().observe(getViewLifecycleOwner(), pos -> {
            Log.i(TAG_NAME, "debut observer getPos");
            if (pos != null){
                textViewPos.setText("Classement National : " + pos + " eme");
                linearGauge.data(new SingleValueDataSet(new Integer[] { 7000-pos }));
            } else {
                textViewPos.setText("Classement National : Echec du calcul de la position");
                linearGauge.data(new SingleValueDataSet(new Integer[] { 7000 }));
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

    private void searchPosApi(double pts, String classType){
        synthesisViewModel.searchPosApi(pts, classType);
    }


}