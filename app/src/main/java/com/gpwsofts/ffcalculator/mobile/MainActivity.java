package com.gpwsofts.ffcalculator.mobile;

import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG_NAME, "debut onCreateOptionsMenu");
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.vues_menu, menu);
        Vue currentVue = vueViewModel.getVueLiveData().getValue();
        if (currentVue != null) {
            Log.i(TAG_NAME, "  setting check sur " + currentVue.getName());
            menu.getItem(currentVue.getIndexInComboMenu()).setChecked(true);
        }
        Log.i(TAG_NAME, "fin onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i(TAG_NAME, "  demande de changement de vue dans la menu");
        int itemId = item.getItemId();
        final String newVue = FFCalculatorApplication.instance.getServicesManager().getVueService().getVueFromMenuItem(itemId);
        Log.i(TAG_NAME, "  mise a jour vers la vue " + newVue);
        vueViewModel.updateVue(newVue);
        Log.i(TAG_NAME, "  fin de demande de changement de vue  = <" + newVue + ">");
        return false;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Log.i(TAG_NAME, "  mise a jour de la case cochee dans le menu");
        final Vue currentVue = vueViewModel.getVueLiveData().getValue();
        menu.getItem(currentVue.getIndexInComboMenu()).setChecked(true);
        Log.i(TAG_NAME, "  fin mise a jour de la case cochee dans le menu = <" + currentVue.getName() + ">");
    }
}