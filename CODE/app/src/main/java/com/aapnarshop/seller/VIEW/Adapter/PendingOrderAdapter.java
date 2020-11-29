package com.aapnarshop.seller.VIEW.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderListResponse;
import com.aapnarshop.seller.Model.SEND.OrderStatusRequest;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.FRAGMENT.order.PendingOrderDirections;
import com.aapnarshop.seller.VIEWMODEL.OrderViewModel;
import com.aapnarshop.seller.databinding.RecyclerviewPendingOrderBinding;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Anik Roy on 17,November,2020
 */
public class PendingOrderAdapter extends RecyclerView.Adapter<PendingOrderAdapter.ViewHolder> {

    private final Context context;
    private final List<OrderListResponse> orderList;
    private final OrderViewModel orderViewModel;
    private Utility utility;
    private String reason="";
    private String all_order;



    public PendingOrderAdapter(Context context, List<OrderListResponse> orderList, OrderViewModel orderViewModel, Utility utility) {
        this.context = context;
        this.orderList = orderList;
        this.orderViewModel = orderViewModel;
        this.utility = utility;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewPendingOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_pending_order, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OrderListResponse orderListResponse = orderList.get(position);

        holder.binding.rvPendingOrderName.setText(orderListResponse.getBuyerName());
        holder.binding.rvPendingOrderMobileNumber.setText(orderListResponse.getBuyerPhone());
        holder.binding.rvPendingOrderOrderNumber.setText(String.format("Order #%s", orderListResponse.getOrderTransactionId()));
        Glide.with(context)
                .load(orderListResponse.getBuyerProfileImage())
                .placeholder(R.drawable.ic_profile)
                .into(holder.binding.rvPendingOrderProfileImage);

        holder.binding.rvPendingOrderOrderTime.setText(utility.getDateTimeFromMillSecond(orderListResponse.getCreatedAt()));

        if (orderListResponse.getOrderStatus().equalsIgnoreCase(KeyWord.PLACED)) {

                holder.binding.rvPendingOrderOrderStatus.setText(String.format("Order Status: %s", "Placed"));
                holder.binding.rvPendingOrder.setCardBackgroundColor(context.getResources().getColor(R.color.app_yellow2));



            holder.binding.rvPendingOrderUpdateOrder.setText("Process");
        } else if (orderListResponse.getOrderStatus().equalsIgnoreCase(KeyWord.PROCESSING)) {
            holder.binding.rvPendingOrderOrderStatus.setText(String.format("Order Status: %s", "Processing"));

            holder.binding.rvPendingOrderUpdateOrder.setText("On way");
        } else if (orderListResponse.getOrderStatus().equalsIgnoreCase(KeyWord.ON_WAY)) {
            holder.binding.rvPendingOrderOrderStatus.setText(String.format("Order Status: %s", "On way"));

            holder.binding.rvPendingOrderUpdateOrder.setText("Deliver");
        }


        holder.binding.rvPendingOrderUpdateOrder.setOnClickListener(v -> {

            if (orderListResponse.getOrderStatus().equalsIgnoreCase(KeyWord.PLACED)) {
                OrderStatusRequest orderStatusRequest = new OrderStatusRequest(orderListResponse.getOrderId(), KeyWord.PROCESSING);
                orderViewModel.updateOrderStatus(orderStatusRequest);
            } else if (orderListResponse.getOrderStatus().equalsIgnoreCase(KeyWord.PROCESSING)) {
                OrderStatusRequest orderStatusRequest = new OrderStatusRequest(orderListResponse.getOrderId(), KeyWord.ON_WAY);
                orderViewModel.updateOrderStatus(orderStatusRequest);

            } else if (orderListResponse.getOrderStatus().equalsIgnoreCase(KeyWord.ON_WAY)) {
                OrderStatusRequest orderStatusRequest = new OrderStatusRequest(orderListResponse.getOrderId(), KeyWord.DELIVERED);
                orderViewModel.updateOrderStatus(orderStatusRequest);
                holder.binding.rvPendingOrderUpdateOrder.setVisibility(View.GONE);
                holder.binding.rvPendingOrderCancelOrder.setVisibility(View.GONE);
            }

        });

        holder.binding.rvPendingOrderCancelOrder.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_order_reject, null);

            //findViewById here
            RadioGroup radioGroup = view.findViewById(R.id.order_reject_radio_group);

            EditText comment = view.findViewById(R.id.order_reject_comment);
            ImageView clear = view.findViewById(R.id.dialog_order_reject_clear);
            TextView save = view.findViewById(R.id.dialog_order_reject_save);

            builder.setView(view);
            final AlertDialog dialog = builder.create();
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            clear.setOnClickListener(v1 -> {
                dialog.dismiss();
            });

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                int id=group.getCheckedRadioButtonId();
                RadioButton reasonButton = view.findViewById(id);
                if (reasonButton.getText().toString().equalsIgnoreCase("other")){
                    comment.setVisibility(View.VISIBLE);
                }else {
                    comment.setVisibility(View.GONE);
                    reason = reasonButton.getText().toString();
                    comment.setText("");
                }
            });
            save.setOnClickListener(v1 -> {
                if (!comment.getText().toString().equals("")||!comment.getText().toString().isEmpty()){
                    reason = comment.getText().toString();
                }
                OrderStatusRequest orderStatusRequest = new OrderStatusRequest(orderListResponse.getOrderId(), KeyWord.CANCELLED);
                orderStatusRequest.setReason(reason);
                orderViewModel.updateOrderStatus(orderStatusRequest);
                dialog.dismiss();

            });
        });

       holder.binding.rvPendingOrderDetails.setOnClickListener(v->{
           PendingOrderDirections.ActionPendingOrderFragmentToOrderDetailsFragment action = PendingOrderDirections.actionPendingOrderFragmentToOrderDetailsFragment();
           action.setOrderId(orderListResponse.getOrderId());
           Navigation.findNavController(v).navigate(action);

       });

    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerviewPendingOrderBinding binding;

        public ViewHolder(RecyclerviewPendingOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
