package com.example.pastry_treat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
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
import com.example.pastry_treat.Adapters.HomeRvParentAdapter;
import com.example.pastry_treat.Models.HomeRvChildModelClass;
import com.example.pastry_treat.Models.HomeRvParentModelClass;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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

    private RecyclerView recyclerView_parent;
    private ArrayList<HomeRvChildModelClass> top_products;

    private ArrayList<HomeRvChildModelClass> you_may_like_it;
    private ArrayList<HomeRvChildModelClass> popular_products;
    private ArrayList<HomeRvChildModelClass> best_deals;


    private ArrayList<HomeRvParentModelClass> homeRvParentModelClassArrayList;

    private HomeRvParentAdapter homeRvParentAdapter;


    FirebaseAuth auth;
    FirebaseUser user;
    String uid;
    FirebaseFirestore db;
    String userEmail, userName, userPassword;
    DocumentReference docRef;
    StorageReference storageReference;


//    private Button dineInButton;
//    private Button takeoutButton;
//    private Button deliveryButton;

    // scroll view componant by zisan //

/*

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
*/

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileref = storageReference.child("User/"+ auth.getCurrentUser().getUid()+"/Profile.png");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(home_profile_img);
            }
        });








        try {
            recyclerView_parent = (RecyclerView) findViewById(R.id.home_rv_parent);

            top_products = new ArrayList<>();
            you_may_like_it = new ArrayList<>();
            popular_products = new ArrayList<>();
            best_deals = new ArrayList<>();


            homeRvParentModelClassArrayList = new ArrayList<>();


            top_products.add(new HomeRvChildModelClass(R.drawable.chocobiscuit));
            top_products.add(new HomeRvChildModelClass(R.drawable.chococake));
            top_products.add(new HomeRvChildModelClass(R.drawable.waffles));
            top_products.add(new HomeRvChildModelClass(R.drawable.strawcupcake));
            top_products.add(new HomeRvChildModelClass(R.drawable.chococupcake));


            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.moosecake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.whitecake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.jellycake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.cheesecake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.marbleee));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.crunchydelight));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.italianpudding));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.redforrest));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.blackforrest));




            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("Top Products", top_products));
            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("You May Also Like ", you_may_like_it));

            //top_products.clear();
            //you_may_like_it.clear();


            best_deals.add(new HomeRvChildModelClass(R.drawable.strawchocopastry));
            best_deals.add(new HomeRvChildModelClass(R.drawable.fraisiercake));
            best_deals.add(new HomeRvChildModelClass(R.drawable.macaroons));
            best_deals.add(new HomeRvChildModelClass(R.drawable.brownievalentine));
            best_deals.add(new HomeRvChildModelClass(R.drawable.oreos));

            popular_products.add(new HomeRvChildModelClass(R.drawable.dessertkababs));
            popular_products.add(new HomeRvChildModelClass(R.drawable.donuts));
            popular_products.add(new HomeRvChildModelClass(R.drawable.creamdelight));
            popular_products.add(new HomeRvChildModelClass(R.drawable.oreoart));
            popular_products.add(new HomeRvChildModelClass(R.drawable.bubbleoybillcake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.yellowicecake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.cheesecupcake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.theglobecake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.chocoberrycake));

            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("Popular Products",popular_products ));
            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("Best Deals", best_deals));




            homeRvParentAdapter = new HomeRvParentAdapter(homeRvParentModelClassArrayList, HomeActivity.this);
            recyclerView_parent.setLayoutManager(new LinearLayoutManager(this));
            recyclerView_parent.setAdapter(homeRvParentAdapter);
            homeRvParentAdapter.notifyDataSetChanged();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



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


        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbar
        if (actionBar != null) {
            actionBar.hide();
        }


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




        home_profile_img = (ImageView) findViewById(R.id.home_profile_img);



        home_profile_img.setOnClickListener(new View.OnClickListener() {
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


       // track_location_img = (ImageView) findViewById(R.id.track_location_img);
/*
        track_location_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String locationUrl = "Chillox Dhanmondi";
//                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + locationUrl);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
/*
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
*/

        try {


            bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

            bottomNavigation.show(1, false); //this is default layout

            bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_restaurant_menu_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_shopping_cart_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_navigation_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.baseline_settings_24));


            meowNavigation();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }


    private void meowNavigation(){

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                try {

                    switch (model.getId()) {

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
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }


                return null;
            }
        });

    }


}