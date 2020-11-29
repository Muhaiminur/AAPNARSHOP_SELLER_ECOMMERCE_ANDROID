package com.aapnarshop.seller.VIEW.FRAGMENT.product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentProductAddedSuccessfullyBinding;

public class ProductAddedSuccessfully extends Fragment {

    Toolbar toolbar;

    public ProductAddedSuccessfully() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProductAddedSuccessfullyBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_added_successfully, container, false);
        try{
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setVisibility(View.GONE);

            binding.productAddedSuccessfullyBackBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_productAddedSuccessfullyFragment_to_addProductFragment));
            binding.productAddedSuccessfullyDashboard.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_productAddedSuccessfullyFragment_to_dashboardFragment));
            binding.productAddedSuccessfullyAddMore.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_productAddedSuccessfullyFragment_to_addProductFragment2));
        }
        catch(Exception e){
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }


    @Override
    public void onPause() {
        super.onPause();
        toolbar.setVisibility(View.VISIBLE);

    }
}