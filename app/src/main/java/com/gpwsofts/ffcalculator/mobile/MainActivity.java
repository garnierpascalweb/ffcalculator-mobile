package com.gpwsofts.ffcalculator.mobile;

import android.os.Bundle;
import android.util.Log;
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
import com.gpwsofts.ffcalculator.mobile.databinding.ActivityMainBinding;
import com.gpwsofts.ffcalculator.mobile.model.Vue;
import com.gpwsofts.ffcalculator.mobile.ui.view.VueViewModel;

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
        /*
        vueViewModel.getVueLiveData().observe(getViewLifecycleOwner(), vue -> {
            //TODO 1.0.0 uniformiser toutes les logs pour un changement de type observe
            Log.i(TAG_NAME, "changement vue vers " + vue);
            if (vue != null) {
                Toast.makeText(this, getString(R.string.toast_update_vue_ok, vue.getName()), Toast.LENGTH_SHORT).show();
            }
        });
        */
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
        final String newCodeVue = FFCalculatorApplication.instance.getServicesManager().getVueService().getCodeVueFromMenuItem(itemId);
        Log.i(TAG_NAME, "  mise a jour asynchrone vers la vue " + newCodeVue);
        vueViewModel.updateVue(newCodeVue);
        Log.i(TAG_NAME, "  check de item courant ");
        // TODO 1.0.0 pas forcement, updateView etant asynchrone, peut etre que la vue a pas été mise a jour
        item.setChecked(true);
        Log.i(TAG_NAME, "  fin de demande de changement de vue  = <" + newCodeVue + ">");
        return false;
    }
}