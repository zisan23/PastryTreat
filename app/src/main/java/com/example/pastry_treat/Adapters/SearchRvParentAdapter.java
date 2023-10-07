package com.example.pastry_treat.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pastry_treat.Models.SearchRvParentModel;
import com.example.pastry_treat.R;

import java.util.List;

public class SearchRvParentAdapter extends RecyclerView.Adapter<SearchRvParentAdapter.ViewHolder> {

    Context context;
    List<SearchRvParentModel> searchRvParentModelList;

    public SearchRvParentAdapter(Context context, List<SearchRvParentModel> searchRvParentModelList) {
        this.context = context;
        this.searchRvParentModelList = searchRvParentModelList;
    }

    @NonNull
    @Override
    public SearchRvParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_parent_rv_layout,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull SearchRvParentAdapter.ViewHolder holder, int position) {
        holder.title.setText(searchRvParentModelList.get(position).title);

        SearchRvChildAdapter searchRvChildAdapter = new SearchRvChildAdapter(searchRvParentModelList.get(position).searchRvChildModelList
                                                    , context);
        holder.child_rv.setLayoutManager(new GridLayoutManager(context,2)); //vertical
        holder.child_rv.setAdapter(searchRvChildAdapter);
        searchRvChildAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return searchRvParentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        RecyclerView child_rv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.search_tv_parent_title);
            child_rv = itemView.findViewById(R.id.search_rv_child);

        }
    }
}
