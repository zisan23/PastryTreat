package com.example.pastry_treat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class HomeActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_restaurant_menu_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_navigation_24));
    }


}