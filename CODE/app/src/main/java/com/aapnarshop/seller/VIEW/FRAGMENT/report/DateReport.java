package com.aapnarshop.seller.VIEW.FRAGMENT.report;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.VIEW.Adapter.ReportAdapter;
import com.aapnarshop.seller.Library.CalendarView;
import com.aapnarshop.seller.Model.Report;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentDateBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class DateReport extends Fragment {

    FragmentDateBinding binding;
    SimpleDateFormat monthFormat, dayFormat, dateFormat, yearFormat;
    String month, year, day_date;
    private List<Report> reportList;

    public DateReport() {
        // Required empty public constructor
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_date, container, false);
        try {


            monthFormat = new SimpleDateFormat("MMM");
            yearFormat = new SimpleDateFormat("yyyy");
            dayFormat = new SimpleDateFormat("EEEE");
            dateFormat = new SimpleDateFormat("dd");

            reportList = new ArrayList<>();

            reportList.add(new Report("Product Add", 10, 200000));
            reportList.add(new Report("Product Sell", 5, 1000));
            reportList.add(new Report("Complete Order", 10, 5000));
            reportList.add(new Report("Cancel Order", 2, 500));


            ReportAdapter adapter = new ReportAdapter(getActivity(), reportList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            binding.dateReportRecyclerview.setLayoutManager(mLayoutManager);
            binding.dateReportRecyclerview.setAdapter(adapter);


            getSevenDaysAgoDate();

            getTodaysDate();

            clickEvent();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
        return binding.getRoot();
    }


    private void getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

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
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, -6);
        Date sevenDaysBeforeDate = cal.getTime();

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

            openCalenderDialog(binding.dateFromDateDay, binding.dateDate, binding.dateFromDateMonthYear);

        });

        binding.dateToDate.setOnClickListener(v -> {

            openCalenderDialog(binding.dateToDateDay, binding.dateToDateDate, binding.dateToDateMonthYear);

        });
    }


    //Open calender dialog
    private void openCalenderDialog(TextView completeOrderDay, TextView completeOrderDate, TextView completeOrderMonthYear) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.calendar_view);


        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        CalendarView cv = dialog.findViewById(R.id.calendar_view1);
        ImageView imageView = dialog.findViewById(R.id.control_calender_cancel);

        cv.updateCalendar(events);


        imageView.setOnClickListener(view -> {
            dialog.dismiss();
        });


        // assign event handler
        cv.setEventHandler(date -> {
            // show returned day
            DateFormat df = SimpleDateFormat.getDateInstance();


            completeOrderDay.setText(dayFormat.format(date));
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
}