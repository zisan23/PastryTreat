package com.example.pastry_treat.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.Models.OrderedItemModel;
import com.example.pastry_treat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull OrderedItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //holder.cart_rv_menu_item_img.setImageResource(orderedItemModelList.get(position).getImage());
        String orderId = orderedItemModelList.get(position).getOrderId();

        Picasso.get().load(orderedItemModelList.get(position).getImageUri()).into(holder.cart_rv_menu_item_img);

        holder.cart_rv_menu_foodName.setText(orderedItemModelList.get(position).getFoodName());

        holder.cart_rv_menu_Restaurent.setText(orderedItemModelList.get(position).getRestaurentName());

        holder.cart_rv_menu_price.setText(Double.toString(orderedItemModelList.get(position).getTotalPrice()));

        holder.cart_rv_menu_quantity.setText(Integer.toString(orderedItemModelList.get(position).getQuantity()));

        holder.cart_rv_menu_orderID.setText("Order ID: " + orderedItemModelList.get(position).getOrderId());



        holder.cartCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog to confirm deletion
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Deletion")
                        .setMessage("Have the orders been sent?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Orders have been sent, delete the order
                                deleteOrder(orderId);
                            }
                        })
                        .setNegativeButton("No", null) // Do nothing if "No" is clicked
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderedItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cart_rv_menu_item_img;
        TextView cart_rv_menu_foodName, cart_rv_menu_Restaurent, cart_rv_menu_quantity,cart_rv_menu_price, cart_rv_menu_orderID;
        CardView cartCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_rv_menu_item_img = itemView.findViewById(R.id.cart_rv_menu_item_img);
            cart_rv_menu_foodName = itemView.findViewById(R.id.cart_rv_menu_foodName);
            cart_rv_menu_Restaurent = itemView.findViewById(R.id.cart_rv_menu_Restaurent);
            cart_rv_menu_quantity = itemView.findViewById(R.id.cart_rv_menu_quantity);
            cart_rv_menu_price = itemView.findViewById(R.id.cart_rv_menu_price);
            cart_rv_menu_orderID = itemView.findViewById(R.id.cart_rv_menu_orderID);
            cartCardView = itemView.findViewById(R.id.cart_cv);
        }
    }

    private void deleteOrder(String orderId) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference ordersCollection = firestore.collection("orders");

        Toast.makeText(context, "Deleting order", Toast.LENGTH_LONG).show();

        ordersCollection
                .whereEqualTo("orderId",orderId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        WriteBatch batch = firestore.batch();
                        List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();

                        for(DocumentSnapshot snapshot: doc){
                            batch.delete(snapshot.getReference());
                        }

                        batch.commit()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Order deleted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error deleting order", Toast.LENGTH_LONG).show();
                    }
                });


    }

}
