package com.aapnarshop.seller.VIEW.FRAGMENT.order;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.CalendarView;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderListResponse;
import com.aapnarshop.seller.Model.SEND.OrderListRequest;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.CompleteOrderAdapter;
import com.aapnarshop.seller.VIEWMODEL.OrderViewModel;
import com.aapnarshop.seller.databinding.FragmentDate2Binding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Date extends Fragment {

    private FragmentDate2Binding binding;
    private SimpleDateFormat monthFormat, dayFormat, dateFormat, yearFormat;
    private String month, year, day_date;
    private OrderViewModel orderViewModel;
    private Utility utility;
    private final List<OrderListResponse> orderListResponseList = new ArrayList<>();
    private String fromDate, toDate;


    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_date2, container, false);
        try {
            orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
            monthFormat = new SimpleDateFormat("MMM");
            yearFormat = new SimpleDateFormat("yyyy");
            dayFormat = new SimpleDateFormat("EEEE");
            dateFormat = new SimpleDateFormat("dd");
            utility = new Utility(getActivity());
            binding.dateSwipeRefresh.setRefreshing(true);
            getSevenDaysAgoDate();
            getTodayDate();
            observeOrderList(fromDate, toDate);
            clickEvent();
            binding.dateSwipeRefresh.setOnRefreshListener(() -> {
                observeOrderList(fromDate, toDate);

            });

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return binding.getRoot();
    }

    private void observeOrderList(String fromDate, String toDate) {
        OrderListRequest orderListRequest = new OrderListRequest(KeyWord.TYPE_ALL, KeyWord.KEYWORD_DATE, fromDate, toDate);
        orderViewModel.getOrderList(orderListRequest).observe(getActivity(), orderListResponses -> {

            orderListResponseList.clear();
            if (orderListResponses.size() > 0) {
                orderListResponseList.addAll(orderListResponses);
                binding.dateSwipeRefresh.setVisibility(View.VISIBLE);
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
                            binding.dateSwipeRefresh.setVisibility(View.GONE);
                            binding.noOrderFound.setVisibility(View.VISIBLE);
                        }

                    } else {
                        initAdapter(orderListResponses);

                    }
                }
                binding.dateSwipeRefresh.setRefreshing(false);
            } else {
                binding.dateSwipeRefresh.setVisibility(View.GONE);
                binding.noOrderFound.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initAdapter(List<OrderListResponse> orderList) {
        CompleteOrderAdapter adapter = new CompleteOrderAdapter(getActivity(), orderList, utility);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvDate.setLayoutManager(mLayoutManager);
        binding.rvDate.setAdapter(adapter);
    }

    private void getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date today = calendar.getTime();

        toDate = String.valueOf(today.getTime());
        month = monthFormat.format(today);
        year = yearFormat.format(today);
        binding.dateToDateDay.setText(dayFormat.format(today));
        day_date = dateFormat.format(today);
        binding.dateToDateDate.setText(day_date);
        switch (day_date) {
            case "01":
                binding.dateToDateMonthYear.setText(String.format("ST %s %s", month, year));
                break;
            case "02":
                binding.dateToDateMonthYear.setText(String.format("ND %s %s", month, year));
                break;
            case "03":
                binding.dateToDateMonthYear.setText(String.format("RD %s %s", month, year));
                break;
            default:
                binding.dateToDateMonthYear.setText(String.format("TH %s %s", month, year));
                break;
        }
    }


    // get 7 days ago date
    private void getSevenDaysAgoDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DAY_OF_YEAR, -6);
        java.util.Date sevenDaysBeforeDate = cal.getTime();

        fromDate = String.valueOf(sevenDaysBeforeDate.getTime());
        month = monthFormat.format(sevenDaysBeforeDate);
        year = yearFormat.format(sevenDaysBeforeDate);
        binding.dateFromDateDay.setText(dayFormat.format(sevenDaysBeforeDate));
        day_date = dateFormat.format(sevenDaysBeforeDate);
        binding.dateDate.setText(day_date);
        switch (day_date) {
            case "01":
                binding.dateFromDateMonthYear.setText(String.format("ST %s %s", month, year));
                break;
            case "02":
                binding.dateFromDateMonthYear.setText(String.format("ND %s %s", month, year));
                break;
            case "03":
                binding.dateFromDateMonthYear.setText(String.format("RD %s %s", month, year));
                break;
            default:
                binding.dateFromDateMonthYear.setText(String.format("TH %s %s", month, year));
                break;
        }
    }

    //All type of click event
    private void clickEvent() {
        binding.dateFromDate.setOnClickListener(v -> {

            openCalenderDialog(binding.dateFromDateDay, binding.dateDate, binding.dateFromDateMonthYear, "fromDate");

        });

        binding.dateToDate.setOnClickListener(v -> {

            openCalenderDialog(binding.dateToDateDay, binding.dateToDateDate, binding.dateToDateMonthYear, "toDate");

        });
    }

    //Open calender dialog
    private void openCalenderDialog(TextView completeOrderDay, TextView completeOrderDate, TextView completeOrderMonthYear, String from_Date) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.calendar_view);


        HashSet<java.util.Date> events = new HashSet<>();
        events.add(new java.util.Date());
        CalendarView cv = dialog.findViewById(R.id.calendar_view1);
        ImageView imageView = dialog.findViewById(R.id.control_calender_cancel);

        cv.updateCalendar(events);


        imageView.setOnClickListener(view -> {
            dialog.dismiss();
        });


        // assign event handler
        cv.setEventHandler(date -> {
            // show returned day

            if (from_Date.equalsIgnoreCase("fromDate")) {

                completeOrderDay.setText(dayFormat.format(date));
                java.util.Date date1 = fromDateStartHour(date);
                fromDate = String.valueOf(date1.getTime());
                observeOrderList(fromDate, toDate);

            } else if (from_Date.equalsIgnoreCase("toDate")) {
                completeOrderDay.setText(dayFormat.format(date));
                java.util.Date date1 = toDateLastHour(date);
                toDate = String.valueOf(date1.getTime());
                observeOrderList(fromDate, toDate);
            }

            day_date = dateFormat.format(date);
            completeOrderDate.setText(day_date);
            month = monthFormat.format(date);
            year = yearFormat.format(date);

            switch (day_date) {
                case "01":
                    completeOrderMonthYear.setText(String.format("ST %s %s", month, year));
                    break;
                case "02":
                    completeOrderMonthYear.setText(String.format("ND %s %s", month, year));
                    break;
                case "03":
                    completeOrderMonthYear.setText(String.format("RD %s %s", month, year));
                    break;
                default:
                    completeOrderMonthYear.setText(String.format("TH %s %s", month, year));
                    break;
            }

            dialog.dismiss();
        });


        dialog.show();
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



    public static java.util.Date toDateLastHour(java.util.Date date1) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTime();
    }

    public static java.util.Date fromDateStartHour(java.util.Date date1) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}