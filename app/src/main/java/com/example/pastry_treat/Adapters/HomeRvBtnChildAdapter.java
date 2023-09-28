package com.example.pastry_treat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.Models.HomeRvBtnChildModelClass;
import com.example.pastry_treat.R;

import java.util.List;

public class HomeRvBtnChildAdapter extends RecyclerView.Adapter<HomeRvBtnChildAdapter.ViewHolder> {

    public List<HomeRvBtnChildModelClass> homeRvBtnChildModelClassList;

    public Context context;

    public HomeRvBtnChildAdapter(List<HomeRvBtnChildModelClass> homeRvBtnChildModelClassList, Context context) {
        this.homeRvBtnChildModelClassList = homeRvBtnChildModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRvBtnChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.homenav_rv_btn_child_layout,null,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRvBtnChildAdapter.ViewHolder holder, int position) {
        holder.homeNav_iv_btn_child_img.setImageResource(homeRvBtnChildModelClassList.get(position).image);

        holder.homeNav_tv_btn_child_title.setText(homeRvBtnChildModelClassList.get(position).nameTitle);

        //////need to add something here
    }

    @Override
    public int getItemCount() {
        return homeRvBtnChildModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView homeNav_iv_btn_child_img;

        TextView homeNav_tv_btn_child_title;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            homeNav_iv_btn_child_img = itemView.findViewById(R.id.homeNav_iv_btn_child_img);

            homeNav_tv_btn_child_title = itemView.findViewById(R.id.homeNav_tv_btn_child_title);
        }
    }
}
