package com.example.pastry_treat.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.Models.HomeRvBtnParentModelClass;
import com.example.pastry_treat.R;

import java.util.List;

public class HomeRvBtnParentAdapter extends RecyclerView.Adapter<HomeRvBtnParentAdapter.ViewHolder> {

    List<HomeRvBtnParentModelClass> homeRvBtnParentModelClassList;
    Context context;

    public HomeRvBtnParentAdapter(List<HomeRvBtnParentModelClass> homeRvBtnParentModelClassList, Context context) {
        this.homeRvBtnParentModelClassList = homeRvBtnParentModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRvBtnParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homenav_rv_btn_parent_layout,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull HomeRvBtnParentAdapter.ViewHolder holder, int position) {
        HomeRvBtnChildAdapter homeRvBtnChildAdapter;

        homeRvBtnChildAdapter = new HomeRvBtnChildAdapter(homeRvBtnParentModelClassList.get(position).homeRvBtnChildModelClassList,context);
        holder.homeNav_rv_btn_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.homeNav_rv_btn_child.setAdapter(homeRvBtnChildAdapter);
        homeRvBtnChildAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return homeRvBtnParentModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView homeNav_rv_btn_child;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homeNav_rv_btn_child = itemView.findViewById(R.id.homeNav_rv_btn_child);
        }
    }
}
