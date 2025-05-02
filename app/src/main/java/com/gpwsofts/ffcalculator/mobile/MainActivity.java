package com.gpwsofts.ffcalculator.mobile;

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
import androidx.navigation.fragment.NavHostFragment;
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
                vueViewModel.loadVueAsync();

            vueViewModel.getVueLiveData().observe(this, vue -> {
                try {
                    LogUtils.d(TAG_NAME, "debut observer vue = <" + vue + ">");
                    if (vue != null) {
                        final String newCodeVue = vue.getCode();
                        if (newCodeVue.equals(vueViewModel.getCurrentCodeVue())) {
                            LogUtils.v(TAG_NAME, "  observer vue = <" + vue + "> - vue selectionnee identique en cache");
                        } else {
                            indexInComboMenuSelected = vue.getIndexInComboMenu();
                            LogUtils.d(TAG_NAME, "  observer vue = <" + vue + "> nouvelle vue selectionnee <" + newCodeVue + "> - indexInComboMenuSelected correspondant <" + indexInComboMenuSelected + "> - appel invalidateOptionsMenu()");
                            vueViewModel.setCurrentCodeVue(newCodeVue);
                            invalidateOptionsMenu();
                            Toast.makeText(this, getString(R.string.toast_update_vue_ok, vue.getName()), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LogUtils.w(TAG_NAME, "  observer vue = <" + vue + "> - vue null");
                    }
                } catch (Exception e) {
                    LogUtils.e(TAG_NAME, "observer vue - probleme sur observer vue, envoi report ", e);
                    FFCalculatorApplication.instance.getServicesManager().getAsyncReportService().sendReportAsync(TAG_NAME, e);
                } finally {
                    LogUtils.d(TAG_NAME,   "fin observer vue = <" + vue + ">");
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
        menuInflater.inflate(R.menu.vues_menu, menu);
        LogUtils.d(TAG_NAME, "onCreateOptionsMenu - cochage indexInComboMenuSelected <" + indexInComboMenuSelected + ">");
        menu.getItem(indexInComboMenuSelected).setChecked(true);
        LogUtils.d(TAG_NAME, "fin onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        LogUtils.d(TAG_NAME, "debut onOptionsItemSelected");
        int itemId = item.getItemId();
        LogUtils.d(TAG_NAME, "onOptionsItemSelected - update asynchrone de la vue selon itenId selectionne = <" + itemId + ">");
        vueViewModel.updateVueFromMenuAsync(itemId);
        LogUtils.d(TAG_NAME, "fin onOptionsItemSelected");
        return true;
    }
}