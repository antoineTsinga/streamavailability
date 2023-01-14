package com.streamavailability;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.streamavailability.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,  R.id.navigation_account, R.id.navigation_category, R.id.navigation_watchlist)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        if(getActionBar() !=null) getActionBar().hide();

        Activity activity = this;
        navView.setOnItemSelectedListener(item -> {

         //   System.out.println("----------------is Detach------------"+ detailsMovie.isDetached()) ;
            NavController navController1 = Navigation.findNavController(activity, R.id.nav_host_fragment_activity_main);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    navController1.navigate(R.id.navigation_home);
                    System.out.println("home");

                    break;
                case R.id.navigation_category:
                    navController1.navigate(R.id.navigation_category);
                    System.out.println("category");
                    break;
                case R.id.navigation_watchlist:
                    navController1.navigate(R.id.navigation_watchlist);
                    System.out.println("watchlist");
                    break;
                case R.id.navigation_account:
                    navController1.navigate(R.id.navigation_account);
                    break;
            }
            return true;
        });

    }



}