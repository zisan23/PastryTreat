package com.example.pastry_treat.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pastry_treat.Adapters.HomeRvRestaurentParentAdapter;
import com.example.pastry_treat.Models.HomeRvRestaurentChildModel;
import com.example.pastry_treat.Models.HomeRvRestaurentParentModel;
import com.example.pastry_treat.R;
import com.example.pastry_treat.SearchActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class RestaurantSearchFragment extends Fragment {

    private EditText searchEditText;
    private Button searchBtn;

    private RecyclerView search_rv_parent;
    private ArrayList<HomeRvRestaurentChildModel> featuredRestaurents = new ArrayList<>();
    private ArrayList<HomeRvRestaurentParentModel> homeRvRestaurentParentModelList = new ArrayList<>();


    public RestaurantSearchFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_restaurant_search, container, false);

        search_rv_parent = view.findViewById(R.id.fragment_rest_search_rv_parent);
        searchEditText = view.findViewById(R.id.fragment_rest_search_et_search);
        searchBtn = view.findViewById(R.id.fragment_search_btn);


        searchEditText.requestFocus();

        ShowRestaurantsRecyclerView();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchEditText.getText().toString().trim();

                if(query == null){
                    Toast.makeText(getContext(), "Provide a Valid Restaurant Name", Toast.LENGTH_SHORT).show();
                    searchEditText.requestFocus();
                }
                else{
                    Toast.makeText(getContext(), "Searching" + query, Toast.LENGTH_SHORT).show();
                    ShowQueriedRestaurantsRecyclerView(query);
                    searchEditText.setText("");
                }
            }
        });


        return view;
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
                                Toast.makeText(getContext(), "featurerestaurants empty", Toast.LENGTH_LONG).show();
                            }




                            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Featured Restaurents", featuredRestaurents));

                            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(getContext(), homeRvRestaurentParentModelList,true);
                            search_rv_parent.setLayoutManager(new GridLayoutManager(getContext(), LinearLayoutManager.VERTICAL));

                            search_rv_parent.setAdapter(homeRvRestaurentParentAdapter);
                            homeRvRestaurentParentAdapter.notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext(), "restaurants not loaded", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(getContext(), "No Restaurant exist with such name ", Toast.LENGTH_LONG).show();
                                return;
                            }



                            homeRvRestaurentParentModelList.clear();
                            homeRvRestaurentParentModelList.add(new HomeRvRestaurentParentModel("Queried Restaurants", featuredRestaurents));

                            HomeRvRestaurentParentAdapter homeRvRestaurentParentAdapter = new HomeRvRestaurentParentAdapter(getContext(), homeRvRestaurentParentModelList,true);
                            search_rv_parent.setLayoutManager(new GridLayoutManager(getContext(),LinearLayoutManager.VERTICAL));

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


}