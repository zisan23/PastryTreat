package com.example.pastry_treat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pastry_treat.Adapters.FoodAdapter;

import com.example.pastry_treat.Models.FoodModel;

import com.example.pastry_treat.Models.RestaurentModel;
import com.example.pastry_treat.Models.RestaurentRvChildModel;
import com.example.pastry_treat.Models.RestaurentRvParentModel;
import com.example.pastry_treat.Models.WishListModel;
import com.example.pastry_treat.databinding.ActivityRestaurentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurentActivity extends AppCompatActivity {

    ActivityRestaurentBinding binding;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    DocumentReference restaurantRef;
    String restaurantId;
    FirebaseUser user;
    String userId;

    private Boolean liked = false;
    private String profileImageUri;

    private RecyclerView recyclerView;


    private String ownerId;

    ImageView wishListHeart;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent);

        binding = ActivityRestaurentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbar
        if (actionBar != null) {
            actionBar.hide();
        }

        ownerId = getIntent().getStringExtra("ownerId");

        // Get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Get the user's unique ID
        assert user != null;
        userId = user.getUid();

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.rest_rv);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        wishListHeart = (ImageView) findViewById(R.id.addToWishList);



        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        checkWishListPekka(ownerId,userId);




        if (user != null) {
            // Query the Firestore to retrieve the restaurant associated with the user
            firestore.collection("restaurants")
                    .whereEqualTo("ownerId", ownerId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Process the user's restaurant data
                                    RestaurentModel restaurant = document.toObject(RestaurentModel.class);

                                    // Now you have the restaurant details, you can use them as needed
                                    String restaurantName = restaurant.getRestaurentName();
                                    String restaurantAddress = restaurant.getLocation();
                                    String restaurantImageURL = restaurant.getProfile_image(); // Get the image URL

                                    restaurantId = document.getId();


                                    restaurantRef = document.getReference();

                                    // Display restaurant information in your UI
                                    binding.restName.setText(restaurantName);
                                    binding.restAddress.setText(restaurantAddress);
                                    profileImageUri = restaurantImageURL;

                                    // Load the restaurant image using Picasso with error handling
                                    Picasso.get()
                                            .load(restaurantImageURL)
                                            .into(binding.profileImage, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    // Image loaded successfully
//                                                    Toast.makeText(RestaurentActivity.this, "Image Loaded Successfully", Toast.LENGTH_LONG).show();
                                                }

                                                @Override
                                                public void onError(Exception e) {
                                                    // Handle image loading error
                                                    // You can set a placeholder image or show an error message
                                                    binding.profileImage.setImageResource(R.drawable.adv1); // Set a placeholder image
                                                    Toast.makeText(RestaurentActivity.this, "Error loading image", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                            } else {
                                // Handle errors if necessary
                                Toast.makeText(RestaurentActivity.this, "Error fetching restaurant data", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(RestaurentActivity.this, "User not authenticated", Toast.LENGTH_LONG).show();
        }

        ShowFoodItemsRecyclerView();


        binding.addToWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(liked){
                    Toast.makeText(getApplicationContext(),"Already added to wishlist", Toast.LENGTH_LONG).show();
                    removeFromWishList(ownerId, userId);

                }else{
                    WishListModel wishList = new WishListModel();

                    if(user != null && profileImageUri!= null){
                        wishList.userId = userId;
                        wishList.ownerId = ownerId;
                        wishList.restaurantName = binding.restName.getText().toString();
                        wishList.restAddress = binding.restAddress.getText().toString();
                        wishList.liked = true;
                        wishList.imageUri = profileImageUri;

                        addToWishListFireStorePekka(wishList);
                    }else{
                        Toast.makeText(RestaurentActivity.this, "something wrong in wishList...not loaded", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



    }

    private void checkWishListPekka(String ownerId, String userId) {
        if (user != null) {
            // Query the Firestore to retrieve the restaurant associated with the user
            firestore.collection("wishlist")
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("ownerId", ownerId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            boolean restaurantExistsInWishlist = false;

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // If a document is found, the restaurant exists in the user's wishlist
                                    restaurantExistsInWishlist = true;
                                    break;
                                }
                            } else {
                                // Handle errors if necessary
                                Toast.makeText(RestaurentActivity.this, "Error fetching wishlist data", Toast.LENGTH_SHORT).show();
                            }

                            // Here, you can update your UI or perform other actions based on the flag.
                            if (restaurantExistsInWishlist) {
                                // Restaurant exists in the wishlist
                                wishListHeart.setImageResource(R.drawable.baseline_heart_24_red);
                                liked = true;
                            } else {
                                // Restaurant does not exist in the wishlist
                                wishListHeart.setImageResource(R.drawable.baseline_heart_24);
                                liked = false;
                            }
                        }
                    });
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(RestaurentActivity.this, "User not authenticated", Toast.LENGTH_LONG).show();
        }
    }



    private void ShowFoodItemsRecyclerView(){
        try {

            ArrayList<FoodModel> foodModels = new ArrayList<>();

            CollectionReference restaurantsCollection = firestore.collection("foodItems");

            // Query to retrieve all documents in the collection
            restaurantsCollection
                    .whereEqualTo("ownerId",ownerId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                String foodId = documentSnapshot.getString("foodId");
                                String description = documentSnapshot.getString("description");
                                String imageUri = documentSnapshot.getString("imageUri");
                                String name = documentSnapshot.getString("name");
                                Double price = documentSnapshot.getDouble("price");
                                String rest_name = documentSnapshot.getString("restaurantName");

                                //Toast.makeText(HomeActivity.this, restaurantName + " " + profileImage, Toast.LENGTH_SHORT).show();

                                FoodModel food = new FoodModel();

                                food.setFoodId(foodId);
                                food.setName(name);
                                food.setDescription(description);
                                food.setOwnerId(ownerId);
                                food.setImageUri(imageUri);
                                food.setPrice(price);
                                food.setRestaurantName(rest_name);

                                foodModels.add(food);

                            }
                            //Log.d("Firestore", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                            //Toast.makeText(HomeActivity.this, queryDocumentSnapshots.toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(HomeActivity.this, "Restaurants Loaded", Toast.LENGTH_SHORT).show();

//                            if(foodModels.isEmpty()){
//                                Toast.makeText(RestaurentActivity.this, "food-models empty", Toast.LENGTH_LONG).show();
//                            }


//                            ArrayList<HomeRvRestaurentParentModel> homeRvRestaurentParentModelList = new ArrayList<>();
//
//                            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Featured Restaurents", featuredRestaurents));
//
//                            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(HomeActivity.this, homeRvRestaurentParentModelList);
//                            home_restaurent_rv_parent.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
//                            home_restaurent_rv_parent.setAdapter(homeRvRestaurentParentAdapter);
//                            homeRvRestaurentParentAdapter.notifyDataSetChanged();

                            FoodAdapter foodAdapter = new FoodAdapter(RestaurentActivity.this,foodModels);

                            recyclerView.setLayoutManager(new LinearLayoutManager(RestaurentActivity.this, LinearLayoutManager.HORIZONTAL,false));
                            recyclerView.setAdapter(foodAdapter);
                            foodAdapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RestaurentActivity.this, "foods not loaded", Toast.LENGTH_LONG).show();
                        }
                    });




        } catch (Exception e) {

            System.out.println("home food items recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void addToWishListFireStorePekka(WishListModel wishList) {

        // Save data to Firestore
        firestore.collection("wishlist")
                .add(wishList)
                .addOnSuccessListener(new OnSuccessListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Object documentReference) {
                        //Toast.makeText(getApplicationContext(), "WishList has been added", Toast.LENGTH_LONG).show();
                        liked = true;
                        wishListHeart.setImageResource(R.drawable.baseline_heart_24_red);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error!!! wishlist could not be added", Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void removeFromWishList(String ownerId, String userId) {
        // Query the Firestore to find and remove the restaurant from the wishlist
        firestore.collection("wishlist")
                .whereEqualTo("userId", userId)
                .whereEqualTo("ownerId", ownerId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Delete the document to remove the restaurant from the wishlist
                                document.getReference().delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Successfully removed from the wishlist
                                                Toast.makeText(RestaurentActivity.this, "Removed from wishlist", Toast.LENGTH_SHORT).show();
                                                liked = false;
                                                wishListHeart.setImageResource(R.drawable.baseline_heart_24);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle removal failure
                                                Toast.makeText(RestaurentActivity.this, "Error removing from wishlist", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            // Handle errors if necessary
                            Toast.makeText(RestaurentActivity.this, "Error fetching wishlist data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}