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

import com.gpwsofts.ffcalculator.mobile.FFCalculatorApplication;
import com.gpwsofts.ffcalculator.mobile.R;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.databinding.FragmentSeasonBinding;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

public class SeasonFragment extends Fragment {

    private static final String TAG_NAME = "SeasonFragment";
    private FragmentSeasonBinding binding;
    private SeasonViewModel seasonViewModel;
    private VueViewModel vueViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onCreate");
        super.onCreate(savedInstanceState);
        seasonViewModel = new ViewModelProvider(requireActivity()).get(SeasonViewModel.class);
        vueViewModel = new ViewModelProvider(requireActivity()).get(VueViewModel.class);
        LogUtils.i(TAG_NAME, "fin appel de onCreate");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onCreateView");
        binding = FragmentSeasonBinding.inflate(inflater, container, false);
        LogUtils.d(TAG_NAME, "onCreateView - chargement asynchrone vue");
        vueViewModel.loadVueAsync();
        LogUtils.i(TAG_NAME, "fin appel de onCreateView");
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtils.d(TAG_NAME, "appel de onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        // RECUPERATION DES ELEMENTS GRAPHIQUES
        final TextView textViewPts = binding.textAllpts;
        final TextView textViewPos = binding.textMypos;
        final RecyclerView resultRV = binding.idRVCourse;
        // FIN RECUPERATION DES ELEMENTS GRAPHIQUES

        // INITIALISATION DES ADAPTERS
        final ResultListAdapter resultListAdapter = new ResultListAdapter(new ResultListAdapter.ResultDiff());
        resultRV.setAdapter(resultListAdapter);
        resultRV.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        resultRV.setHasFixedSize(true);
        // FIN INITIALISATION DES ADAPTERS

        // DEFINITION DE ItemTouchHelper POUR LE SWITCH SUPPRESSION
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    LogUtils.d(TAG_NAME, "demande suppression de resultat par swipe");
                    seasonViewModel.delete(resultListAdapter.getItemFromAdapter(viewHolder.getAbsoluteAdapterPosition()));
                } catch (Exception e) {
                    LogUtils.e(TAG_NAME, "probleme suppression de resultat par swipe");
                    FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
                } finally {
                    LogUtils.d(TAG_NAME, "fin demande suppression de resultat par swipe");
                }
            }
        });
        LogUtils.d(TAG_NAME, "attachement recyclerView du itemTouchHelper ");
        itemTouchHelper.attachToRecyclerView(resultRV);
        // FIN DEFINITION DE ItemTouchHelper POUR LE SWITCH SUPPRESSION

        // OBSERVATION DUN CHANGEMENT DE VUE
        vueViewModel.getVueLiveData().observe(getViewLifecycleOwner(), vue -> {
            try{
                LogUtils.i(TAG_NAME, "debut observer vue");
                // si on a changé de type de classement, faut recalculer
                final String vueClassType = vue.getMapClass();
                if (vueClassType.equals(seasonViewModel.getCurrentClassType())){
                    LogUtils.d(TAG_NAME, "type classement nouvelle vue <" + vue.getCode() + "> identique en cache <" + vueClassType + "> - pas de recalcul");
                } else {
                    LogUtils.d(TAG_NAME, "type classement nouvelle vue <" + vue.getCode() + "> (" + vueClassType + ") different du cache <" + seasonViewModel.getCurrentClassType() + "> - recalcul");
                    seasonViewModel.searchPosApi(seasonViewModel.getCurrentPts(), vueClassType);
                    seasonViewModel.setCurrentClassType(vueClassType);
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "observer vue - probleme sur observer vue, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer vue");
            }
        });

        // OBSERVATION DUN CHANGEMENT DANS LA LISTE DE RESULTATS
        seasonViewModel.getAllResultsLiveData().observe(getViewLifecycleOwner(), results -> {
            try {
                LogUtils.i(TAG_NAME, "debut observer results");
                if (results != null) {
                    LogUtils.d(TAG_NAME, "observer results - update liste resultats (désormais <" + results.size() + "> élements)");
                    resultListAdapter.submitList(results);
                }
            } catch (Exception e) {
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer results");
            }
        });
        // FIN OBSERVATION DUN CHANGEMENT DANS LA LISTE DE RESULTATS

        // OBSERVATION DUN CHANGEMENT DANS LE NOMBRE DE POINTS
        seasonViewModel.getAllPtsLiveData().observe(getViewLifecycleOwner(), pts -> {
            try {
                LogUtils.i(TAG_NAME, "debut observer allPts");
                if (null != pts) {
                    if (Double.compare(pts, seasonViewModel.getCurrentPts()) != 0) {
                        // valeur rendue differente de celle en cache, recalcul de la position au classement antional
                        final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
                        textViewPos.setText(getString(R.string.label_classement_national_loading));
                        LogUtils.d(TAG_NAME, "observer allPts - envoi job asynchrone recherche position pour <" + pts + "> pts sur le classement <" + classType + ">");
                        seasonViewModel.searchPosApi(pts, classType);
                        seasonViewModel.setCurrentPts(pts);
                        seasonViewModel.setCurrentClassType(classType);
                    } else {
                        LogUtils.d(TAG_NAME, "observer allPts - valeur identique au cache <" + pts + "> - pas de recalcul");
                        if (null == seasonViewModel.getCurrentPos()) {
                            final String classType = vueViewModel.getVueLiveData().getValue().getMapClass();
                            LogUtils.d(TAG_NAME, "observer allPts - envoi job asynchrone recherche position pour <" + pts + "> pts sur le classeemnt <" + classType + ">");
                            seasonViewModel.searchPosApi(pts, classType);
                        } else {
                            LogUtils.v(TAG_NAME, "observer allPts - conservation de la position en cache = <" + seasonViewModel.getCurrentPos() + ">");
                            textViewPos.setText(getString(R.string.label_classement_national_ok, seasonViewModel.getCurrentClassType(), seasonViewModel.getCurrentPos()));
                        }
                    }
                    textViewPts.setText(getString(R.string.label_total_pts_ok, pts));
                } else {
                    textViewPts.setText(getString(R.string.label_aucun_resultat));
                    textViewPos.setText("");
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "observer allPts - probleme technique",e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer allPts");
            }
        });
        // FIN OBSERVATION DUN CHANGEMENT DANS LE NOMBRE DE POINTS

        // OBSERVATION DUN CHANGEMENT DANS LA POSITION AU CLASSEMENT NATIONAL
        seasonViewModel.getOverAllPosLiveData().observe(getViewLifecycleOwner(), overAllPos -> {
            try {
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
            } catch (Exception e) {
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer overAllPos");
            }
        });
        // FIN OBSERVATION DUN CHANGEMENT DANS LA POSITION AU CLASSEMENT NATIONAL
        LogUtils.d(TAG_NAME, "fin appel de onViewCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}