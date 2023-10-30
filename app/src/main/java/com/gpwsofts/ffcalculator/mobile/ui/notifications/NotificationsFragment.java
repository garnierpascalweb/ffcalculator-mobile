package com.gpwsofts.ffcalculator.mobile.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.core.ui.Legend;
import com.anychart.enums.LegendLayout;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pyramid;
import com.anychart.enums.Align;
import com.anychart.palettes.RangeColors;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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


        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}