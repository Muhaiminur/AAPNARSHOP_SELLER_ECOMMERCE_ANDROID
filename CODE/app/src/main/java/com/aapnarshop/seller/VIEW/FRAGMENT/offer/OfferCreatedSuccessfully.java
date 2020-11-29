package com.aapnarshop.seller.VIEW.FRAGMENT.offer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.FragmentOfferCreatedSuccessfullyBinding;


public class OfferCreatedSuccessfully extends Fragment {

    FragmentOfferCreatedSuccessfullyBinding binding;
    String fromDate, toDate, discount, message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offer_created_successfully, container, false);
        fromDate = OfferCreatedSuccessfullyArgs.fromBundle(getArguments()).getOfferDate();
        toDate = OfferCreatedSuccessfullyArgs.fromBundle(getArguments()).getOfferToDate();
        discount = OfferCreatedSuccessfullyArgs.fromBundle(getArguments()).getOfferDiscount();
        message = OfferCreatedSuccessfullyArgs.fromBundle(getArguments()).getMessage();



        binding.discount.setText(discount);

        binding.offerPeriod.setText(String.format("%s To %s", fromDate, toDate));
        binding.message.setText(message);
        binding.productAddedSuccessfullyBackBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_offerCreatedSuccessfully_to_createOfferFragment));
        return binding.getRoot();
    }
}