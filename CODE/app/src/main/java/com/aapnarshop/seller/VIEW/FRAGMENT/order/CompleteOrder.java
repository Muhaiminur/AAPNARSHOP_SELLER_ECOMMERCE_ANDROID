package com.aapnarshop.seller.VIEW.FRAGMENT.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.VIEW.Adapter.AllOrderViewPagerAdapter;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentCompleteOrderBinding;

import java.util.Optional;

public class CompleteOrder extends Fragment{

    Toolbar toolbar;
    TextView textToolHeader;
    FragmentCompleteOrderBinding binding;
    Utility utility;
    AllOrderViewPagerAdapter adapter;
    int count =0;
    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete_order, container, false);

        try {

            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            textToolHeader.setText(getActivity().getResources().getString(R.string.complete_order));
            utility = new Utility(getActivity());

            adapter = new AllOrderViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(new AllFragment(), "ALL");
            adapter.addFragment(new LastWeek(), "LAST WEEK");
            adapter.addFragment(new LastMonth(), "LAST MONTH");
            adapter.addFragment(new LastYear(), "LAST YEAR");
            adapter.addFragment(new Date(), "DATE");
            binding.viewPager.setAdapter(adapter);
            binding.tabs.setupWithViewPager(binding.viewPager);


            binding.completeOrderSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    getActivity().sendBroadcast(new Intent("com.aapnarshop.seller.VIEW.FRAGMENT.order").putExtra("orderNo",s.toString()));
                    if (s.length()>0){
                        utility.setOrderNo(s.toString());

                    }else {
                        utility.clearOrderNo();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            binding.completeOrderSort.setOnClickListener(v->{
                if (count == 0){
                    getActivity().sendBroadcast(new Intent("com.aapnarshop.seller.VIEW.FRAGMENT.order.sort").putExtra("sort","descending"));

                    utility.setOrderSort("descending");
                    binding.completeOrderSort.setImageResource(R.drawable.ic_sort_descending);
                    count=1;
                }else {
                    getActivity().sendBroadcast(new Intent("com.aapnarshop.seller.VIEW.FRAGMENT.order.sort").putExtra("sort","ascending"));
                    utility.setOrderSort("ascending");
                    binding.completeOrderSort.setImageResource(R.drawable.ic_sort);
                    count = 0;

                }
            });

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        utility.clearOrderNo();
        utility.clearSort();

    }
}