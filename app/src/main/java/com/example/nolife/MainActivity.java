package com.example.nolife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;

import com.example.nolife.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity
    {
    String nickname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nickname = getIntent().getExtras().get("name").toString();
        ActivityMainBinding bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.news, R.id.favourites, R.id.profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bind.bottomNav, navController);
    }
        @Override
        public boolean onSupportNavigateUp() {
            return Navigation.findNavController(this, R.id.nav_host_fragment_main).navigateUp()
                    || super.onSupportNavigateUp();
        }
}