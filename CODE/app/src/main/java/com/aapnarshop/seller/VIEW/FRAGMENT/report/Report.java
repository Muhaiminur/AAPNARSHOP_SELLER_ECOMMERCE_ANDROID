package com.aapnarshop.seller.VIEW.FRAGMENT.report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.aapnarshop.seller.VIEW.Adapter.ReportViewPagerAdapter;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.ReportDropdown;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentReportBinding;

import java.util.ArrayList;
import java.util.List;

public class Report extends Fragment {

    private static final String ARG_PARAM1 = "";
    Toolbar toolbar;
    TextView textToolHeader;
    private ReportViewPagerAdapter adapter;
    FragmentReportBinding binding;
    private Utility utility;
    private String language;

    private List<ReportDropdown> reportDropdownList;

    public Report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false);
        try {
            utility = new Utility(getActivity());
            language = utility.getLangauge();
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            textToolHeader.setText(language.equals(KeyWord.BANGLA)?KeyWord.REPORT_BN:getActivity().getResources().getString(R.string.report));
            adapter = new ReportViewPagerAdapter(getActivity().getSupportFragmentManager());


            // Change Language --------------------start-------------
            binding.reportCategoryTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.category_bn):getString(R.string.category));
            binding.reportCategoryDropdown.setHint(language.equals(KeyWord.BANGLA)?getString(R.string.category_bn):getString(R.string.category));
            binding.reportProductNameTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.product_name_bn):getString(R.string.product_name));
            binding.reportProductNameDropdown.setHint(language.equals(KeyWord.BANGLA)?getString(R.string.product_name_bn):getString(R.string.product_name));
            binding.reportProductTv.setText(language.equals(KeyWord.BANGLA)?getString(R.string.product_bn):getString(R.string.product));
            binding.reportProductDropdown.setHint(language.equals(KeyWord.BANGLA)?getString(R.string.product_bn):getString(R.string.product));


            adapter.addFragment(new AllReport(), language.equals(KeyWord.BANGLA)?getString(R.string.all_bn):getString(R.string.all));
            adapter.addFragment(new WeeklyReport(), language.equals(KeyWord.BANGLA)?getString(R.string.weekly_bn):getString(R.string.weekly));
            adapter.addFragment(new MonthlyReport(), language.equals(KeyWord.BANGLA)?getString(R.string.monthly_bn):getString(R.string.monthly));
            adapter.addFragment(new YearlyReport(), language.equals(KeyWord.BANGLA)?getString(R.string.yearly_bn):getString(R.string.yearly));
            adapter.addFragment(new DateReport(), language.equals(KeyWord.BANGLA)?getString(R.string.date_bn):getString(R.string.date));

            //--------End-----------------

            binding.viewPager.setAdapter(adapter);
            binding.tabs.setupWithViewPager(binding.viewPager);

            reportDropdownList = new ArrayList<>();

            reportDropdownList.add(new ReportDropdown("Select Category"));
            reportDropdownList.add(new ReportDropdown("Television"));
            reportDropdownList.add(new ReportDropdown("Television"));
            reportDropdownList.add(new ReportDropdown("Television"));
            reportDropdownList.add(new ReportDropdown("Mobile"));
            reportDropdownList.add(new ReportDropdown("Mobile xiaomi"));
            reportDropdownList.add(new ReportDropdown("Camera"));





        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }


}