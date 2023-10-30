package com.example.appadmin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appadmin.Adapters.FoodAdapter;
import com.example.appadmin.Adapters.RestaurentRvFoodAdapter;
import com.example.appadmin.Models.FoodModel;
import com.example.appadmin.Models.RestaurentModel;
import com.example.appadmin.Models.RestaurentRvFoodModel;
import com.example.appadmin.databinding.ActivityRestaurentBinding;
import com.google.android.gms.common.util.ScopeUtil;
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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.Manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RestaurentActivity extends AppCompatActivity {

    ActivityRestaurentBinding binding;

    private Button signout;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    DocumentReference restaurantRef;
    private Uri selectedImageUri;
    String restaurantId;
    String restName;
    FirebaseUser user;

    private RecyclerView recyclerView;
    private FoodAdapter adapter; // Custom adapter for food items
    private List<FoodModel> foodItems = new ArrayList<>(); // List to store food items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.rest_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter = new FoodAdapter(foodItems);
        recyclerView.setAdapter(adapter);





        // Fetch food items from Firestore
        fetchFoodItems();



        if (user != null) {
            // Get the user's unique ID
            String userId = user.getUid();

            // Query the Firestore to retrieve the restaurant associated with the user
            firestore.collection("restaurants")
                    .whereEqualTo("ownerId", userId)
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
                                    restName = restaurantName;
                                    String restaurantAddress = restaurant.getLocation();
                                    String restaurantImageURL = restaurant.getProfile_image(); // Get the image URL

                                    restaurantId = document.getId();


                                    restaurantRef = document.getReference();

                                    // Display restaurant information in your UI
                                    binding.restName.setText(restaurantName);
                                    binding.restAddress.setText(restaurantAddress);

                                    // Load the restaurant image using Picasso with error handling
                                    Picasso.get()
                                            .load(restaurantImageURL)
                                            .into(binding.profileImage, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                    // Image loaded successfully
                                                    Toast.makeText(RestaurentActivity.this, "Welcome Chef", Toast.LENGTH_LONG).show();
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



        binding.addItemBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RestaurentActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_food_item, null);

                // Initialize dialog views (EditText fields and ImageView)
                EditText itemNameEditText = dialogView.findViewById(R.id.itemNameEditText);
                EditText itemDescriptionEditText = dialogView.findViewById(R.id.itemDescriptionEditText);
                EditText itemPriceEditText = dialogView.findViewById(R.id.itemPriceEditText);
                Button selectImageBtn = dialogView.findViewById(R.id.selectImageBtn);

                // Dexter permission request to access the gallery
                Dexter.withContext(RestaurentActivity.this)
                        .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                // Permission granted, open the gallery to choose an image
                                selectImageBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, 123); // Use a unique request code
                                    }
                                });
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(RestaurentActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        })
                        .check();

                builder.setView(dialogView)
                        .setTitle("Add Food Item")
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Get the food item details from the dialog
                                String itemName = itemNameEditText.getText().toString();
                                String itemDescription = itemDescriptionEditText.getText().toString();
                                double itemPrice = Double.parseDouble(itemPriceEditText.getText().toString());
                                String foodId = UUID.randomUUID().toString();

                                if (selectedImageUri != null) {

                                    // Call a method to add the food item to Firestore
                                    //addFoodItemToFirestore(itemName, itemDescription, itemPrice, selectedImageUri);

                                    final StorageReference storageReference = firebaseStorage.getReference().
                                            child("foodItemImages")
                                            .child(foodId);

                                    addFoodItemToFireStorePekka(storageReference,foodId,
                                            itemName,itemDescription,itemPrice,selectedImageUri);



                                } else {
                                    Toast.makeText(RestaurentActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                                }

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });


        binding.seeOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurentActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });


        signout = findViewById(R.id.signout);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });




    }





    // Create a method to fetch food items from Firestore
    private void fetchFoodItems() {
        // Use FirebaseFirestore to query the food items collection from Firestore

        firestore.collection("foodItems")
                .whereEqualTo("ownerId", user.getUid()) // Filter by owner ID (you may need to adapt this)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            foodItems.clear(); // Clear existing items
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Convert Firestore document to a FoodItemModel
                                FoodModel foodItem = document.toObject(FoodModel.class);
                                foodItems.add(foodItem); // Add the item to the list
                            }
                            adapter.notifyDataSetChanged(); // Notify the adapter that data has changed
                        } else {
                            // Handle errors if necessary
                            Toast.makeText(RestaurentActivity.this, "Error fetching food items", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }









    private void addFoodItemToFireStorePekka(StorageReference storageReference,String foodId, String itemName, String itemDescription, double itemPrice, Uri selectedImageUri) {
        storageReference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        FoodModel food = new FoodModel();
                        food.name = itemName;
                        food.ownerId = user.getUid();
                        food.description = itemDescription;
                        food.price = itemPrice;
                        food.foodId = foodId;
                        food.imageUri = String.valueOf(uri);
                        food.setRestaurantName(restName);


                        // Save data to Firestore
                        firestore.collection("foodItems")
                                .add(food)
                                .addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object documentReference) {
                                        Toast.makeText(RestaurentActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                                        fetchFoodItems();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RestaurentActivity.this, "Upload Error!!!!", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK) {
            // Retrieve the selected image's URI
            if (data != null) {
                selectedImageUri = data.getData();

                if (selectedImageUri == null) {
                    Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
    }


}
