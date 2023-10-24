package com.example.appadmin;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appadmin.Models.RestaurentModel;
import com.example.appadmin.databinding.ActivityRestaurentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class RestaurentActivity extends AppCompatActivity {

    ActivityRestaurentBinding binding;
    FirebaseFirestore firestore;

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
    }
}
