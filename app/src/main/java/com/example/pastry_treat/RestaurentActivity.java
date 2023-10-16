package com.example.pastry_treat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.pastry_treat.Adapters.HomeRvParentAdapter;
import com.example.pastry_treat.Models.HomeRvChildModelClass;
import com.example.pastry_treat.Models.HomeRvParentModelClass;

import java.util.ArrayList;

public class RestaurentActivity extends AppCompatActivity {

    private RecyclerView recyclerView_parent;
    private ArrayList<HomeRvChildModelClass> top_products;

    private ArrayList<HomeRvChildModelClass> you_may_like_it;
    private ArrayList<HomeRvChildModelClass> popular_products;
    private ArrayList<HomeRvChildModelClass> best_deals;


    private ArrayList<HomeRvParentModelClass> homeRvParentModelClassArrayList;

    private HomeRvParentAdapter homeRvParentAdapter;

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


            top_products.add(new HomeRvChildModelClass(R.drawable.chocobiscuit));
            top_products.add(new HomeRvChildModelClass(R.drawable.chococake));
            top_products.add(new HomeRvChildModelClass(R.drawable.waffles));
            top_products.add(new HomeRvChildModelClass(R.drawable.strawcupcake));
            top_products.add(new HomeRvChildModelClass(R.drawable.chococupcake));


            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.moosecake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.whitecake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.jellycake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.cheesecake));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.marbleee));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.crunchydelight));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.italianpudding));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.redforrest));
            you_may_like_it.add(new HomeRvChildModelClass(R.drawable.blackforrest));


            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("Top Products", top_products));
            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("You May Also Like ", you_may_like_it));

            //top_products.clear();
            //you_may_like_it.clear();


            best_deals.add(new HomeRvChildModelClass(R.drawable.strawchocopastry));
            best_deals.add(new HomeRvChildModelClass(R.drawable.fraisiercake));
            best_deals.add(new HomeRvChildModelClass(R.drawable.macaroons));
            best_deals.add(new HomeRvChildModelClass(R.drawable.brownievalentine));
            best_deals.add(new HomeRvChildModelClass(R.drawable.oreos));

            popular_products.add(new HomeRvChildModelClass(R.drawable.dessertkababs));
            popular_products.add(new HomeRvChildModelClass(R.drawable.donuts));
            popular_products.add(new HomeRvChildModelClass(R.drawable.creamdelight));
            popular_products.add(new HomeRvChildModelClass(R.drawable.oreoart));
            popular_products.add(new HomeRvChildModelClass(R.drawable.bubbleoybillcake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.yellowicecake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.cheesecupcake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.theglobecake));
            popular_products.add(new HomeRvChildModelClass(R.drawable.chocoberrycake));

            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("Popular Products", popular_products));
            homeRvParentModelClassArrayList.add(new HomeRvParentModelClass("Best Deals", best_deals));


            homeRvParentAdapter = new HomeRvParentAdapter(homeRvParentModelClassArrayList, RestaurentActivity.this);
            recyclerView_parent.setLayoutManager(new LinearLayoutManager(this));
            recyclerView_parent.setAdapter(homeRvParentAdapter);
            homeRvParentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}