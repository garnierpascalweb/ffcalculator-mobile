package com.gpwsofts.ffcalculator.mobile;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gpwsofts.ffcalculator.mobile.databinding.ActivityMainBinding;
import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;
import com.gpwsofts.ffcalculator.mobile.utils.LogUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_NAME = "MainActivity";
    private ActivityMainBinding binding;

    private VueViewModel vueViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vueViewModel = new ViewModelProvider(this).get(VueViewModel.class);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_result, R.id.navigation_season, R.id.navigation_synthesis).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
        //TODO 1.0.0 uniformiser toutes les logs pour un changement de type observe
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtils.i(TAG_NAME, "debut onCreateOptionsMenu sur menu");
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.vues_menu, menu);
        LogUtils.d(TAG_NAME, "onCreateOptionsMenu sur menu - recuperation de la vue en LiveData");
        //TODO 1.0.0 a ameliorer
        Vue currentVue = vueViewModel.getVueLiveData().getValue();
        if (currentVue != null) {
            LogUtils.d(TAG_NAME, "onCreateOptionsMenu sur menu - selection de l'item correspondant a la vue courante = <" + currentVue.getCode() + ">");
            menu.getItem(currentVue.getIndexInComboMenu()).setChecked(true);
        }
        LogUtils.i(TAG_NAME, "fin onCreateOptionsMenu sur menu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        LogUtils.i(TAG_NAME, "debut onOptionsItemSelected sur menuItem");
        LogUtils.d(TAG_NAME, "onOptionsItemSelected sur menuItem - recuperation de l'index de l'item sélectionné");
        int itemId = item.getItemId();
        final String newCodeVue = FFCalculatorApplication.instance.getServicesManager().getVueService().getCodeVueFromMenuItem(itemId);
        LogUtils.d(TAG_NAME, "onOptionsItemSelected sur menuItem - selection de la vue <" + newCodeVue + "> - envoi du job asynchrone de mise a jour de la vue");
        vueViewModel.updateVue(newCodeVue);
        // TODO 1.0.0 pas forcement, updateView etant asynchrone, peut etre que la vue a pas été mise a jour
        item.setChecked(true);
        LogUtils.i(TAG_NAME, "fin onOptionsItemSelected sur menuItem");
        return false;
    }
}