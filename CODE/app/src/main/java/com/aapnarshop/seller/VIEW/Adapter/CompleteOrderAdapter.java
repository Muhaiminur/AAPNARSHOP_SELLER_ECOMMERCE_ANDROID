package com.aapnarshop.seller.VIEW.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderListResponse;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.FRAGMENT.order.CompleteOrderDetailsFragmentArgs;
import com.aapnarshop.seller.VIEW.FRAGMENT.order.CompleteOrderDirections;
import com.aapnarshop.seller.databinding.RecyclerviewCompleteOrderBinding;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Anik Roy on 19,November,2020
 */
public class CompleteOrderAdapter extends RecyclerView.Adapter<CompleteOrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderListResponse> orderList;
    private Utility utility;

    public CompleteOrderAdapter(Context context, List<OrderListResponse> orderList,Utility utility) {
        this.context = context;
        this.orderList = orderList;
        this.utility = utility;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewCompleteOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_complete_order,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderListResponse orderListResponse = orderList.get(position);
        holder.binding.rvCompleteOrderName.setText(String.format("Name:%s", orderListResponse.getBuyerName()));
        holder.binding.rvCompleteOrderMobileNumber.setText(orderListResponse.getBuyerPhone());
        holder.binding.rvCompleteOrderOrderNumber.setText(String.format("Order no #%s", orderListResponse.getOrderTransactionId()));
        holder.binding.rvCompleteOrderOrderTime.setText(utility.getDateTimeFromMillSecond(orderListResponse.getCreatedAt()));

        if (orderListResponse.getOrderStatus().equalsIgnoreCase(KeyWord.CANCELLED)){
            holder.binding.rvCompleteOrderOrderStatus.setText(String.format("%s:%s", orderListResponse.getOrderStatus(), orderListResponse.getOrderReason()));
            holder.binding.rvCompleteOrderOrderStatus.setTextColor(context.getResources().getColor(R.color.deep_red));
            holder.binding.rvCompleteOrder.setCardBackgroundColor(context.getResources().getColor(R.color.app_yellow2));

        }else {
            holder.binding.rvCompleteOrderOrderStatus.setText(String.format("Order status:%s", orderListResponse.getOrderStatus()));

        }
        Glide.with(context)
                .load(orderListResponse.getBuyerProfileImage())
                .placeholder(R.drawable.ic_profile)
                .into(holder.binding.rvCompleteOrderProfileImage);

        holder.binding.rvCompleteOrderDetails.setOnClickListener(v -> {
            CompleteOrderDirections.ActionCompleteOrderFragmentToCompleteOrderDetailsFragment action = CompleteOrderDirections.actionCompleteOrderFragmentToCompleteOrderDetailsFragment();
            action.setCompleteOrderId(orderListResponse.getOrderId());
            Navigation.findNavController(v).navigate(action);
            utility.hideKeyboard(holder.itemView);
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewCompleteOrderBinding binding;
        public ViewHolder(RecyclerviewCompleteOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
