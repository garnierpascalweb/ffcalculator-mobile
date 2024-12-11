package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSeasonBinding;

public class SeasonFragment extends Fragment {

    private static final String TAG_NAME = "SeasonFragment";
    private FragmentSeasonBinding binding;
    private SeasonViewModel resultViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultViewModel = new ViewModelProvider(this).get(SeasonViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // affecte le binding
        binding = FragmentSeasonBinding.inflate(inflater, container, false);
        // binding donne la vue
        View root = binding.getRoot();
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

        //TODO 1.0.0 le swip ne marche pas dans le fragment puisque le swip est porté par le fragment


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}