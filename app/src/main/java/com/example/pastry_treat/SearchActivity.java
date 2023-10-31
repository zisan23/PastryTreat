package com.example.pastry_treat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pastry_treat.Adapters.HomeRvRestaurentParentAdapter;
import com.example.pastry_treat.Adapters.SearchRvParentAdapter;
import com.example.pastry_treat.Models.HomeRvRestaurentChildModel;
import com.example.pastry_treat.Models.HomeRvRestaurentParentModel;
import com.example.pastry_treat.Models.SearchRvChildModel;
import com.example.pastry_treat.Models.SearchRvParentModel;
import com.example.pastry_treat.databinding.ActivitySearchBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView search_rv_parent;
    //ActivitySearchBinding binding;
    Button searchSearchBtn;
    ArrayList<HomeRvRestaurentChildModel> featuredRestaurents = new ArrayList<>();
    ArrayList<HomeRvRestaurentParentModel> homeRvRestaurentParentModelList = new ArrayList<>();

    EditText searchEtSearch;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEtSearch = (EditText) findViewById(R.id.search_et_search);
        search_rv_parent = (RecyclerView) findViewById(R.id.search_rv_parent);
        searchSearchBtn = (Button) findViewById(R.id.search_search_btn);

        searchEtSearch.requestFocus();

        ShowRestaurantsRecyclerView();

        searchSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchEtSearch.getText().toString().trim();
                Toast.makeText(SearchActivity.this, query, Toast.LENGTH_LONG).show();

                if(query != null){
                    ShowQueriedRestaurantsRecyclerView(query);
                    searchEtSearch.setText("");
                    searchEtSearch.requestFocus();
                }else{
                    Toast.makeText(SearchActivity.this, "Provide a valid Restaurant Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void ShowRestaurantsRecyclerView(){
        try {




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
                                Toast.makeText(getApplicationContext(), "featurerestaurants empty", Toast.LENGTH_LONG).show();
                            }




                            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Featured Restaurents", featuredRestaurents));

                            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(SearchActivity.this, homeRvRestaurentParentModelList,true);
                            search_rv_parent.setLayoutManager(new GridLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL));

                            search_rv_parent.setAdapter(homeRvRestaurentParentAdapter);
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

            System.out.println("search restaurant recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    private void ShowQueriedRestaurantsRecyclerView(String query){
        try {


           featuredRestaurents.clear();

            // Get a reference to the Firestore collection
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            CollectionReference restaurantsCollection = firestore.collection("restaurants");

            // Query to retrieve all documents in the collection
            restaurantsCollection
                    .whereEqualTo("restaurentName",query)
                    .get()

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
                                Toast.makeText(getApplicationContext(), "No Restaurant exist with such name ", Toast.LENGTH_LONG).show();
                                return;
                            }



                            homeRvRestaurentParentModelList.clear();
                            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Queried Restaurants", featuredRestaurents));

                            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(SearchActivity.this, homeRvRestaurentParentModelList,true);
                            search_rv_parent.setLayoutManager(new GridLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL));

                            search_rv_parent.setAdapter(homeRvRestaurentParentAdapter);
                            homeRvRestaurentParentAdapter.notifyDataSetChanged();

                            Toast.makeText(SearchActivity.this, "Query DONE", Toast.LENGTH_SHORT).show();
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

}