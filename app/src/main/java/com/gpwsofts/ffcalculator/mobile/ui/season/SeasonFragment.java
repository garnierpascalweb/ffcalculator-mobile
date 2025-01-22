package com.gpwsofts.ffcalculator.mobile.ui.season;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSeasonBinding;
import com.gpwsofts.ffcalculator.mobile.ui.synthesis.SynthesisViewModel;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

public class SeasonFragment extends Fragment {

    private static final String TAG_NAME = "SeasonFragment";
    private FragmentSeasonBinding binding;
    private SeasonViewModel resultViewModel;
    private SynthesisViewModel synthesisViewModel;
    private VueViewModel vueViewModel;
    private TextView textViewPts;
    private TextView textViewPos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultViewModel = new ViewModelProvider(this).get(SeasonViewModel.class);
        synthesisViewModel = new ViewModelProvider(this).get(SynthesisViewModel.class);
        vueViewModel = new ViewModelProvider(this).get(VueViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // affecte le binding
        binding = FragmentSeasonBinding.inflate(inflater, container, false);
        // binding donne la vue
        View root = binding.getRoot();
        textViewPts = binding.textAllpts;
        textViewPos = binding.textMypos;
        // RecyclerView
        RecyclerView resultRV = root.findViewById(R.id.idRVCourse);
        final ResultListAdapter adapter = new ResultListAdapter(new ResultListAdapter.ResultDiff());
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.v(TAG_NAME, "demande de suppression de resultat par swipe");
                resultViewModel.delete(adapter.getItemFromAdapter(viewHolder.getAbsoluteAdapterPosition()));
            }
        });
        Log.i(TAG_NAME, "attachement a la recyclerView du itemTouchHelper ");
        itemTouchHelper.attachToRecyclerView(resultRV);
        resultRV.setAdapter(adapter);
        resultRV.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
        resultRV.setHasFixedSize(true);

        resultViewModel.getAllResults().observe(getViewLifecycleOwner(), results -> {
            Log.i(TAG_NAME, "debut observer getAllResults");
            adapter.submitList(results);
            Log.i(TAG_NAME, "fin observer getAllResults");
        });

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

        //TODO 1.0.0 le swip ne marche pas dans le fragment puisque le swip est porté par le fragment


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