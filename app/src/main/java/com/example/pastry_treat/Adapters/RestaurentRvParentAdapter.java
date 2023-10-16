package com.example.pastry_treat.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.Models.RestaurentRvParentModel;
import com.example.pastry_treat.R;

import java.util.List;

public class RestaurentRvParentAdapter extends RecyclerView.Adapter<RestaurentRvParentAdapter.ViewHolder> {

    List<RestaurentRvParentModel> homeRvParentModelClassList;

    Context context;

    public RestaurentRvParentAdapter(List<RestaurentRvParentModel> homeRvParentModelClassList, Context context) {
        this.homeRvParentModelClassList = homeRvParentModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurentRvParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurent_parent_rv_layout, null, false);

        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull RestaurentRvParentAdapter.ViewHolder holder, int position) {
        holder.homenav_tv_parent_title.setText(homeRvParentModelClassList.get(position).title);

        RestaurentRvChildAdapter homeRvChildAdapter;

        homeRvChildAdapter = new RestaurentRvChildAdapter(homeRvParentModelClassList.get(position).homeRvChildModelClassList, context);
        holder.homenav_rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.homenav_rv_child.setAdapter(homeRvChildAdapter);
        homeRvChildAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return homeRvParentModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView homenav_rv_child;

        TextView homenav_tv_parent_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homenav_rv_child = itemView.findViewById(R.id.homenav_rv_child);
            homenav_tv_parent_title = itemView.findViewById(R.id.homenav_tv_parent_title);
        }
    }
}
