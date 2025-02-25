package com.gpwsofts.ffcalculator.mobile.ui.season;

import android.os.Bundle;
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

import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSeasonBinding;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

public class SeasonFragment extends Fragment {

    private static final String TAG_NAME = "SeasonFragment";
    private FragmentSeasonBinding binding;
    private SeasonViewModel seasonViewModel;
    private VueViewModel vueViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG_NAME, "appel de onCreate");
        seasonViewModel = new ViewModelProvider(requireActivity()).get(SeasonViewModel.class);
        vueViewModel = new ViewModelProvider(requireActivity()).get(VueViewModel.class);
        LogUtils.i(TAG_NAME, "fin appel de onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onCreateView");
        binding = FragmentSeasonBinding.inflate(inflater, container, false);
        LogUtils.i(TAG_NAME, "fin appel de onCreateView");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "debut appel de onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        /**
         * Recuperation des elements graphiques
         */
        final TextView textViewPts = binding.textAllpts;
        final TextView textViewPos = binding.textMypos;
        final RecyclerView resultRV = binding.idRVCourse;
        /**
         * Fin recuperation des elements graphiques
         */

        /**
         * Initialisation des adapters
         */
        final ResultListAdapter resultListAdapter = new ResultListAdapter(new ResultListAdapter.ResultDiff());
        resultRV.setAdapter(resultListAdapter);
        resultRV.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        resultRV.setHasFixedSize(true);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                LogUtils.v(TAG_NAME, "demande de suppression de resultat par swipe");
                seasonViewModel.delete(resultListAdapter.getItemFromAdapter(viewHolder.getAbsoluteAdapterPosition()));
            }
        });
        LogUtils.i(TAG_NAME, "attachement a la recyclerView du itemTouchHelper ");
        itemTouchHelper.attachToRecyclerView(resultRV);


        /**
         * Observation d'un changement dans la liste des résultats
         */
        seasonViewModel.getAllResultsLiveData().observe(getViewLifecycleOwner(), results -> {
            LogUtils.i(TAG_NAME, "debut observer results");
            if (results != null){
                LogUtils.d(TAG_NAME, "observer results - la liste chargee n'est pas nulle");
                LogUtils.d(TAG_NAME, "observer results - mise a jour de l'adapter de la liste des resultats (désormais <" + results.size() + "> élements)");
                resultListAdapter.submitList(results);
            }
            LogUtils.i(TAG_NAME, "fin observer results");
        });
        /**
         * Fin Observation d'un changement dans la liste des résultats
         */

        /**
         * Observation d'un changement du nombre total de points
         */
        seasonViewModel.getAllPtsLiveData().observe(getViewLifecycleOwner(), pts -> {
            LogUtils.i(TAG_NAME, "debut observer allPts");
            if (null != pts) {
                LogUtils.d(TAG_NAME, "observer allPts - le resultat rendu n'est pas null");
                if (Double.compare(pts, seasonViewModel.getCurrentPts()) !=0){
                    // valeur rendue differente de celle en cache, recalcul de la position au classement antional
                    LogUtils.d(TAG_NAME, "observer allPts - la valeur rendue est differente de celle en cache");
                    final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
                    textViewPos.setText(getString(R.string.label_classement_national_loading));
                    LogUtils.d(TAG_NAME, "observer allPts - envoi du job asynchrone de recherche de la position pour <" + pts +"> pts sur le classeemnt <" + classType + ">");
                    seasonViewModel.searchPosApi(pts, classType);
                    seasonViewModel.setCurrentPts(pts);
                    seasonViewModel.setCurrentClassType(classType);
                } else {
                    // valeur rendue egale a celle en cache, pas recalcul de la position au classement antional saud si indfini
                    LogUtils.d(TAG_NAME, "observer allPts - la valeur rendue est la meme que celle en cache");
                    if (null == seasonViewModel.getCurrentPos()){
                        LogUtils.d(TAG_NAME, "observer allPts - pas de position en cache");
                        final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
                        LogUtils.d(TAG_NAME, "observer allPts - envoi du job asynchrone de recherche de la position pour <" + pts +"> pts sur le classeemnt <" + classType + ">");
                        seasonViewModel.searchPosApi(pts,classType);
                    } else {
                        LogUtils.v(TAG_NAME, "observer allPts - conservation de la position en cache = <" + seasonViewModel.getCurrentPos() + ">");
                        textViewPos.setText(getString(R.string.label_classement_national_ok, seasonViewModel.getCurrentClassType(), seasonViewModel.getCurrentPos()));
                    }
                }
                textViewPts.setText(getString(R.string.label_total_pts_ok, pts));
            } else {
                LogUtils.d(TAG_NAME, "observer allPts - le resultat rendu est null - aucun resultat");
                textViewPts.setText(getString(R.string.label_aucun_resultat));
                textViewPos.setText("");
                LogUtils.w(TAG_NAME, "la valeur de pts est pas renseignee");
            }
            LogUtils.i(TAG_NAME, "fin observer allPts");
        });

        /**
         * Observation d'un changement sur la position au classement national
         */
        seasonViewModel.getOverAllPosLiveData().observe(getViewLifecycleOwner(), overAllPos -> {
            LogUtils.i(TAG_NAME, "debut observer overAllPos");
            final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
            final String textFieldPosText;
            if (overAllPos != null) {
                LogUtils.d(TAG_NAME, "observer allPts - la position rendue n'est pas null = <" + overAllPos + ">");
                LogUtils.d(TAG_NAME, "observer allPts - mise a jour du textField pour affichage de la position");
                textFieldPosText = getString(R.string.label_classement_national_ok, classType, overAllPos);
                LogUtils.d(TAG_NAME, "observer allPts - mise en cache de la position et du type de classement");
                seasonViewModel.setCurrentPos(overAllPos);
                seasonViewModel.setCurrentClassType(classType);
            } else {
                LogUtils.w(TAG_NAME, "observer allPts - la position rendue est null");
                textFieldPosText = getString(R.string.label_classement_national_ko);
            }
            LogUtils.d(TAG_NAME, "observer allPts - mise a jour du textField pour affichage de la position");
            textViewPos.setText(textFieldPosText);
            LogUtils.i(TAG_NAME, "fin observer overAllPos");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}