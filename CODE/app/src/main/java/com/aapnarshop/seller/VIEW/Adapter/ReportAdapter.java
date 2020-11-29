package com.aapnarshop.seller.VIEW.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.RecyclerviewReportBinding;
import com.aapnarshop.seller.Model.Report;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context context;
    private List<Report> reportList;

    public ReportAdapter(Context context,List<Report> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerviewReportBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.recyclerview_report,parent,false);
        return new ReportAdapter.ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            Report report = reportList.get(position);

            holder.binding.recyclerViewReportTitle.setText(report.getTitle());
            holder.binding.recyclerviewReportTotalProduct.setText(String.valueOf(report.getTotalNumber()));
            holder.binding.recyclerviewReportAmount.setText(report.getTotalPrice()+" TK");
        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }




    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerviewReportBinding binding;
        public ViewHolder(RecyclerviewReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
