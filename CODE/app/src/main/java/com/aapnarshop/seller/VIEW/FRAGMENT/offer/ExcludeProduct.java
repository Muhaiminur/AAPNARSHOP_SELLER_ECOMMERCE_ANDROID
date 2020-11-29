package com.aapnarshop.seller.VIEW.FRAGMENT.offer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.aapnarshop.seller.R;


public class ExcludeProduct extends Fragment {


    public ExcludeProduct() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exclude_product, container, false);
    }
}