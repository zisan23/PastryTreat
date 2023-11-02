package com.example.pastry_treat.FragmentAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pastry_treat.Fragments.FoodSearchFragment;
import com.example.pastry_treat.Fragments.RestaurantSearchFragment;

public class SearchFragmentAdapter extends FragmentStateAdapter {
    public SearchFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new FoodSearchFragment();
        }

        return new RestaurantSearchFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
