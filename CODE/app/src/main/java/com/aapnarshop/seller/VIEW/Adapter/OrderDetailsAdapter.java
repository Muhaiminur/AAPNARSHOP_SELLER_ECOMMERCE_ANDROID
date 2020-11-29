package com.aapnarshop.seller.VIEW.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderListResponse;
import com.aapnarshop.seller.Model.GET.OrderProduct;
import com.aapnarshop.seller.Model.SEND.OrderStatusRequest;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.FRAGMENT.order.PendingOrderDirections;
import com.aapnarshop.seller.VIEWMODEL.OrderViewModel;
import com.aapnarshop.seller.databinding.RecyclerviewOrderDetailsBinding;
import com.aapnarshop.seller.databinding.RecyclerviewPendingOrderBinding;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Anik Roy on 17,November,2020
 */
public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private final Context context;
    private final List<OrderProduct> productList;


    public OrderDetailsAdapter(Context context, List<OrderProduct> productList) {
        this.context = context;
        this.productList = productList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewOrderDetailsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_order_details, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderProduct orderListResponse = productList.get(position);
        holder.binding.productName.setText(orderListResponse.getName());
        holder.binding.productQuantity.setText("x"+orderListResponse.getQuantity());
        holder.binding.productPrice.setText(orderListResponse.getPrice());


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewOrderDetailsBinding binding;

        public ViewHolder(RecyclerviewOrderDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
