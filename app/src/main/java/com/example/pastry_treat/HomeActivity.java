package com.example.pastry_treat;

import androidx.annotation.NonNull;
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
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

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
import com.example.pastry_treat.Adapters.WishListAdapter;
import com.example.pastry_treat.Models.HomeRvBtnChildModelClass;
import com.example.pastry_treat.Models.HomeRvBtnParentModelClass;
import com.example.pastry_treat.Models.HomeRvRestaurentChildModel;
import com.example.pastry_treat.Models.HomeRvRestaurentParentModel;
import com.example.pastry_treat.Models.OrderedItemModel;
import com.example.pastry_treat.Models.WishListModel;
import com.example.pastry_treat.More.AboutUs;
import com.example.pastry_treat.More.faq;
import com.example.pastry_treat.More.help;
import com.example.pastry_treat.More.privacy;
import com.example.pastry_treat.More.termsofservice;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class HomeActivity extends AppCompatActivity {

    private MeowBottomNavigation bottomNavigation;
    private TextView home_tv_hiName;

    private RelativeLayout home_layout, settings_layout,cart_layout,wishlist_layout;
    private ScrollView home_scrollview, settings_scrollview, cart_scrollview,  wishlist_scrollview;

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

        home_tv_hiName = findViewById(R.id.home_tv_hiName);
        uid = user.getUid();
        docRef = db.collection("User").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Data for the user exists, you can access it here
                        userName = document.getString("name");

                        // Update UI with the retrieved data
                        home_tv_hiName.setText("Hi! " + userName);
                    } else {
                        // Document does not exist
                        Toast.makeText(HomeActivity.this, "Home Activity User Do Not Exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // An error occurred while fetching the data
                    Toast.makeText(HomeActivity.this, "ERROR WHILE FRETCHING DATA", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ShowRestaurantsRecyclerView();

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
        vp_arraylist.add(R.drawable.adv3);
        vp_arraylist.add(R.drawable.adv4);
        vp_arraylist.add(R.drawable.adv1);
        vp_arraylist.add(R.drawable.adv2);
        vp_arraylist.add(R.drawable.adv3);
        vp_arraylist.add(R.drawable.adv4);

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


    //button recyclerView commented

//        try {
//            recyclerView_btn_parent = (RecyclerView) findViewById(R.id.home_btn_rv_parent);
//
//            homeButtonList = new ArrayList<>();
//
//            homeRvBtnParentModelClassArrayList = new ArrayList<>();
//
//            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream"));
//            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream1"));
//            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream2"));
//            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream3"));
//            homeButtonList.add(new HomeRvBtnChildModelClass(R.drawable.img1, "ice cream4"));
//
//            homeRvBtnParentModelClassArrayList.add(new HomeRvBtnParentModelClass(homeButtonList));
//
//            homeRvBtnParentAdapter = new HomeRvBtnParentAdapter(homeRvBtnParentModelClassArrayList, HomeActivity.this);
//            recyclerView_btn_parent.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
//            recyclerView_btn_parent.setAdapter(homeRvBtnParentAdapter);
//            homeRvBtnParentAdapter.notifyDataSetChanged();
//
//
//        } catch (Exception e) {
//            System.out.println("Button List home recyclerview not working");
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }

        ////////////// Search commented

//        CardView home_cv_search = (CardView) findViewById(R.id.home_cv_search);
        ;

//        home_cv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
//                startActivity(intent);
//            }
//        });

        /////////////////


        home_layout = (RelativeLayout) findViewById(R.id.home_layout);
        cart_layout = (RelativeLayout) findViewById(R.id.cart_layout);
        settings_layout = (RelativeLayout) findViewById(R.id.settings_layout);
        wishlist_layout = (RelativeLayout) findViewById(R.id.wishlist_layout);


        home_layout.setVisibility(View.VISIBLE);
        cart_layout.setVisibility(View.GONE);
        settings_layout.setVisibility(View.GONE);
        wishlist_layout.setVisibility(View.GONE);

        home_scrollview = (ScrollView) findViewById(R.id.home_scrollview);
        cart_scrollview = (ScrollView) findViewById(R.id.cart_scrollview);
        settings_scrollview = (ScrollView) findViewById(R.id.settings_scrollview);
        wishlist_scrollview = (ScrollView) findViewById(R.id.wishlist_scrollview);

        home_scrollview.setVisibility(View.VISIBLE);
        cart_scrollview.setVisibility(View.GONE);
        settings_scrollview.setVisibility(View.GONE);
        wishlist_scrollview.setVisibility(View.GONE);



        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbar
        if (actionBar != null) {
            actionBar.hide();
        }



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
                Intent intent = new Intent(HomeActivity.this, termsofservice.class);
                startActivity(intent);
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, help.class);
                startActivity(intent);
            }
        });


        CardView searchCV = (CardView) findViewById(R.id.home_cv_search);
        TextView searchTv = (TextView) findViewById(R.id.home_et_search);

        searchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });



       ShowOrdersRecyclerView();
       ShowWishListRecyclerView();


        try {


            bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomNavigation);

            bottomNavigation.show(1, false); //this is default layout
            bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.baseline_home_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.baseline_shopping_cart_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_heart_24));
            bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.baseline_more_horiz_24));



            meowNavigation();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }







    private void ShowOrdersRecyclerView(){
        try {
            RecyclerView cart_rv_orderedItems = (RecyclerView) findViewById(R.id.cart_rv_order_menu);
            ArrayList<OrderedItemModel> orderedItemModels = new ArrayList<>();



            // Get a reference to the Firestore collection
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            CollectionReference restaurantsCollection = firestore.collection("orders");

            // Query to retrieve all documents in the collection
            restaurantsCollection
                    .whereEqualTo("buyerId",user.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                String buyerId = documentSnapshot.getString("buyerId");
                                String ownerId = documentSnapshot.getString("ownerId");
                                String imageUri = documentSnapshot.getString("imageUri");
                                String restaurantName = documentSnapshot.getString("restaurentName");
                                String foodId = documentSnapshot.getString("foodId");
                                String foodName = documentSnapshot.getString("foodName");
                                Long quantityLong = documentSnapshot.getLong("quantity");
                                int quantity = 0;
                                if (quantityLong != null) {
                                    quantity = quantityLong.intValue();

                                } else {
                                    Toast.makeText(HomeActivity.this, "QUANTITY PROBLEM", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Double totalPrice = documentSnapshot.getDouble("totalPrice");
                                String orderId = documentSnapshot.getString("orderId");



                                OrderedItemModel orderedItem = new OrderedItemModel();

                                orderedItem.setBuyerId(buyerId);
                                orderedItem.setOwnerId(ownerId);
                                orderedItem.setImageUri(imageUri);
                                orderedItem.setRestaurentName(restaurantName);
                                orderedItem.setFoodId(foodId);
                                orderedItem.setFoodName(foodName);
                                orderedItem.setQuantity(quantity);
                                orderedItem.setTotalPrice(totalPrice);
                                orderedItem.setOrderId(orderId);

                                orderedItemModels.add(orderedItem);

                            }
                            //Log.d("Firestore", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                            //Toast.makeText(HomeActivity.this, queryDocumentSnapshots.toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(HomeActivity.this, "Restaurants Loaded", Toast.LENGTH_SHORT).show();

//                            if(orderedItemModels.isEmpty()){
//                                Toast.makeText(HomeActivity.this, "no orders == empty", Toast.LENGTH_LONG).show();
//                            }

//
//                            ArrayList<HomeRvRestaurentParentModel> homeRvRestaurentParentModelList = new ArrayList<>();
//
//                            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Featured Restaurents", featuredRestaurents));
//
//                            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(HomeActivity.this, homeRvRestaurentParentModelList);
//                            home_restaurent_rv_parent.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
//                            home_restaurent_rv_parent.setAdapter(homeRvRestaurentParentAdapter);
//                            homeRvRestaurentParentAdapter.notifyDataSetChanged();

                            OrderedItemAdapter orderedItemAdapter = new OrderedItemAdapter(HomeActivity.this,
                                    orderedItemModels);
                            cart_rv_orderedItems.setLayoutManager(new LinearLayoutManager(HomeActivity.this
                            ,LinearLayoutManager.VERTICAL,false));
                            cart_rv_orderedItems.setAdapter(orderedItemAdapter);
                            orderedItemAdapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeActivity.this, "orders not loaded", Toast.LENGTH_LONG).show();
                        }
                    });

        } catch (Exception e) {

            System.out.println("home cart orders recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    private void ShowWishListRecyclerView(){
        try {
            RecyclerView wishlist_rv = (RecyclerView) findViewById(R.id.wishlist_rv);
            ArrayList<WishListModel> wishListModels = new ArrayList<>();



            // Get a reference to the Firestore collection
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            CollectionReference restaurantsCollection = firestore.collection("wishlist");

            // Query to retrieve all documents in the collection
            restaurantsCollection
                    .whereEqualTo("userId",user.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                WishListModel wishList = documentSnapshot.toObject(WishListModel.class);

                                wishListModels.add(wishList);

                            }


                            WishListAdapter wishListAdapter = new WishListAdapter(wishListModels,HomeActivity.this);
                            wishlist_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                    LinearLayoutManager.VERTICAL,true));
                            wishlist_rv.setAdapter(wishListAdapter);
                            wishListAdapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(HomeActivity.this, "wishlists not loaded", Toast.LENGTH_LONG).show();
                        }
                    });

        } catch (Exception e) {

            System.out.println("home wishlist recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    private void ShowRestaurantsRecyclerView(){
        try {
            RecyclerView home_restaurent_rv_parent = (RecyclerView) findViewById(R.id.home_restaurent_rv_parent);

            ArrayList<HomeRvRestaurentChildModel> featuredRestaurents = new ArrayList<>();

            // Get a reference to the Firestore collection
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            CollectionReference restaurantsCollection = firestore.collection("restaurants");

            // Query to retrieve all documents in the collection
            restaurantsCollection.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                String location = documentSnapshot.getString("location");
                                String ownerId = documentSnapshot.getString("ownerId");
                                String profileImage = documentSnapshot.getString("profile_image");
                                String restaurantName = documentSnapshot.getString("restaurentName");

                                //Toast.makeText(HomeActivity.this, restaurantName + " " + profileImage, Toast.LENGTH_SHORT).show();

                                HomeRvRestaurentChildModel restaurent = new HomeRvRestaurentChildModel();

                                restaurent.setName(restaurantName);
                                restaurent.setAddress(location);
                                restaurent.setImage(profileImage);
                                restaurent.setShortDescription("description needs to be added");
                                restaurent.setOwnerId(ownerId);

                                featuredRestaurents.add(restaurent);

                            }
                            //Log.d("Firestore", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                            //Toast.makeText(HomeActivity.this, queryDocumentSnapshots.toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(HomeActivity.this, "Restaurants Loaded", Toast.LENGTH_SHORT).show();

                            if(featuredRestaurents.isEmpty()){
                                Toast.makeText(HomeActivity.this, "featurerestaurants empty", Toast.LENGTH_LONG).show();
                            }


                            ArrayList<HomeRvRestaurentParentModel> homeRvRestaurentParentModelList = new ArrayList<>();

                            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Featured Restaurents", featuredRestaurents));

                            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(HomeActivity.this, homeRvRestaurentParentModelList);
                            home_restaurent_rv_parent.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                            home_restaurent_rv_parent.setAdapter(homeRvRestaurentParentAdapter);
                            homeRvRestaurentParentAdapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(HomeActivity.this, "restaurants not loaded", Toast.LENGTH_LONG).show();
                        }
                    });




        } catch (Exception e) {

            System.out.println("home restaurant recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
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
                            wishlist_layout.setVisibility(View.GONE);

                            home_scrollview.setVisibility(View.VISIBLE);
                            cart_scrollview.setVisibility(View.GONE);
                            settings_scrollview.setVisibility(View.GONE);
                            wishlist_scrollview.setVisibility(View.GONE);


                            break;

                        case 2:

                            home_layout.setVisibility(View.GONE);
                            cart_layout.setVisibility(View.VISIBLE);
                            settings_layout.setVisibility(View.GONE);
                            wishlist_layout.setVisibility(View.GONE);

                            home_scrollview.setVisibility(View.GONE);
                            cart_scrollview.setVisibility(View.VISIBLE);
                            settings_scrollview.setVisibility(View.GONE);
                            wishlist_scrollview.setVisibility(View.GONE);

                            ShowOrdersRecyclerView();

                            break;

                        case 3:
                            home_layout.setVisibility(View.GONE);
                            cart_layout.setVisibility(View.GONE);
                            settings_layout.setVisibility(View.GONE);
                            wishlist_layout.setVisibility(View.VISIBLE);

                            home_scrollview.setVisibility(View.GONE);
                            cart_scrollview.setVisibility(View.GONE);
                            settings_scrollview.setVisibility(View.GONE);
                            wishlist_scrollview.setVisibility(View.VISIBLE);

                            break;

                        case 4:

                            home_layout.setVisibility(View.GONE);
                            cart_layout.setVisibility(View.GONE);
                            settings_layout.setVisibility(View.VISIBLE);
                            wishlist_layout.setVisibility(View.GONE);

                            home_scrollview.setVisibility(View.GONE);
                            cart_scrollview.setVisibility(View.GONE);
                            settings_scrollview.setVisibility(View.VISIBLE);
                            wishlist_scrollview.setVisibility(View.GONE);

                            ShowOrdersRecyclerView();

                            break;


                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


                return null;
            }
        });

    }

    private Handler handler = new Handler();
    private int delay = 20000; // 20 seconds in milliseconds

    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            // Call your ShowOrdersRecyclerView() function here or put the RecyclerView update logic directly
            ShowRestaurantsRecyclerView();
            //ShowWishListRecyclerView();
            //ShowOrdersRecyclerView();
            handler.postDelayed(this, delay); // Schedule the Runnable again
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        // Start the recurring update when the activity is resumed
        handler.postDelayed(updateRunnable, delay);
    }




    @Override
    protected void onPause() {
        super.onPause();
        // Stop the handler when the activity is paused
        VpHandler.removeCallbacksAndMessages(null);

    }


}