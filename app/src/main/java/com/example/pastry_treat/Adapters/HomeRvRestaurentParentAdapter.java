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

import com.example.pastry_treat.Models.HomeRvRestaurentParentModel;
import com.example.pastry_treat.R;

import java.util.List;

public class HomeRvRestaurentParentAdapter extends RecyclerView.Adapter<HomeRvRestaurentParentAdapter.ViewHolder> {
    Context context;
    List<HomeRvRestaurentParentModel> parentModelList;
    boolean vertical = false;

    public HomeRvRestaurentParentAdapter(Context context, List<HomeRvRestaurentParentModel> parentModelList) {
        this.context = context;
        this.parentModelList = parentModelList;
    }

    public HomeRvRestaurentParentAdapter(Context context, List<HomeRvRestaurentParentModel> parentModelList, boolean vertical) {
        this.context = context;
        this.parentModelList = parentModelList;
        this.vertical = vertical;
    }

    @NonNull
    @Override
    public HomeRvRestaurentParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homenav_restaurent_rv_parent_layout,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull HomeRvRestaurentParentAdapter.ViewHolder holder, int position) {
        holder.home_tv_RvRestaurent_parent_title.setText(parentModelList.get(position).getTitle());

        HomeRvRestaurentChildAdapter childAdapter;
        childAdapter = new HomeRvRestaurentChildAdapter(parentModelList.get(position).getChildModelList(),context);
        if(vertical){
            holder.home_restaurent_rv_child.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        }
        else{
            holder.home_restaurent_rv_child.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }


        holder.home_restaurent_rv_child.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return parentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView home_restaurent_rv_child;
        TextView home_tv_RvRestaurent_parent_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_tv_RvRestaurent_parent_title = itemView.findViewById(R.id.home_tv_RvRestaurent_parent_title);
            home_restaurent_rv_child = itemView.findViewById(R.id.home_restaurent_rv_child);

        }
    }
}
