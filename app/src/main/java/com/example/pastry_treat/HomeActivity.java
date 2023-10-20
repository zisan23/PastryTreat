package com.example.pastry_treat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pastry_treat.Adapters.HomeRvBtnParentAdapter;
import com.example.pastry_treat.Adapters.HomeRvRestaurentParentAdapter;
import com.example.pastry_treat.Adapters.HomeVpAdapter;
import com.example.pastry_treat.Adapters.OrderedItemAdapter;
import com.example.pastry_treat.Models.HomeRvBtnChildModelClass;
import com.example.pastry_treat.Models.HomeRvBtnParentModelClass;
import com.example.pastry_treat.Models.HomeRvRestaurentChildModel;
import com.example.pastry_treat.Models.HomeRvRestaurentParentModel;
import com.example.pastry_treat.Models.OrderedItemModel;
import com.example.pastry_treat.More.AboutUs;
import com.example.pastry_treat.More.faq;
import com.example.pastry_treat.More.privacy;
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

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class HomeActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;

    private RelativeLayout home_layout, settings_layout,cart_layout;
    private ScrollView home_scrollview, settings_scrollview, cart_scrollview;

    private ViewPager home_vp_advertisements;
    private ArrayList<Integer> vp_arraylist = new ArrayList<>();
    Handler VpHandler;
    private boolean userTouchedViewPager = false;
    final long DELAY_MS = 4000; // Delay between page changes (adjust as needed)


    private ImageView home_profile_img, menu_profile_img, cart_profile_img;

//    private ImageView settings_phone_img, settings_email_img, settings_msg_img;

    private Button aboutus, FAQ, Privacy, TOS, Help;

    private static final int PERMISSION_REQUEST_CODE = 123;


    private RecyclerView recyclerView_btn_parent;
    private ArrayList<HomeRvBtnChildModelClass> homeButtonList;

    private ArrayList<HomeRvBtnParentModelClass> homeRvBtnParentModelClassArrayList;
    private HomeRvBtnParentAdapter homeRvBtnParentAdapter;







    private RecyclerView cart_rv_orderedItems;
    private ArrayList<OrderedItemModel> orderedItemModels = new ArrayList<>();
    private OrderedItemAdapter menuOrderedItemAdapter;


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


    /////////OnCreate() method //////

    @SuppressLint({"NotifyDataSetChanged", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileref = storageReference.child("User/" + auth.getCurrentUser().getUid() + "/Profile.png");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(home_profile_img);
            }
        });

        /////////////

        home_vp_advertisements = (ViewPager) findViewById(R.id.home_vp_advertisements);
        vp_arraylist.add(R.drawable.adv1);
        vp_arraylist.add(R.drawable.adv2);
        vp_arraylist.add(R.drawable.adv1);
        vp_arraylist.add(R.drawable.adv2);
        vp_arraylist.add(R.drawable.adv1);
        vp_arraylist.add(R.drawable.adv2);
        vp_arraylist.add(R.drawable.adv1);
        vp_arraylist.add(R.drawable.adv2);

        HomeVpAdapter homeVpAdapter = new HomeVpAdapter(HomeActivity.this, vp_arraylist);
        home_vp_advertisements.setAdapter(homeVpAdapter);

        // Initialize the handler for automatic sliding
        VpHandler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                int currentItem = home_vp_advertisements.getCurrentItem();
                int totalItems = homeVpAdapter.getCount();

                if (currentItem < totalItems - 1) {
                    home_vp_advertisements.setCurrentItem(currentItem + 1);
                } else {
                    home_vp_advertisements.setCurrentItem(0);
                }

                VpHandler.postDelayed(this, DELAY_MS);
            }
        };

        // Set up automatic scrolling
        VpHandler.postDelayed(runnable, DELAY_MS);

        // Add a touch listener to pause automatic scrolling
        home_vp_advertisements.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userTouchedViewPager = true;
                VpHandler.removeCallbacksAndMessages(null);

                // Resume automatic scrolling after 10 seconds
                VpHandler.postDelayed(runnable, 10000);

                return false;
            }
        });


        try {
            recyclerView_btn_parent = (RecyclerView) findViewById(R.id.home_btn_rv_parent);

            homeButtonList = new ArrayList<>();

            homeRvBtnParentModelClassArrayList = new ArrayList<>();

            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream"));
            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream1"));
            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream2"));
            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream3"));
            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream4"));

            homeRvBtnParentModelClassArrayList.add(new HomeRvBtnParentModelClass(homeButtonList));

            homeRvBtnParentAdapter = new HomeRvBtnParentAdapter(homeRvBtnParentModelClassArrayList, HomeActivity.this);
            recyclerView_btn_parent.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView_btn_parent.setAdapter(homeRvBtnParentAdapter);
            homeRvBtnParentAdapter.notifyDataSetChanged();


        } catch (Exception e) {
            System.out.println("Button List home recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        CardView home_cv_search = (CardView) findViewById(R.id.home_cv_search);
        ;

        home_cv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        try {
            RecyclerView home_restaurent_rv_parent = (RecyclerView) findViewById(R.id.home_restaurent_rv_parent);

            ArrayList<HomeRvRestaurentChildModel> featuredRestaurents = new ArrayList<>();

            featuredRestaurents.add(new HomeRvRestaurentChildModel(R.drawable.adv1, "Meherun's Hotel", "CTG", "Faiak er wife Meherun"));
            featuredRestaurents.add(new HomeRvRestaurentChildModel(R.drawable.adv2, "Meherun's Hotel", "CTG", "Faiak er wife Meherun"));
            featuredRestaurents.add(new HomeRvRestaurentChildModel(R.drawable.adv1, "Meherun's Hotel", "CTG", "Faiak er wife Meherun"));
            featuredRestaurents.add(new HomeRvRestaurentChildModel(R.drawable.adv2, "Meherun's Hotel", "CTG", "Faiak er wife Meherun"));
            featuredRestaurents.add(new HomeRvRestaurentChildModel(R.drawable.adv1, "Meherun's Hotel", "CTG", "Faiak er wife Meherun"));
            featuredRestaurents.add(new HomeRvRestaurentChildModel(R.drawable.adv2, "Meherun's Hotel", "CTG", "Faiak er wife Meherun"));

            ArrayList<HomeRvRestaurentParentModel> homeRvRestaurentParentModelList = new ArrayList<>();

            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Featured Restaurents", featuredRestaurents));

            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(HomeActivity.this, homeRvRestaurentParentModelList);
            home_restaurent_rv_parent.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
            home_restaurent_rv_parent.setAdapter(homeRvRestaurentParentAdapter);
            homeRvRestaurentParentAdapter.notifyDataSetChanged();


        } catch (Exception e) {

            System.out.println("home restaurent recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


        /////////////////


        home_layout = (RelativeLayout) findViewById(R.id.home_layout);
        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        settings_layout = (RelativeLayout) findViewById(R.id.settings_layout);


        home_layout.setVisibility(View.VISIBLE);
        cart_layout.setVisibility(View.GONE);
        settings_layout.setVisibility(View.GONE);

        home_scrollview = (ScrollView) findViewById(R.id.home_scrollview);
        cart_scrollview = (ScrollView) findViewById(R.id.cart_scrollview);
        settings_scrollview = (ScrollView) findViewById(R.id.settings_scrollview);

        home_scrollview.setVisibility(View.VISIBLE);
        cart_scrollview.setVisibility(View.GONE);
        settings_scrollview.setVisibility(View.GONE);



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


        // More Activity

        aboutus = findViewById(R.id.aboutus);
        FAQ = findViewById(R.id.FAQ);
        Privacy = findViewById(R.id.Privacy);
        TOS = findViewById(R.id.TOS);
        Help = findViewById(R.id.Help);

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });
        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, faq.class);
                startActivity(intent);
            }
        });

        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, privacy.class);
                startActivity(intent);
            }
        });
        TOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        try{
            cart_rv_orderedItems = (RecyclerView) findViewById(R.id.cart_rv_order_menu);
            orderedItemModels.add(new OrderedItemModel(R.drawable.cheesecake,"Cheese Cake","ZisanXRiyaHotel",10000,10));
            orderedItemModels.add(new OrderedItemModel(R.drawable.cheesecake,"Cheese Cake","ZisanXRiyaHotel",10000,10));
            orderedItemModels.add(new OrderedItemModel(R.drawable.cheesecake,"Cheese Cake","ZisanXRiyaHotel",10000,10));
            orderedItemModels.add(new OrderedItemModel(R.drawable.cheesecake,"Cheese Cake","ZisanXRiyaHotel",10000,10));
            orderedItemModels.add(new OrderedItemModel(R.drawable.cheesecake,"Cheese Cake","ZisanXRiyaHotel",10000,10));
            orderedItemModels.add(new OrderedItemModel(R.drawable.cheesecake,"Cheese Cake","ZisanXRiyaHotel",10000,10));

            menuOrderedItemAdapter = new OrderedItemAdapter(HomeActivity.this, orderedItemModels);
            cart_rv_orderedItems.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.VERTICAL,false));
            cart_rv_orderedItems.setAdapter(menuOrderedItemAdapter);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }





