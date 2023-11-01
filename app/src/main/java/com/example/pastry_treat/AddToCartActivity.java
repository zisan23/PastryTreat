package com.example.pastry_treat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pastry_treat.Models.OrderedItemModel;
import com.example.pastry_treat.databinding.ActivityAddToCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddToCartActivity extends AppCompatActivity {

    ActivityAddToCartBinding binding;

    String foodId;
    String ownerId;
    String foodName;
    String description;
    String imageUri;
    Double price;
    String restaurantName;
    String buyerAddress;


    FirebaseUser user;
    FirebaseFirestore firestore;
    DocumentReference docRef;

    Integer quantity;
    Double total_price;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        binding = ActivityAddToCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        quantity = 0;
        total_price = 0.0;
        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        String uid = user.getUid();
        docRef = firestore.collection("User").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Data for the user exists, you can access it here
                        buyerAddress = document.getString("address");
                    } else {
                        // Document does not exist
                        Toast.makeText(AddToCartActivity.this, "Home Activity User Do Not Exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // An error occurred while fetching the data
                    Toast.makeText(AddToCartActivity.this, "ERROR WHILE FRETCHING DATA", Toast.LENGTH_SHORT).show();
                }

            }
        });





        foodId = getIntent().getStringExtra("foodId");
        ownerId = getIntent().getStringExtra("ownerId");
        foodName = getIntent().getStringExtra("foodName");
        imageUri = getIntent().getStringExtra("imageUri");
        description = getIntent().getStringExtra("description");
        price = getIntent().getDoubleExtra("price", 0.0);
        restaurantName = getIntent().getStringExtra("restaurantName");

        Picasso.get()
                .load(imageUri)
                .into(binding.ivAddToCart);

        binding.tvFoodNameAddToCart.setText(foodName);
        binding.tvDescFoodAddToCart.setText(description);
        binding.tvPriceAddToCart.setText(Double.toString(price));
        binding.quantityTv.setText(Integer.toString(quantity));

        binding.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                total_price = quantity * price;
                binding.quantityTv.setText(Integer.toString(quantity));
            }
        });

        binding.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    quantity--;
                    total_price = quantity * price;
                    binding.quantityTv.setText(Integer.toString(quantity));
                }
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(quantity > 0){
                    OrderedItemModel order = new OrderedItemModel();
                    String orderId = UUID.randomUUID().toString();
                    order.setOrderId(orderId);
                    order.setFoodId(foodId);
                    order.setFoodName(foodName);
                    order.setQuantity(quantity);
                    order.setTotalPrice(total_price);
                    order.setImageUri(imageUri);
                    order.setRestaurentName(restaurantName);
                    order.setOwnerId(ownerId);
                    order.setBuyerAddresss(buyerAddress);
                    order.setBuyerId(uid);

                    addToCartFireStorePekka(order);
                }
                else{
                    Toast.makeText(AddToCartActivity.this, "order some", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    private void addToCartFireStorePekka(OrderedItemModel order) {

        // Save data to Firestore
        firestore.collection("orders")
                .add(order)
                .addOnSuccessListener(new OnSuccessListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(Object documentReference) {
                        Toast.makeText(AddToCartActivity.this, "Order has been placed", Toast.LENGTH_LONG).show();
                        quantity = 0;
                        total_price = 0.0;
                        binding.quantityTv.setText(Integer.toString(quantity));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddToCartActivity.this, "Error!!! order has not been placed", Toast.LENGTH_LONG).show();
                    }
                });

    }


}
