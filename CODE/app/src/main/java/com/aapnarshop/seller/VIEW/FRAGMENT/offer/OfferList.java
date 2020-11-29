package com.aapnarshop.seller.VIEW.FRAGMENT.offer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.aapnarshop.seller.VIEW.Adapter.OfferListViewPagerAdapter;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentOfferListBinding;


public class OfferList extends Fragment {

    private static final String ARG_PARAM1 = "";
    Toolbar toolbar;
    TextView textToolHeader;
    FragmentOfferListBinding binding;
    private OfferListViewPagerAdapter adapter;

    public OfferList() {
        // Required empty public constructor
    }

    public static OfferList newInstance(String param1) {
        OfferList fragment = new OfferList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_offer_list, container, false);
        try {
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            textToolHeader.setText(getActivity().getResources().getString(R.string.offerList));
            adapter = new OfferListViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(new RunningOffer(), "Running");
            adapter.addFragment(new ExpiredOffer(), "Expired");
            adapter.addFragment(new AllOffer(), "All");

            binding.viewPager.setAdapter(adapter);
            binding.tabs.setupWithViewPager(binding.viewPager);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }
}