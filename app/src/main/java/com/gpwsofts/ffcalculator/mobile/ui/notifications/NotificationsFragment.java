package com.gpwsofts.ffcalculator.mobile.ui.notifications;

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

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.core.ui.Legend;
import com.anychart.enums.LegendLayout;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pyramid;
import com.anychart.enums.Align;
import com.anychart.palettes.RangeColors;
import com.google.android.material.textfield.TextInputEditText;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.dao.Result;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentNotificationsBinding;
import com.gpwsofts.ffcalculator.mobile.viewmodel.ResultViewModel;
import com.gpwsofts.ffcalculator.mobile.viewmodel.SharedPrefsViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private static final String TAG_NAME = "NotificationsFragment";
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        SharedPrefsViewModel sharedPrefsViewModel = new ViewModelProvider(getActivity()).get(SharedPrefsViewModel.class);
        ResultViewModel resultViewModel = new ViewModelProvider(getActivity()).get(ResultViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
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
        // notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        sharedPrefsViewModel.getVue().getStringLiveData("vue", "G").observe(getViewLifecycleOwner(), new Observer<String>(){
            @Override
            public void onChanged(String s) {
                Log.i(TAG_NAME, "la vue a change et vaut desormais " + s);
                textView.setText(s);
                
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
                sharedPrefsViewModel.update(newVue);
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