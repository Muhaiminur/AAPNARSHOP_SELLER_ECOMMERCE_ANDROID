package com.aapnarshop.seller.VIEW.FRAGMENT.report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.VIEW.Adapter.ReportAdapter;
import com.aapnarshop.seller.Model.Report;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentWeeklyBinding;

import java.util.ArrayList;
import java.util.List;


public class WeeklyReport extends Fragment {

    FragmentWeeklyBinding binding;
    ReportAdapter adapter;
    List<Report> reportList;



    public WeeklyReport() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_weekly, container, false);
        try{
            reportList = new ArrayList<>();

            reportList.add(new Report("Product Add",10,200000));
            reportList.add(new Report("Product Sell",5,1000));
            reportList.add(new Report("Complete Order",10,5000));
            reportList.add(new Report("Cancel Order",2,500));



            adapter = new ReportAdapter(getActivity(),reportList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            binding.weeklyReportRecyclerview.setLayoutManager(mLayoutManager);
            binding.weeklyReportRecyclerview.setAdapter(adapter);
        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return  binding.getRoot();
    }
}