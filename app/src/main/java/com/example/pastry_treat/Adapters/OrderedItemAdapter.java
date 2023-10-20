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

import com.example.pastry_treat.Models.OrderedItemModel;
import com.example.pastry_treat.R;

import java.util.List;

public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.ViewHolder> {

    Context context;
    List<OrderedItemModel> orderedItemModelList;

    public OrderedItemAdapter(Context context, List<OrderedItemModel> orderedItemModelList) {
        this.context = context;
        this.orderedItemModelList = orderedItemModelList;
    }

    @NonNull
    @Override
    public OrderedItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_rv_order_menu_item,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderedItemAdapter.ViewHolder holder, int position) {
        holder.cart_rv_menu_item_img.setImageResource(orderedItemModelList.get(position).getImage());

        holder.cart_rv_menu_foodName.setText(orderedItemModelList.get(position).getFoodName());

        holder.cart_rv_menu_Restaurent.setText(orderedItemModelList.get(position).getRestaurentName());

        holder.cart_rv_menu_price.setText(Integer.toString(orderedItemModelList.get(position).getTotalPrice()));

        holder.cart_rv_menu_quantity.setText(Integer.toString(orderedItemModelList.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return orderedItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_rv_menu_item_img;
        TextView cart_rv_menu_foodName, cart_rv_menu_Restaurent, cart_rv_menu_quantity,cart_rv_menu_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_rv_menu_item_img = itemView.findViewById(R.id.cart_rv_menu_item_img);
            cart_rv_menu_foodName = itemView.findViewById(R.id.cart_rv_menu_foodName);
            cart_rv_menu_Restaurent = itemView.findViewById(R.id.cart_rv_menu_Restaurent);
            cart_rv_menu_quantity = itemView.findViewById(R.id.cart_rv_menu_quantity);
            cart_rv_menu_price = itemView.findViewById(R.id.cart_rv_menu_price);
        }
    }
}
