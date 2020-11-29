package com.aapnarshop.seller.VIEW.FRAGMENT.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderDetailsResponse;
import com.aapnarshop.seller.Model.GET.OrderProduct;
import com.aapnarshop.seller.Model.SEND.OrderDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OrderStatusRequest;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.OrderDetailsAdapter;
import com.aapnarshop.seller.VIEWMODEL.OrderViewModel;
import com.aapnarshop.seller.databinding.FragmentOrderDetailsBinding;
import com.bumptech.glide.Glide;

import java.util.List;


public class OrderDetails extends Fragment {


    private FragmentOrderDetailsBinding binding;
    private OrderViewModel orderViewModel;
    private String orderId;
    private Utility utility;
    private boolean isProcessingClicked = false, isOnWayClicked = false;
    private String status = "", reason = "";
    private OrderDetailsResponse orderDetailsResponse;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);
        try {
            orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            TextView textToolHeader = toolbar.findViewById(R.id.title);
            textToolHeader.setText(getActivity().getResources().getString(R.string.order_details));
            utility = new Utility(getActivity());
            orderId = OrderDetailsArgs.fromBundle(getArguments()).getOrderId();
            utility.showProgress(false, "");
            observeOrderDetails(orderId);

            observeOrderStatusResponse();

            binding.orderDetailsProcessingIv.setOnClickListener(v -> {

                if (!status.equalsIgnoreCase(KeyWord.CANCELLED)) {
                    status = KeyWord.PROCESSING;
                    binding.orderDetailsProcessingIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                    binding.orderDetailsView2.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                    binding.orderDetailsProcessingTv.setText(getString(R.string.processing));
                    binding.orderDetailsProcessingTv.setVisibility(View.VISIBLE);
                    isProcessingClicked = true;
                }
            });

            binding.orderDetailsOnthewayIv.setOnClickListener(v -> {

                if (orderDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.PROCESSING)) {
                    status = KeyWord.ON_WAY;
                    binding.orderDetailsOnthewayIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                    binding.orderDetailsView3.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                    binding.orderDetailsOnthewayTv.setText(getString(R.string.on_the_way));
                    binding.orderDetailsOnthewayTv.setVisibility(View.VISIBLE);
                    isOnWayClicked = true;
                } else {
                    if (isProcessingClicked) {
                        status = KeyWord.ON_WAY;
                        binding.orderDetailsOnthewayIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                        binding.orderDetailsView3.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                        binding.orderDetailsOnthewayTv.setText(getString(R.string.on_the_way));
                        binding.orderDetailsOnthewayTv.setVisibility(View.VISIBLE);
                        isOnWayClicked = true;
                    }
                }


            });

            binding.orderDetailsDeliveredIv.setOnClickListener(v -> {
                if (orderDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.ON_WAY)) {
                    status = KeyWord.DELIVERED;
                    binding.orderDetailsDeliveredIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                    binding.orderDetailsDeliveredTv.setText(getString(R.string.delivered));
                    binding.orderDetailsDeliveredTv.setVisibility(View.VISIBLE);
                } else {
                    if (isOnWayClicked) {
                        status = KeyWord.DELIVERED;
                        binding.orderDetailsDeliveredIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                        binding.orderDetailsDeliveredTv.setText(getString(R.string.delivered));
                        binding.orderDetailsDeliveredTv.setVisibility(View.VISIBLE);
                    }
                }


            });

            binding.orderDetailsCancelOrder.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.dialog_order_reject, null);

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
                    int id = group.getCheckedRadioButtonId();
                    RadioButton reasonButton = view.findViewById(id);
                    if (reasonButton.getText().toString().equalsIgnoreCase("other")) {
                        comment.setVisibility(View.VISIBLE);
                    } else {
                        comment.setVisibility(View.GONE);
                        reason = reasonButton.getText().toString();
                        comment.setText("");
                    }
                });
                save.setOnClickListener(v1 -> {
                    if (!comment.getText().toString().equals("") || !comment.getText().toString().isEmpty()) {
                        reason = comment.getText().toString();
                    }
                    status = KeyWord.CANCELLED;
                    OrderStatusRequest orderStatusRequest = new OrderStatusRequest(orderId, status);
                    orderStatusRequest.setReason(reason);
                    orderViewModel.updateOrderStatus(orderStatusRequest);
                    dialog.dismiss();

                });

            });

            binding.orderDetailsUpdate.setOnClickListener(v -> {
                OrderStatusRequest orderStatusRequest = new OrderStatusRequest(orderId, status);
                orderViewModel.updateOrderStatus(orderStatusRequest);


            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return binding.getRoot();
    }

    private void observeOrderStatusResponse() {
        orderViewModel.getOrderStatusResponse().observe(getActivity(), api_response -> {
            if (api_response.getCode() == 200) {
                utility.showDialog(api_response.getData().getAsString());
                if (status.equalsIgnoreCase(KeyWord.CANCELLED)) {

                    binding.orderDetailsStatus.setText(status.toLowerCase() + ":" + reason);
                    binding.orderDetailsStatus.setTextColor(getActivity().getResources().getColor(R.color.deep_red));

                } else {
                    binding.orderDetailsStatus.setText(status.toLowerCase());

                }

            } else {
                utility.showDialog(api_response.getData().getAsString());

            }
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void observeOrderDetails(String orderId) {

        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest(orderId);

        orderViewModel.getOrderDetails(orderDetailsRequest).observe(getActivity(), orderDetailsResponse -> {

            this.orderDetailsResponse = orderDetailsResponse;
            utility.hideProgress();
            Glide.with(getActivity())
                    .load(orderDetailsResponse.getProfilePicture())
                    .into(binding.orderDetailsProfileimage);
            binding.orderDetailsName.setText(String.format("Name: %s", orderDetailsResponse.getName()));
            binding.orderDetailsTime.setText(String.format("Placed on: %s", utility.getDateTimeFromMillSecond(orderDetailsResponse.getCreatedAt())));
            binding.orderDetailsMobileNo.setText(orderDetailsResponse.getPhone());
            binding.orderDetailsOrderNo.setText(String.format("Order No #%s", orderDetailsResponse.getTransactionId()));
            binding.orderDetailsPaymentType.setText(orderDetailsResponse.getPaymentType().toLowerCase());
            if (orderDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.CANCELLED)) {
                binding.orderDetailsStatus.setText(orderDetailsResponse.getStatus().toLowerCase());
                binding.orderDetailsStatus.setTextColor(getActivity().getResources().getColor(R.color.deep_red));
            } else {
                binding.orderDetailsStatus.setText(orderDetailsResponse.getStatus().toLowerCase());
            }

            binding.orderDetailsAddress.setText(orderDetailsResponse.getAddress());
            binding.orderDetailsSubTotal.setText(orderDetailsResponse.getSubTotal());
            binding.orderDetailsDeliveryCharge.setText(orderDetailsResponse.getDeliveryCharge());
            binding.orderDetailsDiscount.setText(orderDetailsResponse.getDiscount());
            binding.orderDetailsTotal.setText(orderDetailsResponse.getTotal());

            initAdapter(orderDetailsResponse.getOrderProducts());

            if (orderDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.PLACED)) {
                binding.orderDetailsPlacedIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView1.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsPlacedTv.setText(getString(R.string.placed));
                binding.orderDetailsPlacedTv.setVisibility(View.VISIBLE);
                binding.orderDetailsPlacedIv.setEnabled(false);
            } else if (orderDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.PROCESSING)) {
                binding.orderDetailsPlacedIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView1.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsPlacedTv.setText(getString(R.string.placed));
                binding.orderDetailsPlacedTv.setVisibility(View.VISIBLE);
                binding.orderDetailsPlacedIv.setEnabled(false);

                binding.orderDetailsProcessingIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView2.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsProcessingTv.setText(getString(R.string.processing));
                binding.orderDetailsProcessingTv.setVisibility(View.VISIBLE);
                binding.orderDetailsProcessingIv.setEnabled(false);
            } else if (orderDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.ON_WAY)) {
                binding.orderDetailsPlacedIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView1.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsPlacedTv.setText(getString(R.string.placed));
                binding.orderDetailsPlacedTv.setVisibility(View.VISIBLE);
                binding.orderDetailsPlacedIv.setEnabled(false);

                binding.orderDetailsProcessingIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView2.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsProcessingTv.setText(getString(R.string.processing));
                binding.orderDetailsProcessingTv.setVisibility(View.VISIBLE);
                binding.orderDetailsProcessingIv.setEnabled(false);

                binding.orderDetailsOnthewayIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView3.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsOnthewayTv.setText(getString(R.string.on_the_way));
                binding.orderDetailsOnthewayTv.setVisibility(View.VISIBLE);
                binding.orderDetailsOnthewayIv.setEnabled(false);
            } else if (orderDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.DELIVERED)) {
                binding.orderDetailsPlacedIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView1.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsPlacedTv.setText(getString(R.string.placed));
                binding.orderDetailsPlacedTv.setVisibility(View.VISIBLE);
                binding.orderDetailsPlacedIv.setEnabled(false);

                binding.orderDetailsProcessingIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView2.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsProcessingTv.setText(getString(R.string.processing));
                binding.orderDetailsProcessingTv.setVisibility(View.VISIBLE);
                binding.orderDetailsProcessingIv.setEnabled(false);

                binding.orderDetailsOnthewayIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsView3.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                binding.orderDetailsOnthewayTv.setText(getString(R.string.on_the_way));
                binding.orderDetailsOnthewayTv.setVisibility(View.VISIBLE);
                binding.orderDetailsOnthewayIv.setEnabled(false);

                binding.orderDetailsDeliveredIv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_tick));
                binding.orderDetailsDeliveredTv.setText(getString(R.string.delivered));
                binding.orderDetailsDeliveredTv.setVisibility(View.VISIBLE);
                binding.orderDetailsDeliveredIv.setEnabled(false);

            }

        });
    }

    private void initAdapter(List<OrderProduct> orderProductList) {
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(getActivity(), orderProductList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvOrderDetails.setLayoutManager(mLayoutManager);
        binding.rvOrderDetails.setAdapter(adapter);
    }
}