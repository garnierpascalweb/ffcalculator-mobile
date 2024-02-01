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
import com.gpwsofts.ffcalculator.mobile.services.vues.IVueService;
import com.gpwsofts.ffcalculator.mobile.ui.shared.SharedPrefsViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_NAME = "MainActivity";
    private SharedPrefsViewModel sharedPrefsViewModel;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefsViewModel = new ViewModelProvider(this).get(SharedPrefsViewModel.class);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        boolean boolReturn = false;
        if (itemId == R.id.idMenuItemElite){
            Log.i(TAG_NAME, "switch en vue Elite");
            sharedPrefsViewModel.update(IVueService.ELITE);
            boolReturn = true;
        } else if (itemId == R.id.idMenuItemOpen1){
            Log.i(TAG_NAME, "switch en vue Open1");
            sharedPrefsViewModel.update(IVueService.OPEN_1);
            boolReturn = true;
        }else if (itemId == R.id.idMenuItemOpen2){
            Log.i(TAG_NAME, "switch en vue Open2");
            sharedPrefsViewModel.update(IVueService.OPEN_2);
            boolReturn = true;
        }else if (itemId == R.id.idMenuItemOpen3){
            Log.i(TAG_NAME, "switch en vue Open3");
            sharedPrefsViewModel.update(IVueService.OPEN_3);
            boolReturn = true;
        }else if (itemId == R.id.idMenuItemAccess){
            Log.i(TAG_NAME, "switch en vue Access");
            sharedPrefsViewModel.update(IVueService.ACCESS);
            boolReturn = true;
        }else if (itemId == R.id.idMenuItemU19){
            Log.i(TAG_NAME, "switch en vue U19");
            sharedPrefsViewModel.update(IVueService.U19);
            boolReturn = true;
        }else if (itemId == R.id.idMenuItemU17){
            Log.i(TAG_NAME, "switch en vue U17");
            sharedPrefsViewModel.update(IVueService.U17);
            boolReturn = true;
        } else {
            Log.w(TAG_NAME, "selection dune vue non geree, vue generale");
            sharedPrefsViewModel.update(IVueService.GENERALE);
            boolReturn = super.onOptionsItemSelected(item);
        }
        return boolReturn;


    }

    /**
     * Methode permettant de switcher vers la vue donnée
     * @since 1.0.0
     * @param vue
     */
    private void switchVue(String vue){
        Log.i(TAG_NAME, "switchons en vue " + vue);
    }
}