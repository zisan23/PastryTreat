package com.example.pastry_treat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    private RelativeLayout home_layout, menu_layout, cart_layout, track_layout, settings_layout;


    private ImageView home_profile_img, menu_profile_img, cart_profile_img, track_profile_img;

    private ImageView settings_phone_img, settings_email_img, settings_msg_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_layout = (RelativeLayout) findViewById(R.id.home_layout);
        menu_layout = (RelativeLayout) findViewById(R.id.menu_layout);
        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        track_layout = (RelativeLayout) findViewById(R.id.track_layout);
        settings_layout = (RelativeLayout) findViewById(R.id.settings_layout);


        home_layout.setVisibility(View.VISIBLE);
        menu_layout.setVisibility(View.GONE);
        cart_layout.setVisibility(View.GONE);
        track_layout.setVisibility(View.GONE);
        settings_layout.setVisibility(View.GONE);


        home_profile_img = (ImageView) findViewById(R.id.home_profile_img);

        home_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        menu_profile_img = (ImageView) findViewById(R.id.menu_profile_img);

        menu_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


        cart_profile_img = (ImageView) findViewById(R.id.cart_profile_img);
        cart_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        track_profile_img = (ImageView) findViewById(R.id.track_profile_img);
        track_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });





        settings_phone_img = (ImageView) findViewById(R.id.settings_phone_img);
        settings_email_img = (ImageView) findViewById(R.id.settings_email_img);
        settings_msg_img = (ImageView) findViewById(R.id.settings_msg_img);

        settings_phone_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "01793704188";
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
        });

        settings_email_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "ssadman8877@gmail.com";
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Send email using:"));
            }
        });

        settings_msg_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "01793704188";
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
                messageIntent.setData(Uri.parse("smsto:" + number));
                startActivity(messageIntent);
            }
        });



        bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

        bottomNavigation.show(1, true); //this is default layout

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_restaurant_menu_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_navigation_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.baseline_settings_24));



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
                        settings_layout.setVisibility(View.GONE);


                        break;

                    case 2:

                        home_layout.setVisibility(View.GONE);
                        menu_layout.setVisibility(View.VISIBLE);
                        cart_layout.setVisibility(View.GONE);
                        track_layout.setVisibility(View.GONE);
                        settings_layout.setVisibility(View.GONE);

                        break;


                    case 3:

                        home_layout.setVisibility(View.GONE);
                        menu_layout.setVisibility(View.GONE);
                        cart_layout.setVisibility(View.VISIBLE);
                        track_layout.setVisibility(View.GONE);
                        settings_layout.setVisibility(View.GONE);

                        break;

                    case 4:

                        home_layout.setVisibility(View.GONE);
                        menu_layout.setVisibility(View.GONE);
                        cart_layout.setVisibility(View.GONE);
                        track_layout.setVisibility(View.VISIBLE);
                        settings_layout.setVisibility(View.GONE);

                        break;

                    case 5:
                        home_layout.setVisibility(View.GONE);
                        menu_layout.setVisibility(View.GONE);
                        cart_layout.setVisibility(View.GONE);
                        track_layout.setVisibility(View.GONE);
                        settings_layout.setVisibility(View.VISIBLE);


                        break;


                }


                return null;
            }
        });



    }


}