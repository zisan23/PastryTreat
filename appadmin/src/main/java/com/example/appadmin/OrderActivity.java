package com.example.appadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appadmin.Adapters.OrderedItemAdapter;
import com.example.appadmin.Models.OrderedItemModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        user = FirebaseAuth.getInstance().getCurrentUser();

        ShowOrdersRecyclerView();


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
                    .whereEqualTo("ownerId",user.getUid())
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
                                String orderId = documentSnapshot.getString("orderId");
                                String buyerAddress = documentSnapshot.getString("buyerAddresss");


                                int quantity = 0;
                                if (quantityLong != null) {
                                    quantity = quantityLong.intValue();

                                } else {
                                    Toast.makeText(OrderActivity.this, "QUANTITY PROBLEM", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Double totalPrice = documentSnapshot.getDouble("totalPrice");



                                //Toast.makeText(HomeActivity.this, restaurantName + " " + profileImage, Toast.LENGTH_SHORT).show();

//                                HomeRvRestaurentChildModel restaurent = new HomeRvRestaurentChildModel();
//
//                                restaurent.setName(restaurantName);
//                                restaurent.setAddress(location);
//                                restaurent.setImage(profileImage);
//                                restaurent.setShortDescription("description needs to be added");
//                                restaurent.setOwnerId(ownerId);
//
//                                featuredRestaurents.add(restaurent);

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
                                orderedItem.setBuyerAddress(buyerAddress);

                                orderedItemModels.add(orderedItem);

                            }

                            OrderedItemAdapter orderedItemAdapter = new OrderedItemAdapter(OrderActivity.this,
                                    orderedItemModels);
                            cart_rv_orderedItems.setLayoutManager(new LinearLayoutManager(OrderActivity.this
                                    ,LinearLayoutManager.VERTICAL,false));
                            cart_rv_orderedItems.setAdapter(orderedItemAdapter);
                            orderedItemAdapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(OrderActivity.this, "orders not loaded", Toast.LENGTH_LONG).show();
                        }
                    });

        } catch (Exception e) {

            System.out.println("home cart orders recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}