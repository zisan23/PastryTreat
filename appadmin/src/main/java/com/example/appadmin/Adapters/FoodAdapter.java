package com.example.appadmin.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appadmin.Models.FoodModel;
import com.example.appadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodItemViewHolder> {
    private List<FoodModel> foodItems;

    public FoodAdapter(List<FoodModel> foodItems) {
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        FoodModel foodItem = foodItems.get(position);

        // Bind data to the views in the ViewHolder
        holder.foodNameTextView.setText(foodItem.name);
        holder.foodDescriptionTextView.setText(foodItem.description);
        holder.foodPriceTextView.setText(String.valueOf(foodItem.price));

        // Load the image using Picasso or Glide
        Picasso.get()
                .load(foodItem.imageUri)
                .into(holder.foodImageView);
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder {
        public TextView foodNameTextView;
        public TextView foodDescriptionTextView;
        public TextView foodPriceTextView;
        public ImageView foodImageView;

        public FoodItemViewHolder(View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.rest_tv_product_name);
            foodDescriptionTextView = itemView.findViewById(R.id.rest_tv_product_description);
            foodPriceTextView = itemView.findViewById(R.id.rest_tv_product_price);
            foodImageView = itemView.findViewById(R.id.rest_iv_child_img);
        }
    }
}

