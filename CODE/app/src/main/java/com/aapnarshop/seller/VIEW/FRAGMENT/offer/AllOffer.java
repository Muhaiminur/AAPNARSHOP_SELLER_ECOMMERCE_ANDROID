package com.aapnarshop.seller.VIEW.FRAGMENT.offer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Model.GET.OfferListResponse;
import com.aapnarshop.seller.Model.GET.OfferTypeResponse;
import com.aapnarshop.seller.Model.SEND.OfferDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OfferListRequest;
import com.aapnarshop.seller.Model.SEND.OfferStatus;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.GenericAdapter;
import com.aapnarshop.seller.VIEWMODEL.OfferViewModel;
import com.aapnarshop.seller.databinding.FragmentAllOfferBinding;

import java.util.List;

public class AllOffer extends Fragment {

    FragmentAllOfferBinding binding;
    private OfferViewModel offerViewModel;
    boolean isActive;
    String offerId;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_offer, container, false);
        offerViewModel = new ViewModelProvider(this).get(OfferViewModel.class);

        offerViewModel.getOfferTypeList().observe(getActivity(), this::showOfferType);

        offerViewModel.getOfferDetailsResponse().observe(getActivity(),offerDetailsResponse -> {

            offerId = offerDetailsResponse.getId();

            if (offerDetailsResponse.getOfferTypeResponse().getOfferTypeTitle().equalsIgnoreCase("total_bill")) {

                binding.allOfferMessage.setText("This offer will be apply upon " + offerDetailsResponse.getOfferTypeResponse().getOfferTypeTitle().toLowerCase() + ".Buyer will get " + offerDetailsResponse.getAmount() + "% discount & max discount will be " + offerDetailsResponse.getMaxAmount() + " Tk.");

            } else if (offerDetailsResponse.getOfferTypeResponse().getOfferTypeTitle().equalsIgnoreCase("free_delivery")) {
                binding.allOfferMessage.setText("To get " + offerDetailsResponse.getOfferTypeResponse().getOfferTypeTitle().toLowerCase() + " offer buyer have to buy minimum " + offerDetailsResponse.getMinAmount() + " Tk. product");

            } else if (offerDetailsResponse.getOfferTypeResponse().getOfferTypeTitle().equalsIgnoreCase("product_based")) {
                binding.allOfferMessage.setText("This offer will be apply for " + offerDetailsResponse.getOfferTypeResponse().getOfferTypeTitle().toLowerCase() + ".Buyer will get " + offerDetailsResponse.getAmount() + "% discount");
            }
            binding.allOfferValidity.setText("Valid Until "+CreateOffer.convertDate(offerDetailsResponse.getStartTime(),"dd/MM/yyyy")+" to "+ CreateOffer.convertDate(offerDetailsResponse.getEndTime(),"dd/MM/yyyy"));


            if (offerDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.ACTIVE)){
                isActive = true;
                binding.allOfferStop.setText("Stop this offer");
            }else if (offerDetailsResponse.getStatus().equalsIgnoreCase(KeyWord.INACTIVE)){
                isActive = false;
                binding.allOfferStop.setText("Start this offer");

            }
        });

        binding.allOfferStop.setOnClickListener(v -> {

            if (!isActive){
                OfferStatus offerStatus = new OfferStatus(offerId,KeyWord.ACTIVE);
                offerViewModel.updateOfferStatus(offerStatus);
                binding.allOfferStop.setText("Stop this offer");
                isActive = true;

            }else{
                OfferStatus offerStatus = new OfferStatus(offerId,KeyWord.INACTIVE);
                offerViewModel.updateOfferStatus(offerStatus);
                binding.allOfferStop.setText("Start this offer");
                isActive = false;
            }

        });
        return binding.getRoot();
    }
    private void showOfferType(List<OfferTypeResponse> offerTypeResponseList) {
        GenericAdapter<OfferTypeResponse> adapter = new GenericAdapter<OfferTypeResponse>(getActivity(),offerTypeResponseList) {
            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                OfferTypeResponse model = getItem(position);


                View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                if (model != null) {
                    label.setText(model.getOfferTypeTitle());
                }
                return spinnerRow;
            }
        };

        binding.allOfferOfferTypeDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.allOfferOfferTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                OfferListRequest offerListRequest = new OfferListRequest(KeyWord.TIMELINE_ALL,offerTypeResponseList.get(position).getOfferTypeId());
                offerViewModel.getOfferList(offerListRequest).observe(getActivity(),offerListResponses -> {

                    showOfferList(offerListResponses);
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

  

    private void showOfferList(List<OfferListResponse> offerListResponses) {
        GenericAdapter<OfferListResponse> adapter = new GenericAdapter<OfferListResponse>(getActivity(),offerListResponses) {
            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                OfferListResponse model = getItem(position);


                View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                label.setText(model.getOfferListTitle());
                return spinnerRow;
            }
        };

        binding.allOfferOfferNameDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.allOfferOfferNameDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OfferDetailsRequest offerDetailsRequest = new OfferDetailsRequest(offerListResponses.get(position).getOfferListId());
                offerViewModel.sendOfferDetailsRequest(offerDetailsRequest);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}