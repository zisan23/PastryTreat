package com.example.appadmin;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class RestaurentActivity extends AppCompatActivity {

    ActivityRestaurentBinding binding;
    FirebaseFirestore firestore;
    DocumentReference restaurantRef;
    private Uri selectedImageUri;



    List<RestaurentRvFoodModel> foodModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        firestore = FirebaseFirestore.getInstance();

        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
                                    String restaurantAddress = restaurant.getLocation();
                                    String restaurantImageURL = restaurant.getProfile_image(); // Get the image URL

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
                                                    Toast.makeText(RestaurentActivity.this,"Image Loaded Successfully",Toast.LENGTH_LONG).show();
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
            Toast.makeText(RestaurentActivity.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }




        ///////////////

        binding.addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFoodItemDialog();
            }
        });




    }


    private void showAddFoodItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_food_item, null);

        // Initialize dialog views (EditText fields and ImageView)
        EditText itemNameEditText = dialogView.findViewById(R.id.itemNameEditText);
        EditText itemDescriptionEditText = dialogView.findViewById(R.id.itemDescriptionEditText);
        EditText itemPriceEditText = dialogView.findViewById(R.id.itemPriceEditText);
        Button selectImageBtn = dialogView.findViewById(R.id.selectImageBtn);

        // Dexter permission request to access the gallery
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
                        // Handle permission denied
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

                        if (selectedImageUri != null) {

                            // Call a method to add the food item to Firestore
                            addFoodItemToFirestore(itemName, itemDescription, itemPrice, selectedImageUri);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK) {
            // Retrieve the selected image's URI
            if (data != null) {
                selectedImageUri = data.getData();

                if (selectedImageUri == null) {
                    Toast.makeText(this, "Failed to get image URI", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(this, "Image URI Taken", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void addFoodItemToFirestore(String itemName, String itemDescription, double itemPrice, Uri selectedImageUri) {
        // Check if selectedImageUri is not null
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new food item document
        Map<String, Object> foodItem = new HashMap<>();
        foodItem.put("name", itemName);
        foodItem.put("description", itemDescription);
        foodItem.put("price", itemPrice);




        // Use the restaurantRef to add the food item details to the specific restaurant
        restaurantRef
                .collection("foodItems") // Reference to the food items subcollection of the selected restaurant
                .add(foodItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Get the ID of the newly added food item document
                        String foodItemId = documentReference.getId();

                        // Upload the selected image to Firebase Storage
                        uploadImageToStorage(foodItemId, selectedImageUri);
                        Toast.makeText(getApplicationContext(), "Added Food Item to Firestore", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(getApplicationContext(), "Error adding food item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadImageToStorage(String foodItemId, Uri selectedImageUri) {
        if (selectedImageUri == null) {
            Toast.makeText(getApplicationContext(), "No image selected for upload", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get a reference to the Firebase Storage location where you want to store the image
        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("foodItemImages") // Use a specific folder name for food item images
                .child(foodItemId) // Use the food item document ID as the image name
                .child("image.jpg"); // You can use a fixed name or create a unique name

        // Upload the image to the specified storage location
        storageRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(getApplicationContext(), "Error uploading image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }




}
