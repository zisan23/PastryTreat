package com.example.pastry_treat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;


import android.annotation.SuppressLint;
import android.os.Bundle;


import com.example.pastry_treat.FragmentAdapter.SearchFragmentAdapter;

import com.example.pastry_treat.databinding.ActivitySearchBinding;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity extends AppCompatActivity {


    ActivitySearchBinding binding;
    SearchFragmentAdapter fragmentAdapter;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchTabLayout.addTab(binding.searchTabLayout.newTab().setText("Restaurants"));
        binding.searchTabLayout.addTab(binding.searchTabLayout.newTab().setText("Food Items"));


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new SearchFragmentAdapter(fragmentManager,getLifecycle());
        binding.searchViewpager2.setAdapter(fragmentAdapter);

        binding.searchTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.searchViewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.searchViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.searchTabLayout.selectTab(binding.searchTabLayout.getTabAt(position));
            }
        });



    }


}