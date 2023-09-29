package com.example.pastry_treat.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.Models.SearchRvChildModel;
import com.example.pastry_treat.R;

import java.util.List;

public class SearchRvChildAdapter extends RecyclerView.Adapter<SearchRvChildAdapter.ViewHolder> {

    public List<SearchRvChildModel> searchRvChildModelList;
    Context context;


    public SearchRvChildAdapter(List<SearchRvChildModel> searchRvChildModelList, Context context) {
        this.searchRvChildModelList = searchRvChildModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchRvChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_child_rv_layout,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SearchRvChildAdapter.ViewHolder holder, int position) {
        holder.image.setImageResource(searchRvChildModelList.get(position).image);

        holder.foodName.setText(searchRvChildModelList.get(position).name);

        holder.restaurentName.setText(searchRvChildModelList.get(position).restaurent);

        holder.rating.setText(Double.toString(searchRvChildModelList.get(position).rating));
    }

    @Override
    public int getItemCount() {
        return searchRvChildModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView foodName, restaurentName, rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.search_iv_child_img);
            foodName = itemView.findViewById(R.id.search_tv_child_foodname);
            restaurentName = itemView.findViewById(R.id.search_tv_restaurent);
            rating = itemView.findViewById(R.id.search_tv_child_rating);
        }
    }
}
