package com.example.appadmin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appadmin.Models.RestaurentRvFoodModel;
import com.example.appadmin.R;

import java.util.List;

public class RestaurentRvFoodAdapter extends RecyclerView.Adapter<RestaurentRvFoodAdapter.ViewHolder> {

    List<RestaurentRvFoodModel> homeRvChildModelClassList;
    Context context;

    public RestaurentRvFoodAdapter(List<RestaurentRvFoodModel> homeRvChildModelClassList, Context context) {
        this.homeRvChildModelClassList = homeRvChildModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.rest_iv_child_image.setImageResource(Integer.parseInt(homeRvChildModelClassList.get(position).imageUri));

        holder.rest_tv_product_name.setText(homeRvChildModelClassList.get(position).productName);

        holder.rest_tv_product_description.setText(homeRvChildModelClassList.get(position).description);

        holder.rest_tv_product_price.setText("$" + Double.toString(homeRvChildModelClassList.get(position).price));


    }

    @Override
    public int getItemCount() {
        return homeRvChildModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView rest_cv_child_item;
        ImageView rest_iv_child_image ;
        TextView rest_tv_product_name, rest_tv_product_description, rest_tv_product_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rest_iv_child_image = itemView.findViewById(R.id.rest_iv_child_img);
            rest_tv_product_name = itemView.findViewById(R.id.rest_tv_product_name);
            rest_tv_product_description = itemView.findViewById(R.id.rest_tv_product_description);
            rest_tv_product_price = itemView.findViewById(R.id.rest_tv_product_price);

            rest_cv_child_item = itemView.findViewById(R.id.rest_cv_child_item);

        }
    }



}
