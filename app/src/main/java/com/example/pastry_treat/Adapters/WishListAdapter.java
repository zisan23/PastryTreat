package com.example.pastry_treat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.Models.WishListModel;
import com.example.pastry_treat.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    List<WishListModel> wishListModelList;
    Context context;


    public WishListAdapter(List<WishListModel> wishListModelList, Context context) {
        this.wishListModelList = wishListModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.homenav_wishlist_rv,null,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {

        holder.wishlist_rv_restName.setText(wishListModelList.get(position).restaurantName);
        holder.wishlist_rv_restAddress.setText(wishListModelList.get(position).restAddress);

        Picasso.get().load(wishListModelList.get(position).imageUri)
                .into(holder.wishlist_rv_restImage);

    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView wishlist_rv_restImage;
        TextView wishlist_rv_restName,wishlist_rv_restAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wishlist_rv_restImage = itemView.findViewById(R.id.wishlist_rv_restImage);
            wishlist_rv_restName = itemView.findViewById(R.id.wishlist_rv_restName);
            wishlist_rv_restAddress = itemView.findViewById(R.id.wishlist_rv_restAddress);
        }
    }
}
