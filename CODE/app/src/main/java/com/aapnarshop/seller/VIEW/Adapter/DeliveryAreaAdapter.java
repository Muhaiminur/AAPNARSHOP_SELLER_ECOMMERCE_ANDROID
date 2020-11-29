package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.RecyclerviewDeliveryAreaBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class DeliveryAreaAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<T> items;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val,int position);

    public DeliveryAreaAdapter(Context context, List<T> items){
        this.context = context;
        this.items = items;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        onBindData(holder,items.get(position),position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public T getItem(int position){
        return items.get(position);
    }

}
