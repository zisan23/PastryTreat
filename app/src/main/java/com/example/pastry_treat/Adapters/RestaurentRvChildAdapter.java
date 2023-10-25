package com.example.pastry_treat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.AddToCartActivity;
import com.example.pastry_treat.Models.RestaurentRvChildModel;
import com.example.pastry_treat.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurentRvChildAdapter extends RecyclerView.Adapter<RestaurentRvChildAdapter.ViewHolder> {

    List<RestaurentRvChildModel> homeRvChildModelClassList;
    Context context;

    public RestaurentRvChildAdapter(List<RestaurentRvChildModel> homeRvChildModelClassList, Context context) {
        this.homeRvChildModelClassList = homeRvChildModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurentRvChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurent_child_rv_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurentRvChildAdapter.ViewHolder holder, int position) {
        holder.homenav_iv_child_image.setImageResource(homeRvChildModelClassList.get(position).image);

        holder.homenav_tv_product_name.setText(homeRvChildModelClassList.get(position).productName);

        holder.homenav_tv_product_description.setText(homeRvChildModelClassList.get(position).description);

        holder.homenav_tv_product_price.setText("$" + Double.toString(homeRvChildModelClassList.get(position).price));

//        Picasso.get()
//                .load(homeRvChildModelClassList.get(position).image)
//                        .into(holder.homenav_iv_child_image);

        holder.homenav_cv_child_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(context, AddToCartActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return homeRvChildModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView homenav_cv_child_item;
        ImageView homenav_iv_child_image ;
        TextView homenav_tv_product_name, homenav_tv_product_description, homenav_tv_product_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homenav_iv_child_image = itemView.findViewById(R.id.homenav_iv_child_img);
            homenav_tv_product_name = itemView.findViewById(R.id.homenav_tv_product_name);
            homenav_tv_product_description = itemView.findViewById(R.id.homenav_tv_product_description);
            homenav_tv_product_price = itemView.findViewById(R.id.homenav_tv_product_price);

            homenav_cv_child_item = itemView.findViewById(R.id.homenav_cv_child_item);

        }
    }
}
