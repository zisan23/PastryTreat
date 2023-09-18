package com.example.pastry_treat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.ULocale;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class HomeActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    private RelativeLayout home_layout, menu_layout, cart_layout, track_layout, settings_layout;


    private ImageView home_profile_img, menu_profile_img, cart_profile_img, track_profile_img;

    private ImageView settings_phone_img, settings_email_img, settings_msg_img;

    private ImageView track_location_img;

    private static final int PERMISSION_REQUEST_CODE = 123;





//    private Button dineInButton;
//    private Button takeoutButton;
//    private Button deliveryButton;

    // scroll view componant by zisan //



    private void openCurrentLocationInMap() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    } else {
                        // Google Maps app is not installed, handle this case
                        Toast.makeText(HomeActivity.this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


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


//        dineInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // start Dine-in activity
//                Intent intent = new Intent(HomeActivity.this, DineInActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        takeoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // start Takeout activity
//                Intent intent = new Intent(HomeActivity.this, TakeoutActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        deliveryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // start Delivery activity
//                Intent intent = new Intent(HomeActivity.this, DeliveryActivity.class);
//                startActivity(intent);
//            }
//        });
        // scroll view


        //end of scroll view


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


        track_location_img = (ImageView) findViewById(R.id.track_location_img);

        track_location_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String locationUrl = "Chillox Dhanmondi";
//                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + locationUrl);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
                        openCurrentLocationInMap();
                    }
                    else{
                        openCurrentLocationInMap();
                    }
                }

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