//        settings_phone_img = (ImageView) findViewById(R.id.settings_phone_img);
//        settings_email_img = (ImageView) findViewById(R.id.settings_email_img);
//        settings_msg_img = (ImageView) findViewById(R.id.settings_msg_img);
//
//
//        settings_phone_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String number = "01793704188";
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:" + number));
//                startActivity(callIntent);
//            }
//        });
//
//        settings_email_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email = "ssadman8877@gmail.com";
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
//                emailIntent.setType("message/rfc822");
//                startActivity(Intent.createChooser(emailIntent, "Send email using:"));
//            }
//        });
//
//        settings_msg_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String number = "01793704188";
//                Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
//                messageIntent.setData(Uri.parse("smsto:" + number));
//                startActivity(messageIntent);
//            }
//        });


        try {


            bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

            bottomNavigation.show(1, false); //this is default layout
            bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_shopping_cart_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_more_horiz_24));


            meowNavigation();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }


    private void meowNavigation() {

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                try {

                    switch (model.getId()) {

                        case 1:

                            home_layout.setVisibility(View.VISIBLE);
                            cart_layout.setVisibility(View.GONE);
                            settings_layout.setVisibility(View.GONE);

                            home_scrollview.setVisibility(View.VISIBLE);
                            cart_scrollview.setVisibility(View.GONE);
                            settings_scrollview.setVisibility(View.GONE);


                            break;

                        case 2:

                            home_layout.setVisibility(View.GONE);
                            cart_layout.setVisibility(View.VISIBLE);
                            settings_layout.setVisibility(View.GONE);

                            home_scrollview.setVisibility(View.GONE);
                            cart_scrollview.setVisibility(View.VISIBLE);
                            settings_scrollview.setVisibility(View.GONE);

                            break;

                        case 3:
                            home_layout.setVisibility(View.GONE);
                            cart_layout.setVisibility(View.GONE);
                            settings_layout.setVisibility(View.VISIBLE);

                            home_scrollview.setVisibility(View.GONE);
                            cart_scrollview.setVisibility(View.GONE);
                            settings_scrollview.setVisibility(View.VISIBLE);

                            break;


                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


                return null;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the handler when the activity is paused
        VpHandler.removeCallbacksAndMessages(null);
    }


}