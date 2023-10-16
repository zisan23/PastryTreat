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
    //////best deals is not showing!!!!????


    private ArrayList<RestaurentRvParentModel> homeRvParentModelClassArrayList;

    private RestaurentRvParentAdapter homeRvParentAdapter;


    //delete these later
    private String sn = "Cheese Cake";
    private String sd = "Product description goes here. Provide details about the delicious dessert.";
    private Double sp = 420.69;

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


            top_products.add(new RestaurentRvChildModel(R.drawable.chocobiscuit,sn,sd,sp));
            top_products.add(new RestaurentRvChildModel(R.drawable.chococake,sn,sd,sp));
            top_products.add(new RestaurentRvChildModel(R.drawable.waffles,sn,sd,sp));
            top_products.add(new RestaurentRvChildModel(R.drawable.strawcupcake,sn,sd,sp));
            top_products.add(new RestaurentRvChildModel(R.drawable.chococupcake,sn,sd,sp));


            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.moosecake,sn,sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.whitecake, sn, sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.jellycake,sn,sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.cheesecake,sn,sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.marbleee,sn,sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.crunchydelight,sn,sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.italianpudding,sn,sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.redforrest,sn,sd,sp));
            you_may_like_it.add(new RestaurentRvChildModel(R.drawable.blackforrest,sn,sd,sp));


            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("Top Products", top_products));
            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("You May Also Like ", you_may_like_it));

            //top_products.clear();
            //you_may_like_it.clear();



            popular_products.add(new RestaurentRvChildModel(R.drawable.dessertkababs,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.donuts,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.creamdelight,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.oreoart,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.bubbleoybillcake,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.yellowicecake,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.cheesecupcake,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.theglobecake,sn,sd,sp));
            popular_products.add(new RestaurentRvChildModel(R.drawable.chocoberrycake,sn,sd,sp));

            best_deals.add(new RestaurentRvChildModel(R.drawable.cheesecake,sn,sd,sp));
            best_deals.add(new RestaurentRvChildModel(R.drawable.cheesecake,sn,sd,sp));
            best_deals.add(new RestaurentRvChildModel(R.drawable.cheesecake,sn,sd,sp));
            best_deals.add(new RestaurentRvChildModel(R.drawable.cheesecake,sn,sd,sp));
            best_deals.add(new RestaurentRvChildModel(R.drawable.cheesecake,sn,sd,sp));
            best_deals.add(new RestaurentRvChildModel(R.drawable.cheesecake,sn,sd,sp));

            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("Popular Products", popular_products));
            homeRvParentModelClassArrayList.add(new RestaurentRvParentModel("Best Deals", best_deals));


            homeRvParentAdapter = new RestaurentRvParentAdapter(homeRvParentModelClassArrayList, RestaurentActivity.this);
            recyclerView_parent.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            recyclerView_parent.setAdapter(homeRvParentAdapter);
            homeRvParentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}