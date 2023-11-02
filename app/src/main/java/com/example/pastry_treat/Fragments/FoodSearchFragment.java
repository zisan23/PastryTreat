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

import com.example.pastry_treat.Adapters.FoodAdapter;
import com.example.pastry_treat.Models.FoodModel;
import com.example.pastry_treat.Models.HomeRvRestaurentChildModel;
import com.example.pastry_treat.Models.HomeRvRestaurentParentModel;
import com.example.pastry_treat.R;
import com.example.pastry_treat.RestaurentActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class FoodSearchFragment extends Fragment {

    private EditText searchEditText;
    private Button searchBtn;

    private RecyclerView recyclerView;
    private ArrayList<FoodModel> foodModels = new ArrayList<>();
    private ArrayList<HomeRvRestaurentParentModel> homeRvRestaurentParentModelList = new ArrayList<>();
    private FirebaseFirestore firestore;

    public FoodSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        firestore = FirebaseFirestore.getInstance();
        View view =  inflater.inflate(R.layout.fragment_food_search, container, false);

        searchEditText = view.findViewById(R.id.fragment_food_search_et_search);
        searchBtn = view.findViewById(R.id.fragment_food_search_btn);
        recyclerView = view.findViewById(R.id.fragment_food_search_rv_parent);

        recyclerView.setPadding(10,10,10,10);

        ShowFoodItemsRecyclerView();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = searchEditText.getText().toString().trim();

                if(query == null){
                    Toast.makeText(getContext(), "Provide a valid food name", Toast.LENGTH_SHORT).show();
                }
                else{
                    ShowQueriedFoodItemsRecyclerView(query);
                    searchEditText.setText("");
                    searchEditText.requestFocus();
                }
            }
        });



        return view;
    }

    private void ShowFoodItemsRecyclerView(){
        try {

            foodModels.clear();

            CollectionReference restaurantsCollection = firestore.collection("foodItems");

            // Query to retrieve all documents in the collection
            restaurantsCollection
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                FoodModel food = documentSnapshot.toObject(FoodModel.class);


                                foodModels.add(food);

                            }
                            //Log.d("Firestore", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                            //Toast.makeText(HomeActivity.this, queryDocumentSnapshots.toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(HomeActivity.this, "Restaurants Loaded", Toast.LENGTH_SHORT).show();

                            if(foodModels.isEmpty()){
                                Toast.makeText(getContext(), "food-models empty", Toast.LENGTH_LONG).show();
                            }



                            FoodAdapter foodAdapter = new FoodAdapter(getContext(),foodModels);

                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            recyclerView.setAdapter(foodAdapter);
                            foodAdapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "foods not loaded", Toast.LENGTH_LONG).show();
                        }
                    });




        } catch (Exception e) {

            System.out.println("search food items recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void ShowQueriedFoodItemsRecyclerView(String query){
        try {

            foodModels.clear();

            CollectionReference restaurantsCollection = firestore.collection("foodItems");

            // Query to retrieve all documents in the collection
            restaurantsCollection
                    .whereEqualTo("name",query)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                FoodModel food = documentSnapshot.toObject(FoodModel.class);
                                foodModels.add(food);

                            }
                            //Log.d("Firestore", "Number of documents retrieved: " + queryDocumentSnapshots.size());
                            //Toast.makeText(HomeActivity.this, queryDocumentSnapshots.toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(HomeActivity.this, "Restaurants Loaded", Toast.LENGTH_SHORT).show();

                            if(foodModels.isEmpty()){
                                Toast.makeText(getContext(), "no food item with such name found", Toast.LENGTH_LONG).show();
                            }

                            FoodAdapter foodAdapter = new FoodAdapter(getContext(),foodModels);

                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                            recyclerView.setAdapter(foodAdapter);
                            foodAdapter.notifyDataSetChanged();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "foods not loaded", Toast.LENGTH_LONG).show();
                        }
                    });


        } catch (Exception e) {

            System.out.println("search food items recyclerview not working");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}