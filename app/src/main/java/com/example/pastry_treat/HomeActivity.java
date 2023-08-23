package com.example.pastry_treat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    private RelativeLayout home_layout, menu_layout, cart_layout, track_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_layout = (RelativeLayout) findViewById(R.id.home_layout);
        menu_layout = (RelativeLayout) findViewById(R.id.menu_layout);
        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        track_layout = (RelativeLayout) findViewById(R.id.track_layout);


        home_layout.setVisibility(View.VISIBLE);
        menu_layout.setVisibility(View.GONE);
        cart_layout.setVisibility(View.GONE);
        track_layout.setVisibility(View.GONE);





        bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

        bottomNavigation.show(1, true); //this is default layout

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_restaurant_menu_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_navigation_24));



        meowNavigation();
    }



    private void meowNavigation(){

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {


                switch(model.getId()){

                    case 1:

                        home_layout.setVisibility(View.VISIBLE);
                        menu_layout.setVisibility(View.GONE);
                        cart_layout.setVisibility(View.GONE);
                        track_layout.setVisibility(View.GONE);

                        break;

                    case 2:

                        home_layout.setVisibility(View.GONE);
                        menu_layout.setVisibility(View.VISIBLE);
                        cart_layout.setVisibility(View.GONE);
                        track_layout.setVisibility(View.GONE);

                        break;


                    case 3:

                        home_layout.setVisibility(View.GONE);
                        menu_layout.setVisibility(View.GONE);
                        cart_layout.setVisibility(View.VISIBLE);
                        track_layout.setVisibility(View.GONE);

                        break;

                    case 4:

                        home_layout.setVisibility(View.GONE);
                        menu_layout.setVisibility(View.GONE);
                        cart_layout.setVisibility(View.GONE);
                        track_layout.setVisibility(View.VISIBLE);

                        break;


                }


                return null;
            }
        });



    }


}