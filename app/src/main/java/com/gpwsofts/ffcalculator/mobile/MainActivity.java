package com.gpwsofts.ffcalculator.mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.databinding.ActivityMainBinding;
import com.gpwsofts.ffcalculator.mobile.services.vue.IVueService;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_NAME = "MainActivity";
    private ActivityMainBinding binding;
    private VueViewModel vueViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            LogUtils.onCreateBegin(TAG_NAME);
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            vueViewModel = new ViewModelProvider(this).get(VueViewModel.class);
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_result, R.id.navigation_season, R.id.navigation_help).build();
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
            // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);

            if (vueViewModel.getCurrentCodeVue() == null)
                vueViewModel.loadCodeVueAsync();

            vueViewModel.getCodeVueLiveData().observe(this, codeVue -> {
                try {
                    final IVueService vueService = FFCalculatorApplication.instance.getServicesManager().getVueService(getResources().getStringArray(R.array.vues_libelles_array),getResources().getStringArray(R.array.vues_ids_array));
                    LogUtils.d(TAG_NAME, "debut observer vue = <" + codeVue + ">");
                    if (codeVue != null) {
                        if (codeVue.equals(vueViewModel.getCurrentCodeVue())) {
                            LogUtils.v(TAG_NAME, "  observer vue = <" + codeVue + "> - vue selectionnee identique en cache");
                        } else {
                            LogUtils.d(TAG_NAME, "  observer vue = <" + codeVue + "> nouvelle vue selectionnee <" + codeVue + ">");
                            vueViewModel.setCurrentCodeVue(codeVue);
                            Toast.makeText(this, getString(R.string.toast_update_vue_ok, vueService.getLibelleFromCodeVue(codeVue)), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LogUtils.w(TAG_NAME, "  observer vue - vue null");
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG_NAME, "observer vue - probleme sur observer vue, envoi report ", e);
                    FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
                } finally {
                    LogUtils.d(TAG_NAME,   "fin observer vue = <" + codeVue + ">");
                }
            });
        } finally {
            LogUtils.d(TAG_NAME, "fin onCreate");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtils.d(TAG_NAME, "debut onCreateOptionsMenu");
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        LogUtils.d(TAG_NAME, "fin onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        LogUtils.d(TAG_NAME, "debut onOptionsItemSelected");
        int menuMainItemId = item.getItemId();
        if (menuMainItemId == R.id.idMenuVue){
            showVueDialog(); // Ouvre le menu déroulant central
            return true;
        }
        if (menuMainItemId == R.id.idMenuAide){
            showAboutDialog();
            return true;
        }
        return true;
    }

    /**
     * Le dialog pour le choix de la vue
     * Très simple, il ne donne pas lieu a un layout XML
     * @since 1.0.0
     */
    private void showVueDialog() {
        IVueService vueService = FFCalculatorApplication.instance.getServicesManager().getVueService(getResources().getStringArray(R.array.vues_libelles_array),getResources().getStringArray(R.array.vues_ids_array));
        LogUtils.d(TAG_NAME, "affichage dialog choix vue avec choix courant sur <" + vueViewModel.getCurrentCodeVue() + ">");
        int checkedItem = vueService.getIndexFromCodeVue(vueViewModel.getCurrentCodeVue());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.title_dialog_vue))
                .setSingleChoiceItems(vueService.getVuesLibelles(), checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Action sur sélection
                        final String vueLibelleSelected = vueService.getVuesLibelles()[which];
                        final String vueCodeSelected = vueService.getVuesCodes()[which];
                        LogUtils.v(TAG_NAME, " dialog choix vue - selection item <" + which + "> soit <" + vueLibelleSelected + "> (" + vueCodeSelected + ") - envoi job update en background");
                        vueViewModel.updateCodeVueAsync(vueCodeSelected);
                        dialog.dismiss(); // Fermer le dialog après choix
                    }
                })
                .setNegativeButton(R.string.label_annuler, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Le dialog pour le menu "A propos"
     * Affiche juste versionName et versionCode
     * @since 1.0.0
     */
    private void showAboutDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogAboutView = inflater.inflate(R.layout.dialog_about, null);
        TextView textView = dialogAboutView.findViewById(R.id.vueTexte);
        textView.setText(getString(R.string.label_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.title_dialog_about))
                .setView(dialogAboutView)
                .setNegativeButton(R.string.label_fermer, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}