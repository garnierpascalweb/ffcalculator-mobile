package com.gpwsofts.ffcalculator.mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gpwsofts.ffcalculator.mobile.common.log.LogUtils;
import com.gpwsofts.ffcalculator.mobile.databinding.ActivityMainBinding;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_NAME = "MainActivity";
    private ActivityMainBinding binding;
    private VueViewModel vueViewModel;
    private int indexInComboMenuSelected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.i(TAG_NAME, "appel de onCreate");
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vueViewModel = new ViewModelProvider(this).get(VueViewModel.class);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_result, R.id.navigation_season, R.id.navigation_help).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (vueViewModel.getCurrentCodeVue() == null)
            vueViewModel.loadVueAsync();

        vueViewModel.getVueLiveData().observe(this, vue -> {
            try{
                LogUtils.i(TAG_NAME, "debut observer vue");
                if (vue != null) {
                    final String newCodeVue = vue.getCode();
                    if (newCodeVue.equals(vueViewModel.getCurrentCodeVue())){
                        LogUtils.d(TAG_NAME, "vue selectionnee identique en cache");
                    } else {
                        indexInComboMenuSelected = vue.getIndexInComboMenu();
                        LogUtils.d(TAG_NAME, "nouvelle vue selectionnee <" + newCodeVue + "> - indexInComboMenuSelected correspondant <" + indexInComboMenuSelected + "> - appel invalidateOptionsMenu()");
                        vueViewModel.setCurrentCodeVue(newCodeVue);
                        invalidateOptionsMenu();
                        Toast.makeText(this, getString(R.string.toast_update_vue_ok, vue.getName()), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    LogUtils.w(TAG_NAME, "vue null");
                }
            } catch (Exception e) {
                LogUtils.e(TAG_NAME, "observer vue - probleme sur observer vue, envoi report ", e);
                FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
            } finally {
                LogUtils.i(TAG_NAME, "fin observer vue");
            }
        });
        LogUtils.i(TAG_NAME, "fin appel de onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtils.i(TAG_NAME, "debut onCreateOptionsMenu");
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.vues_menu, menu);
        LogUtils.d(TAG_NAME, "onCreateOptionsMenu - cochage indexInComboMenuSelected <" + indexInComboMenuSelected + ">");
        menu.getItem(indexInComboMenuSelected).setChecked(true);
        LogUtils.i(TAG_NAME, "fin onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        LogUtils.i(TAG_NAME, "debut onOptionsItemSelected");
        int itemId = item.getItemId();
        LogUtils.d(TAG_NAME, "onOptionsItemSelected - update asynchrone de la vue selon itenId selectionne = <" + itemId + ">");
        vueViewModel.updateVueFromMenuAsync(itemId);
        LogUtils.i(TAG_NAME, "fin onOptionsItemSelected");
        return true;
    }

    /**
     * showUpdateDialog
     * Methode appelée pour vérifier qu'il y a une nouvelle version disponible
     * @param context
     * @param downloadUrl
     */
    private static void showUpdateDialog(Context context, String downloadUrl) {
        new android.os.Handler(context.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Mise à jour disponible");
            builder.setMessage("Une nouvelle version est disponible. Voulez-vous la télécharger ?");
            builder.setPositiveButton("Télécharger", (dialog, which) -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                context.startActivity(browserIntent);
            });
            builder.setNegativeButton("Plus tard", null);
            builder.show();
        });
    }
}