package com.aapnarshop.seller.VIEW.FRAGMENT.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.aapnarshop.seller.VIEW.Adapter.CompleteOrderAdapter;
import com.aapnarshop.seller.VIEWMODEL.OrderViewModel;
import com.aapnarshop.seller.databinding.FragmentLastMonthBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LastMonth extends Fragment {

    FragmentLastMonthBinding binding;
    OrderViewModel orderViewModel;
    List<OrderListResponse> orderListResponseList = new ArrayList<>();

    Utility utility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_last_month, container, false);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        utility = new Utility(getActivity());
        binding.lastMonthSwipeRefresh.setRefreshing(true);
        observeOrderList();
        binding.lastMonthSwipeRefresh.setOnRefreshListener(this::observeOrderList);
        return binding.getRoot();
    }

    private void observeOrderList() {

        OrderListRequest orderListRequest = new OrderListRequest(KeyWord.TYPE_ALL, KeyWord.KEYWORD_MONTH, "", "");
        orderViewModel.getOrderList(orderListRequest).observe(getActivity(), orderListResponses -> {

            orderListResponseList.clear();
            if (orderListResponses.size() > 0) {
                orderListResponseList.addAll(orderListResponses);
                binding.lastMonthSwipeRefresh.setVisibility(View.VISIBLE);
                binding.noOrderFound.setVisibility(View.GONE);
                // Here managed filtered order list and server order list.Shared preference use here.
                if (utility.getSortedOrder().equalsIgnoreCase("descending")) {
                    Collections.sort(orderListResponseList, (o1, o2) -> o2.getCreatedAt().compareToIgnoreCase(o1.getCreatedAt()));

                    if (!utility.getOrderNo().isEmpty()) {
                        initAdapter(utility.getFilteredOrderList(utility.getOrderNo(), orderListResponseList));

                    } else {
                        initAdapter(orderListResponseList);

                    }
                } else if (utility.getSortedOrder().equalsIgnoreCase("ascending")) {
                    Collections.sort(orderListResponseList, (o1, o2) -> o1.getCreatedAt().compareToIgnoreCase(o2.getCreatedAt()));

                    if (!utility.getOrderNo().isEmpty()) {
                        initAdapter(utility.getFilteredOrderList(utility.getOrderNo(), orderListResponseList));

                    } else {
                        initAdapter(orderListResponseList);

                    }
                } else {
                    if (!utility.getOrderNo().isEmpty()) {

                        if (utility.getFilteredOrderList(utility.getOrderNo(), orderListResponseList).size() > 0) {
                            initAdapter(utility.getFilteredOrderList(utility.getOrderNo(), orderListResponseList));

                        } else {
                            binding.lastMonthSwipeRefresh.setVisibility(View.GONE);
                            binding.noOrderFound.setVisibility(View.VISIBLE);
                        }

                    } else {
                        initAdapter(orderListResponses);

                    }
                }

                binding.lastMonthSwipeRefresh.setRefreshing(false);
            } else {
                binding.lastMonthSwipeRefresh.setVisibility(View.GONE);
                binding.noOrderFound.setVisibility(View.VISIBLE);
            }
        });

    }

    private void initAdapter(List<OrderListResponse> orderList) {
        CompleteOrderAdapter adapter = new CompleteOrderAdapter(getActivity(), orderList, utility);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvLastMonth.setLayoutManager(mLayoutManager);
        binding.rvLastMonth.setAdapter(adapter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            initAdapter(utility.getFilteredOrderList(intent.getStringExtra("orderNo"), orderListResponseList));
        }
    };
    BroadcastReceiver sortBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            if (intent.getStringExtra("sort").equalsIgnoreCase("descending")) {
                Collections.sort(orderListResponseList, (o1, o2) -> o2.getCreatedAt().compareToIgnoreCase(o1.getCreatedAt()));

                if (!utility.getOrderNo().isEmpty()) {
                    initAdapter(utility.getFilteredOrderList(utility.getOrderNo(), orderListResponseList));

                } else {
                    initAdapter(orderListResponseList);

                }
            } else if (intent.getStringExtra("sort").equalsIgnoreCase("ascending")) {
                Collections.sort(orderListResponseList, (o1, o2) -> o1.getCreatedAt().compareToIgnoreCase(o2.getCreatedAt()));

                if (!utility.getOrderNo().isEmpty()) {
                    initAdapter(utility.getFilteredOrderList(utility.getOrderNo(), orderListResponseList));

                } else {
                    initAdapter(orderListResponseList);

                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("com.aapnarshop.seller.VIEW.FRAGMENT.order");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
        IntentFilter sortingIntentFilter = new IntentFilter("com.aapnarshop.seller.VIEW.FRAGMENT.order.sort");
        getActivity().registerReceiver(sortBroadcastReceiver, sortingIntentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(sortBroadcastReceiver);

    }
}