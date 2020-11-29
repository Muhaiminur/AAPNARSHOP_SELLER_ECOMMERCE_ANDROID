package com.aapnarshop.seller.VIEW.FRAGMENT.order;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderProduct;
import com.aapnarshop.seller.Model.SEND.OrderDetailsRequest;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.OrderDetailsAdapter;
import com.aapnarshop.seller.VIEWMODEL.OrderViewModel;
import com.aapnarshop.seller.databinding.FragmentCompleteOrderDetailsBinding;
import com.bumptech.glide.Glide;

import java.util.List;


public class CompleteOrderDetailsFragment extends Fragment {

    FragmentCompleteOrderDetailsBinding binding;
    OrderViewModel orderViewModel;
    Utility utility;
    String orderId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_complete_order_details, container, false);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView textToolHeader = toolbar.findViewById(R.id.title);
        textToolHeader.setText(getActivity().getResources().getString(R.string.order_details));
        utility = new Utility(getActivity());
        orderId = CompleteOrderDetailsFragmentArgs.fromBundle(getArguments()).getCompleteOrderId();

        observeOrderDetails(orderId);
        return binding.getRoot();
    }

    private void observeOrderDetails(String orderId) {

        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest(orderId);

        orderViewModel.getOrderDetails(orderDetailsRequest).observe(getActivity(), orderDetailsResponse -> {

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


        });
    }

    private void initAdapter(List<OrderProduct> orderProductList) {
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(getActivity(), orderProductList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvOrderDetails.setLayoutManager(mLayoutManager);
        binding.rvOrderDetails.setAdapter(adapter);
    }
}