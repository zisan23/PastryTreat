package com.example.pastry_treat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.pastry_treat.Adapters.SearchRvParentAdapter;
import com.example.pastry_treat.Models.SearchRvChildModel;
import com.example.pastry_treat.Models.SearchRvParentModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    RecyclerView search_rv_parent;

    List<SearchRvChildModel> searchRvChildModelList;

    List<SearchRvParentModel> searchRvParentModelList;

    SearchRvParentAdapter searchRvParentAdapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_rv_parent = (RecyclerView) findViewById(R.id.search_rv_parent);

        searchRvChildModelList = new ArrayList<>();
        searchRvParentModelList = new ArrayList<>();

        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));
        searchRvChildModelList.add(new SearchRvChildModel(R.drawable.img1,"porota",2.35,"Valo lage na"));

        searchRvParentModelList.add(new SearchRvParentModel("Food Search",searchRvChildModelList));

        searchRvParentAdapter = new SearchRvParentAdapter(this,searchRvParentModelList);
        search_rv_parent.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
        search_rv_parent.setAdapter(searchRvParentAdapter);
        searchRvParentAdapter.notifyDataSetChanged();
    }
}