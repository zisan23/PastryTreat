package com.example.pastry_treat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.pastry_treat.Adapters.RestaurentRvParentAdapter;
import com.example.pastry_treat.Models.RestaurentRvChildModel;
import com.example.pastry_treat.Models.RestaurentRvParentModel;

import java.util.ArrayList;

public class RestaurentActivity extends AppCompatActivity {

    private RecyclerView recyclerView_parent;
    private ArrayList<RestaurentRvChildModel> top_products;

    private ArrayList<RestaurentRvChildModel> you_may_like_it;
    private ArrayList<RestaurentRvChildModel> popular_products;
    private ArrayList<RestaurentRvChildModel> best_deals;


    private ArrayList<RestaurentRvParentModel> homeRvParentModelClassArrayList;

    private RestaurentRvParentAdapter homeRvParentAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent);

        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbar
        if (actionBar != null) {
            actionBar.hide();
        }



        try {
            recyclerView_parent = (RecyclerView) findViewById(R.id.home_rv_parent);

            top_products = new ArrayList<>();
            you_may_like_it = new ArrayList<>();
            popular_products = new ArrayList<>();
            best_deals = new ArrayList<>();


            homeRvParentModelClassArrayList = new ArrayList<>();


            top_products.add(new RestaurentRvChildModel(R.drawable.chocobiscuit));
            top_products.add(new RestaurentRvChildModel(R.drawable.chococake));
            top_products.add(new RestaurentRvChildModel(R.drawable.waffles));
            top_products.add(new RestaurentRvChildModel(R.drawable.strawcupcake));
            top_products.add(new RestaurentRvChildModel(R.drawable.chococupcake));


            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.moosecake));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.whitecake));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.jellycake));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.cheesecake));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.marbleee));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.crunchydelight));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.italianpudding));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.redforrest));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.blackforrest));


            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("Top Products", top_products));
            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("You May Also Like ", you_may_like_it));

            //top_products.clear();
            //you_may_like_it.clear();


            best_deals.add(new RestaurentRvChildModel(R.drawable.strawchocopastry));
            best_deals.add(new RestaurentRvChildModel(R.drawable.fraisiercake));
            best_deals.add(new RestaurentRvChildModel(R.drawable.macaroons));
            best_deals.add(new RestaurentRvChildModel(R.drawable.brownievalentine));
            best_deals.add(new RestaurentRvChildModel(R.drawable.oreos));

            popular_products.add(new RestaurentRvChildModel(R.drawable.dessertkababs));
            popular_products.add(new RestaurentRvChildModel(R.drawable.donuts));
            popular_products.add(new RestaurentRvChildModel(R.drawable.creamdelight));
            popular_products.add(new RestaurentRvChildModel(R.drawable.oreoart));
            popular_products.add(new RestaurentRvChildModel(R.drawable.bubbleoybillcake));
            popular_products.add(new RestaurentRvChildModel(R.drawable.yellowicecake));
            popular_products.add(new RestaurentRvChildModel(R.drawable.cheesecupcake));
            popular_products.add(new RestaurentRvChildModel(R.drawable.theglobecake));
            popular_products.add(new RestaurentRvChildModel(R.drawable.chocoberrycake));

            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("Popular Products", popular_products));
            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("Best Deals", best_deals));


            homeRvParentAdapter = new RestaurentRvParentAdapter(homeRvParentModelClassArrayList, RestaurentActivity.this);
            recyclerView_parent.setLayoutManager(new LinearLayoutManager(this));
            recyclerView_parent.setAdapter(homeRvParentAdapter);
            homeRvParentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}