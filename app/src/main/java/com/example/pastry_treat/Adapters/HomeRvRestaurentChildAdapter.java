package com.example.pastry_treat.Adapters;

import android.annotation.SuppressLint;
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

import com.example.pastry_treat.Models.HomeRvRestaurentChildModel;
import com.example.pastry_treat.R;
import com.example.pastry_treat.RestaurentActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRvRestaurentChildAdapter extends RecyclerView.Adapter<HomeRvRestaurentChildAdapter.ViewHolder> {

    List<HomeRvRestaurentChildModel> childModelList;
    Context context;

    public HomeRvRestaurentChildAdapter(List<HomeRvRestaurentChildModel> childModelList, Context context) {
        this.childModelList = childModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRvRestaurentChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.homenav_restaurent_rv_child_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvRestaurentChildAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.home_cv_RvRestaurent_childItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RestaurentActivity.class);
                intent.putExtra("ownerId",childModelList.get(position).getOwnerId());
                context.startActivity(intent);
            }
        });



        //holder.home_iv_RvRestaurent_child_img.setImageResource(childModelList.get(position).getImage());

        Picasso.get()
                .load(childModelList.get(position).getImage()).into(holder.home_iv_RvRestaurent_child_img);

        holder.home_tv_RvRestaurent_child_restName.setText(childModelList.get(position).getName());

        holder.home_tv_RvRestaurent_child_restAddress.setText(childModelList.get(position).getAddress());

//        holder.home_tv_RvRestaurent_child_restDesc.setText(childModelList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return childModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView home_cv_RvRestaurent_childItem;

        ImageView home_iv_RvRestaurent_child_img;
        TextView home_tv_RvRestaurent_child_restName,
                home_tv_RvRestaurent_child_restAddress,
                home_tv_RvRestaurent_child_restDesc;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_cv_RvRestaurent_childItem =  itemView.findViewById(R.id.home_cv_RvRestaurent_childItem);
            home_iv_RvRestaurent_child_img = itemView.findViewById(R.id.home_iv_RvRestaurent_child_img);
            home_tv_RvRestaurent_child_restName = itemView.findViewById(R.id.home_tv_RvRestaurent_child_restName);
            home_tv_RvRestaurent_child_restAddress = itemView.findViewById(R.id.home_tv_RvRestaurent_child_restAddress);
//            home_tv_RvRestaurent_child_restDesc = itemView.findViewById(R.id.home_tv_RvRestaurent_child_restDesc);

        }
    }
}

