package com.gpwsofts.ffcalculator.mobile;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gpwsofts.ffcalculator.mobile.databinding.ActivityMainBinding;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;
import com.gpwsofts.ffcalculator.mobile.model.Vue;

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
        if (currentVue != null){
            Log.i(TAG_NAME, "  setting check sur " + currentVue.getName());
            menu.getItem(currentVue.getIndexInComboMenu()).setChecked(true);
        }
        Log.i(TAG_NAME, "fin onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        boolean boolReturn = false;
        if (itemId == R.id.idMenuItemElite) {
            Log.i(TAG_NAME, "switch en vue Elite");
            vueViewModel.updateVue(Vue.ELITE);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemOpen1) {
            Log.i(TAG_NAME, "switch en vue Open1");
            vueViewModel.updateVue(Vue.OPEN_1);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemOpen2) {
            Log.i(TAG_NAME, "switch en vue Open2");
            vueViewModel.updateVue(Vue.OPEN_2);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemOpen3) {
            Log.i(TAG_NAME, "switch en vue Open3");
            vueViewModel.updateVue(Vue.OPEN_3);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemAccess) {
            Log.i(TAG_NAME, "switch en vue Access");
            vueViewModel.updateVue(Vue.ACCESS);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemU23) {
            Log.i(TAG_NAME, "switch en vue U23");
            vueViewModel.updateVue(Vue.U23);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemU19) {
            Log.i(TAG_NAME, "switch en vue U19");
            vueViewModel.updateVue(Vue.U19);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemU17) {
            Log.i(TAG_NAME, "switch en vue U17");
            vueViewModel.updateVue(Vue.U17);
            boolReturn = true;
        } else {
            Log.w(TAG_NAME, "selection dune vue non geree, vue generale");
            vueViewModel.updateVue(Vue.GENERALE);
            boolReturn = super.onOptionsItemSelected(item);
        }
        return boolReturn;
    }

    /**
     * Methode permettant de switcher vers la vue donnée
     *
     * @param vue
     * @since 1.0.0
     */
    private void switchVue(String vue) {
        Log.i(TAG_NAME, "switchons en vue " + vue);
    }
}