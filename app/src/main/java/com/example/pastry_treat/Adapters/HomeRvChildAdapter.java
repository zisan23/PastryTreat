package com.example.pastry_treat.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.AddToCartActivity;
import com.example.pastry_treat.HomeActivity;
import com.example.pastry_treat.Models.HomeRvChildModelClass;
import com.example.pastry_treat.Models.HomeRvParentModelClass;
import com.example.pastry_treat.R;

import java.util.List;

public class HomeRvChildAdapter extends RecyclerView.Adapter<HomeRvChildAdapter.ViewHolder> {

    List<HomeRvChildModelClass> homeRvChildModelClassList;
    Context context;

    public HomeRvChildAdapter(List<HomeRvChildModelClass> homeRvChildModelClassList, Context context) {
        this.homeRvChildModelClassList = homeRvChildModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRvChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homenav_child_rv_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvChildAdapter.ViewHolder holder, int position) {
        holder.homenav_iv_child_image.setImageResource(homeRvChildModelClassList.get(position).image);

        holder.homenav_iv_child_image.setOnClickListener(new View.OnClickListener() {
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

        ImageView homenav_iv_child_image ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homenav_iv_child_image = itemView.findViewById(R.id.homenav_iv_child_img);

        }
    }
}
