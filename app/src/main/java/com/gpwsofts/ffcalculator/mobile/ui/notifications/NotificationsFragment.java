package com.gpwsofts.ffcalculator.mobile.ui.notifications;

import android.os.Bundle;
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
        AnyChartView anyChartView = binding.anyChartView;
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