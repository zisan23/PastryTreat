package com.example.pastry_treat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pastry_treat.AddToCartActivity;
import com.example.pastry_treat.Models.FoodModel;
import com.example.pastry_treat.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodItemViewHolder> {
    private List<FoodModel> foodItems;
    private Context context;

    public FoodAdapter(Context context,List<FoodModel> foodItems) {

        this.foodItems = foodItems;
        this.context =  context;
    }

    public FoodAdapter() {

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
        holder.foodNameTextView.setText(foodItem.getName());
        holder.foodDescriptionTextView.setText(foodItem.getDescription());
        holder.foodPriceTextView.setText(String.valueOf(foodItem.getPrice()));

        // Load the image using Picasso or Glide
        Picasso.get()
                .load(foodItem.getImageUri())
                .into(holder.foodImageView);

        holder.foodItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddToCartActivity.class);

                intent.putExtra("foodId",foodItem.getFoodId());
                intent.putExtra("ownerId",foodItem.getOwnerId());
                intent.putExtra("foodName",foodItem.getName());
                intent.putExtra("imageUri",foodItem.getImageUri());
                intent.putExtra("price",foodItem.getPrice());
                intent.putExtra("description", foodItem.getDescription());
                intent.putExtra("restaurantName", foodItem.getRestaurantName());

                context.startActivity(intent);

            }
        });
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
        public CardView foodItemCardView;


        public FoodItemViewHolder(View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.rest_tv_product_name);
            foodDescriptionTextView = itemView.findViewById(R.id.rest_tv_product_description);
            foodPriceTextView = itemView.findViewById(R.id.rest_tv_product_price);
            foodImageView = itemView.findViewById(R.id.rest_iv_child_img);
            foodItemCardView = itemView.findViewById(R.id.rest_cv_child_item);
        }
    }
}

