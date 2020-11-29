package com.aapnarshop.seller.VIEW.FRAGMENT.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderListResponse;
import com.aapnarshop.seller.Model.SEND.OrderListRequest;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.PendingOrderAdapter;
import com.aapnarshop.seller.VIEWMODEL.OrderViewModel;
import com.aapnarshop.seller.databinding.FragmentPendingOrderBinding;

import java.util.List;


public class PendingOrder extends Fragment {

    FragmentPendingOrderBinding binding;
    OrderViewModel orderViewModel;
    Utility utility;

    public PendingOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending_order, container, false);

        try {
            orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            TextView textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            textToolHeader.setText(getActivity().getResources().getString(R.string.pending_order));
            utility = new Utility(getActivity());
            binding.pendingOrderSwipeRefresh.setVisibility(View.VISIBLE);
            binding.pendingOrderSwipeRefresh.setRefreshing(true);

            observeOrderList();
            orderViewModel.getOrderStatusResponse().observe(getActivity(), api_response -> {
                if (api_response.getCode() == 200) {
                    observeOrderList();
                }
            });

            binding.pendingOrderSwipeRefresh.setOnRefreshListener(this::observeOrderList);


        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return binding.getRoot();
    }

    private void observeOrderList() {

        OrderListRequest orderListRequest = new OrderListRequest(KeyWord.TYPE_PENDING, KeyWord.TYPE_ALL, "", "");
        orderViewModel.getOrderList(orderListRequest).observe(getActivity(), orderListResponses -> {

            if (orderListResponses.size() > 0) {
                initAdapter(orderListResponses);
                binding.pendingOrderSwipeRefresh.setRefreshing(false);
                binding.pendingOrderNoOrderFound.setVisibility(View.GONE);
            } else {
                binding.pendingOrderSwipeRefresh.setVisibility(View.GONE);
                binding.pendingOrderNoOrderFound.setVisibility(View.VISIBLE);
                binding.pendingOrderSwipeRefresh.setRefreshing(false);
            }
        });

    }

    private void initAdapter(List<OrderListResponse> orderList) {
        PendingOrderAdapter adapter = new PendingOrderAdapter(getActivity(), orderList, orderViewModel, utility);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.pendingOrderRv.setLayoutManager(mLayoutManager);
        binding.pendingOrderRv.setAdapter(adapter);
    }

}