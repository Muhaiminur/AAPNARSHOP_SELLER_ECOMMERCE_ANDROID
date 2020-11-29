package com.aapnarshop.seller.VIEW.FRAGMENT.offer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aapnarshop.seller.Library.CalendarView;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OfferCategory;
import com.aapnarshop.seller.Model.GET.OfferDetailsResponse;
import com.aapnarshop.seller.Model.GET.OfferListResponse;
import com.aapnarshop.seller.Model.GET.OfferTypeResponse;
import com.aapnarshop.seller.Model.SEND.OfferCategoryRequest;
import com.aapnarshop.seller.Model.SEND.OfferCreate;
import com.aapnarshop.seller.Model.SEND.OfferDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OfferListRequest;
import com.aapnarshop.seller.Model.SEND.OfferStatus;
import com.aapnarshop.seller.Model.SEND.OfferUpdate;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.GenericAdapter;
import com.aapnarshop.seller.VIEWMODEL.OfferViewModel;
import com.aapnarshop.seller.databinding.FragmentCreateOfferBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.aapnarshop.seller.VIEW.ACTIVITY.HomePage.navController;
import static com.aapnarshop.seller.VIEW.FRAGMENT.order.Date.fromDateStartHour;
import static com.aapnarshop.seller.VIEW.FRAGMENT.order.Date.toDateLastHour;

public class CreateOffer extends Fragment {

    Toolbar toolbar;
    TextView textToolHeader, switchTextView;
    SwitchCompat offerSwitch;
    LinearLayout switchLayout;
    FragmentCreateOfferBinding binding;
    SimpleDateFormat monthFormat, dateFormat, yearFormat;
    OfferViewModel offerViewModel;
    private final List<OfferListResponse> offerList = new ArrayList<>();
    private final List<OfferCategory> offerCategoryList = new ArrayList<>();
    String language;
    Utility utility;
    String offerId="", offerListId, offerCategoryId, offerCategoryName, offerTypeId, selectCreateOfferId, amount;
    String offerName = "", amountOfDiscount = "", minimumBuy = "", maxDiscount = "", generatedCode = "", offerPeriodFrom = "", offErPeriodTo = "";
    OfferDetailsResponse offerDetailsResponse;

    @SuppressLint("SimpleDateFormat")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_offer, container, false);
        offerViewModel = new ViewModelProvider(this).get(OfferViewModel.class);

        try {
            utility = new Utility(getActivity());
            language = utility.getLangauge();
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            switchLayout = toolbar.findViewById(R.id.switch_layout2);
            switchTextView = toolbar.findViewById(R.id.switch_textView2);
            offerSwitch = toolbar.findViewById(R.id.offer_switch);

            textToolHeader.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.create_offer_bn) : getActivity().getResources().getString(R.string.create_offer));
            switchLayout.setVisibility(View.VISIBLE);
            switchTextView.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.start_stop_bn) : getString(R.string.start_stop));

            monthFormat = new SimpleDateFormat("MM");
            yearFormat = new SimpleDateFormat("yyyy");
            dateFormat = new SimpleDateFormat("dd");
            utility.showProgress(false, "");

            offerViewModel.getOfferTypeList().observe(getActivity(), this::showOfferType);

           /*offerIdFromOfferList = CreateOfferArgs.fromBundle(getArguments()).getOfferId();
           // offerDetailsResponse = CreateOfferArgs.fromBundle(getArguments()).getCusomeModel();
            Toast.makeText(getActivity(), offerIdFromOfferList, Toast.LENGTH_SHORT).show();

            if (offerIdFromOfferList.equalsIgnoreCase("BLANK") || offerIdFromOfferList.equals("")) {
                observeOfferDetails();

            } else {
                utility.hideProgress();
                binding.createOfferAmountOfDiscount.setText(offerDetailsResponse.getAmount());
                binding.createOfferOfferName.setText(offerDetailsResponse.getOfferName());
                binding.createOfferMinimumBuy.setText(offerDetailsResponse.getMinAmount());
                binding.createOfferMaxDiscountEt.setText(offerDetailsResponse.getMaxAmount());

            }*/
            observeOfferDetails();


            onClickEvents();
            offerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {
                    OfferStatus offerStatus = new OfferStatus(offerId, KeyWord.ACTIVE);
                    offerViewModel.updateOfferStatus(offerStatus);
                } else {
                    OfferStatus offerStatus = new OfferStatus(offerId, KeyWord.INACTIVE);
                    offerViewModel.updateOfferStatus(offerStatus);
                }


            });

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }

    private void observeOfferDetails() {
        offerViewModel.getOfferDetailsResponse().observe(getActivity(), offerDetailsResponse -> {
            utility.hideProgress();
            binding.createOfferAmountOfDiscount.setText(offerDetailsResponse.getAmount());
            binding.createOfferOfferName.setText(offerDetailsResponse.getOfferName());
            binding.createOfferMinimumBuy.setText(offerDetailsResponse.getMinAmount());
            binding.createOfferMaxDiscountEt.setText(offerDetailsResponse.getMaxAmount());
            binding.createOfferOfferNameMinimumBuy.setText(offerDetailsResponse.getOfferName());
            binding.createOfferGenerateOfferEditText.setText(offerDetailsResponse.getGeneratedCode());
            binding.createOfferBuy.setText(offerDetailsResponse.getMinAmount());
            binding.createOfferGet.setText(offerDetailsResponse.getAmount());
            binding.createOfferBuyGetOfferName.setText(offerDetailsResponse.getOfferName());
            binding.createOfferOfferPeriodFrom.setText(convertDate(offerDetailsResponse.getStartTime(), "dd/MM/yyyy"));
            binding.createOfferOfferPeriodTo.setText(convertDate(offerDetailsResponse.getEndTime(), "dd/MM/yyyy"));
            for (int i = 0; i < offerCategoryList.size(); i++) {
                if (offerDetailsResponse.getOfferCategory().getOfferCategoryId().equals(offerCategoryList.get(i).getOfferCategoryId())) {
                    binding.createOfferOfferDropdown.setSelection(i);

                }
            }
              /*  for (int i = 0; i < offerTypeResponseList.size(); i++) {
                    if (offerDetailsResponse.getOfferTypeResponse().getOfferTypeId().equals(offerTypeResponseList.get(i).getOfferTypeId())) {
                        binding.createOfferOfferTypeDropdown.setSelection(i);

                    }
                }*/
            if (offerDetailsResponse.getStatus().equals(KeyWord.ACTIVE)) {
                offerSwitch.setChecked(true);
            } else if (offerDetailsResponse.getStatus().equals(KeyWord.INACTIVE)) {
                offerSwitch.setChecked(false);
            }
            amount = offerDetailsResponse.getAmount();
            offerId = offerDetailsResponse.getId();
            offerCategoryName = offerDetailsResponse.getOfferCategory().getOfferCategoryTitle();
        });
    }


    //All kind of click events
    private void onClickEvents() {
        binding.createOfferOfferPeriodFrom.setOnClickListener(v -> openCalenderDialog(binding.createOfferOfferPeriodFrom, "from"));
        binding.createOfferOfferPeriodTo.setOnClickListener(v -> openCalenderDialog(binding.createOfferOfferPeriodTo, "to"));
        binding.createOfferSave.setOnClickListener(v -> {
            validation(offerTypeId);
        });

        binding.createOfferGenerateOfferIv.setOnClickListener(v -> {
            binding.createOfferGenerateOfferEditText.setText(getSaltString());
        });

        binding.createOfferIncludeExcludeProducts.setOnClickListener(v -> {

            if (offerId.equals("0") || offerId.equals("")) {
                utility.showDialog("Please select existing offer to include or exclude product");
            }
            else {
                CreateOfferDirections.ActionCreateOfferFragmentToIncludeExcludeFragment action = CreateOfferDirections.actionCreateOfferFragmentToIncludeExcludeFragment();
                action.setOfferId(offerId);
                action.setMessage(offerCategoryName + " Discount " + amount + " % ");
                navController.navigate(action);
            }
        });


    }

    private void validation(String offerTypeId) {

        switch (offerTypeId) {
            case "1":
                offerName = binding.createOfferOfferName.getText().toString();
                amountOfDiscount = binding.createOfferAmountOfDiscount.getText().toString();
                minimumBuy = binding.createOfferMinimumBuy.getText().toString();
                maxDiscount = binding.createOfferMaxDiscountEt.getText().toString();
                generatedCode = binding.createOfferGenerateOfferEditText.getText().toString();
                offerPeriodFrom = binding.createOfferOfferPeriodFrom.getText().toString();
                offErPeriodTo = binding.createOfferOfferPeriodTo.getText().toString();
                if (TextUtils.isEmpty(offerTypeId)) {
                    showErrorStyle(binding.createOfferOfferTypeTv);
                } else if (TextUtils.isEmpty(offerCategoryId)) {
                    showErrorStyle(binding.createOfferOfferTv);
                } else if (TextUtils.isEmpty(amountOfDiscount)) {
                    showErrorStyle(binding.createOfferAmountOfDiscountTv, binding.createOfferAmountOfDiscount);
                } else if (TextUtils.isEmpty(offerName)) {
                    showErrorStyle(binding.createOfferOfferNameTv, binding.createOfferOfferName);
                } else if (TextUtils.isEmpty(minimumBuy)) {
                    showErrorStyle(binding.createOfferMinimumBuyTv, binding.createOfferMinimumBuy);
                } else if (TextUtils.isEmpty(maxDiscount)) {
                    showErrorStyle(binding.createOfferMaxDiscountTv, binding.createOfferMaxDiscountEt);
                } else if (TextUtils.isEmpty(generatedCode)) {
                    showErrorStyle(binding.createOfferGenerateOfferCodeTv, binding.createOfferGenerateOfferEditText);
                } else if (TextUtils.isEmpty(offerPeriodFrom)) {
                    showErrorStyle(binding.createOfferOfferPeriodTv);
                } else if (TextUtils.isEmpty(offErPeriodTo)) {
                    showErrorStyle(binding.createOfferOfferPeriodTv);
                } else {
                    // Here "0" means create own offer
                    if (offerListId.equals("0")) {
                        OfferCreate offerCreate = new OfferCreate(offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offErPeriodTo));
                        createOffer(offerCreate, "Upon total bill ", amountOfDiscount);
                    } else {
                        OfferUpdate offerUpdate = new OfferUpdate(offerId, offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offErPeriodTo));
                        updateOffer(offerUpdate, "Upon total bill ", amountOfDiscount);
                    }
                }
                break;
            case "2":
                offerCategoryId = "";
                amountOfDiscount = "";
                maxDiscount = "";
                generatedCode = "";
                minimumBuy = binding.createOfferMinimumBuy.getText().toString();
                offerName = binding.createOfferOfferNameMinimumBuy.getText().toString();
                offerPeriodFrom = binding.createOfferOfferPeriodFrom.getText().toString();
                offErPeriodTo = binding.createOfferOfferPeriodTo.getText().toString();
                if (TextUtils.isEmpty(minimumBuy)) {
                    showErrorStyle(binding.createOfferMinimumBuyTv, binding.createOfferMinimumBuy);
                } else if (TextUtils.isEmpty(offerName)) {
                    showErrorStyle(binding.createOfferOfferNameTv, binding.createOfferOfferName);
                } else if (TextUtils.isEmpty(offerPeriodFrom)) {
                    showErrorStyle(binding.createOfferOfferPeriodTv);
                } else if (TextUtils.isEmpty(offErPeriodTo)) {
                    showErrorStyle(binding.createOfferOfferPeriodTv);
                } else {
                    // Here "0" means create own offer
                    if (selectCreateOfferId.equals("0")) {
                        OfferCreate offerCreate = new OfferCreate(offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offErPeriodTo));
                        createOffer(offerCreate, "Free delivery", "");
                    } else {
                        OfferUpdate offerUpdate = new OfferUpdate(offerId, offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offerPeriodFrom));
                        updateOffer(offerUpdate, "Free delivery", "");
                    }
                }
                break;
            case "3":
                minimumBuy = binding.createOfferBuy.getText().toString();
                amountOfDiscount = binding.createOfferGet.getText().toString();
                offerName = binding.createOfferBuyGetOfferName.getText().toString();
                offerPeriodFrom = binding.createOfferOfferPeriodFrom.getText().toString();
                offErPeriodTo = binding.createOfferOfferPeriodTo.getText().toString();
                if (offerCategoryId.equals("3")) {
                    if (TextUtils.isEmpty(minimumBuy)) {
                        showErrorStyle(binding.createOfferBuyTv, binding.createOfferBuy);
                    } else if (TextUtils.isEmpty(offerName)) {
                        showErrorStyle(binding.createOfferGetTv, binding.createOfferGet);

                    } else if (TextUtils.isEmpty(offerName)) {
                        showErrorStyle(binding.createOfferBuyGetOfferNameTv, binding.createOfferBuyGetOfferName);

                    } else if (TextUtils.isEmpty(offerPeriodFrom)) {
                        showErrorStyle(binding.createOfferOfferPeriodTv);
                    } else if (TextUtils.isEmpty(offErPeriodTo)) {
                        showErrorStyle(binding.createOfferOfferPeriodTv);
                    } else {
                        // Here "0" means create own offer
                        if (selectCreateOfferId.equals("0")) {
                            OfferCreate offerCreate = new OfferCreate(offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                    maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offErPeriodTo));
                            createOffer(offerCreate, "Product based offer", "");
                        } else {
                            OfferUpdate offerUpdate = new OfferUpdate(offerId, offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                    maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offerPeriodFrom));
                            updateOffer(offerUpdate, "Product based offer", "");
                        }
                    }
                } else {
                    minimumBuy = "0";
                    amountOfDiscount = binding.createOfferAmountOfDiscount.getText().toString();
                    offerName = binding.createOfferOfferName.getText().toString();
                    offerPeriodFrom = binding.createOfferOfferPeriodFrom.getText().toString();
                    offErPeriodTo = binding.createOfferOfferPeriodTo.getText().toString();
                    if (TextUtils.isEmpty(amountOfDiscount)) {
                        showErrorStyle(binding.createOfferAmountOfDiscountTv, binding.createOfferAmountOfDiscount);
                    } else if (TextUtils.isEmpty(offerName)) {
                        showErrorStyle(binding.createOfferOfferNameTv, binding.createOfferOfferName);

                    } else if (TextUtils.isEmpty(offerPeriodFrom)) {
                        showErrorStyle(binding.createOfferOfferPeriodTv);
                    } else if (TextUtils.isEmpty(offErPeriodTo)) {
                        showErrorStyle(binding.createOfferOfferPeriodTv);
                    } else {
                        // Here "0" means create own offer
                        if (selectCreateOfferId.equals("0")) {
                            OfferCreate offerCreate = new OfferCreate(offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                    maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offErPeriodTo));
                            createOffer(offerCreate, "Product based offer", amountOfDiscount);
                        } else {
                            OfferUpdate offerUpdate = new OfferUpdate(offerId, offerTypeId, offerCategoryId, offerName, amountOfDiscount, minimumBuy,
                                    maxDiscount, generatedCode, convertStringToMilliSecondForFromDate(offerPeriodFrom), convertStringToMilliSecondForToDate(offerPeriodFrom));
                            updateOffer(offerUpdate, "Product based offer", amountOfDiscount);
                        }
                    }
                }
                break;
        }
    }

    private void updateOffer(OfferUpdate offerUpdate, String message, String discount) {
        offerViewModel.updateOffer(offerUpdate).observe(getActivity(), api_response -> {
            if (api_response.getCode() == 200) {
                CreateOfferDirections.ActionCreateOfferFragmentToOfferCreatedSuccessfully action = CreateOfferDirections.actionCreateOfferFragmentToOfferCreatedSuccessfully();
                action.setOfferDate(binding.createOfferOfferPeriodFrom.getText().toString());
                if (discount.equals("")) {
                    action.setOfferDiscount(message);
                } else {
                    action.setOfferDiscount(message + discount + " %");
                }

                action.setOfferToDate(binding.createOfferOfferPeriodTo.getText().toString());
                action.setMessage("Offer Updated Successfully");
                navController.navigate(action);

            }
        });
    }

    private void createOffer(OfferCreate offerCreate, String message, String amountOfDiscount) {
        offerViewModel.createOffer(offerCreate).observe(getActivity(), api_response -> {
            if (api_response.getCode() == 200) {

                CreateOfferDirections.ActionCreateOfferFragmentToOfferCreatedSuccessfully action = CreateOfferDirections.actionCreateOfferFragmentToOfferCreatedSuccessfully();
                action.setOfferDate(binding.createOfferOfferPeriodFrom.getText().toString());
                if (amountOfDiscount.equals("")) {
                    action.setOfferDiscount(message);
                } else {
                    action.setOfferDiscount(message + amountOfDiscount + " %");
                }
                action.setOfferToDate(binding.createOfferOfferPeriodTo.getText().toString());
                action.setMessage("Offer Created Successfully");
                navController.navigate(action);

            } else {
                utility.showDialog(api_response.getData().getAsString());
            }
        });
    }

    private void showErrorStyle(TextView textView) {
        textView.setTextColor(getActivity().getResources().getColor(R.color.deep_red));

    }

    private void showErrorStyle(TextView textView, EditText editText) {
        textView.setTextColor(getActivity().getResources().getColor(R.color.deep_red));
        editText.requestFocus();
    }

    // All kind of spinner operation

    private void showOfferType(List<OfferTypeResponse> offerTypeResponses) {
        GenericAdapter<OfferTypeResponse> adapter = new GenericAdapter<OfferTypeResponse>(getActivity(), offerTypeResponses) {
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

        binding.createOfferOfferTypeDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.createOfferOfferTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                offerTypeId = offerTypeResponses.get(position).getOfferTypeId();
                OfferCategoryRequest offerCategoryRequest = new OfferCategoryRequest(offerTypeId);
               /* for (int i = 0; i < offerTypeResponses.size(); i++) {
                    if (offerDetailsResponse.getOfferTypeResponse().getOfferTypeId().equals(offerTypeResponses.get(i).getOfferTypeId())) {
                        binding.createOfferOfferTypeDropdown.setSelection(i);

                    }
                }*/
                if (position == 0) {


                    offerViewModel.getOfferCategory(offerCategoryRequest).observe(getActivity(), offerCategories -> {
                        showOfferCategory(offerCategories);
                    });

                    OfferListRequest offerListRequest = new OfferListRequest(KeyWord.TIMELINE_ALL, offerTypeResponses.get(position).getOfferTypeId());
                    offerViewModel.getOfferList(offerListRequest).observe(getActivity(), offerListResponses -> {
                        offerList.clear();
                        OfferListResponse offerListResponse = new OfferListResponse("0", "Create your own offer");
                        offerList.addAll(offerListResponses);
                        offerList.add(offerListResponse);
                        showOfferList(offerList);
                    });
                    binding.createOfferSelectCreateOfferLayout.setVisibility(View.GONE);
                    binding.createOfferMinimumBuyOfferAmountLayout.setVisibility(View.VISIBLE);
                    binding.createOfferGenerateOfferCodeLayout.setVisibility(View.VISIBLE);
                    binding.createOfferAmountOfDiscountLayout.setVisibility(View.VISIBLE);
                    binding.createOfferOfferLayout.setVisibility(View.VISIBLE);
                    binding.createOfferOfferList.setVisibility(View.VISIBLE);
                    binding.createOfferIncludeExcludeProducts.setVisibility(View.GONE);
                    binding.createOfferOfferAmountLayout.setVisibility(View.GONE);
                    binding.createOfferMaxDiscountLayout.setVisibility(View.VISIBLE);
                    binding.createOfferOfferTv.setText("Offer");


                } else if (position == 1) {
                    OfferListRequest offerListRequest = new OfferListRequest(KeyWord.TIMELINE_ALL, offerTypeResponses.get(position).getOfferTypeId());
                    offerViewModel.getOfferList(offerListRequest).observe(getActivity(), offerListResponses -> {
                        offerList.clear();
                        OfferListResponse offerListResponse = new OfferListResponse("0", "Create your own offer");
                        offerList.addAll(offerListResponses);
                        offerList.add(offerListResponse);
                        showOfferSelectCreate(offerList);
                    });
                    binding.createOfferSelectCreateOfferLayout.setVisibility(View.VISIBLE);
                    binding.createOfferIncludeExcludeProducts.setVisibility(View.GONE);
                    binding.createOfferMinimumBuyOfferAmountLayout.setVisibility(View.VISIBLE);
                    binding.createOfferGenerateOfferCodeLayout.setVisibility(View.GONE);
                    binding.createOfferAmountOfDiscountLayout.setVisibility(View.GONE);
                    binding.createOfferOfferLayout.setVisibility(View.GONE);
                    binding.createOfferOfferList.setVisibility(View.GONE);
                    binding.createOfferMaxDiscountLayout.setVisibility(View.GONE);
                    binding.createOfferOfferAmountLayout.setVisibility(View.VISIBLE);


                } else if (position == 2) {

                    offerViewModel.getOfferCategory(offerCategoryRequest).observe(getActivity(), offerCategories -> {
                        showOfferCategory(offerCategories);
                    });
                    OfferListRequest offerListRequest = new OfferListRequest(KeyWord.TIMELINE_ALL, offerTypeResponses.get(position).getOfferTypeId());
                    offerViewModel.getOfferList(offerListRequest).observe(getActivity(), offerListResponses -> {
                        offerList.clear();
                        OfferListResponse offerListResponse = new OfferListResponse("0", "Create your own offer");
                        offerList.addAll(offerListResponses);
                        offerList.add(offerListResponse);
                        showOfferSelectCreate(offerList);
                    });

                    binding.createOfferSelectCreateOfferLayout.setVisibility(View.VISIBLE);
                    binding.createOfferOfferLayout.setVisibility(View.VISIBLE);
                    binding.createOfferOfferTv.setVisibility(View.VISIBLE);
                    binding.createOfferOfferTv.setText("Percentage or Amount");
                    binding.createOfferAmountOfDiscountLayout.setVisibility(View.VISIBLE);
                    binding.createOfferMinimumBuyOfferAmountLayout.setVisibility(View.GONE);
                    binding.createOfferGenerateOfferCodeLayout.setVisibility(View.GONE);
                    binding.createOfferIncludeExcludeProducts.setVisibility(View.VISIBLE);
                    binding.createOfferOfferList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showOfferSelectCreate(List<OfferListResponse> offerList) {
        GenericAdapter<OfferListResponse> adapter = new GenericAdapter<OfferListResponse>(getActivity(), offerList) {
            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                OfferListResponse model = getItem(position);


                View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                label.setText(model.getOfferListTitle());
                return spinnerRow;
            }
        };

        binding.createOfferSelectCreateOfferDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.createOfferSelectCreateOfferDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectCreateOfferId = offerList.get(position).getOfferListId();
                offerName = "";
                amountOfDiscount = "";
                minimumBuy = "";
                maxDiscount = "";
                generatedCode = "";
                offerPeriodFrom = "";
                offErPeriodTo = "";
                if (offerList.get(position).getOfferListId().equals("0")) {

                    binding.createOfferOfferName.setText("");
                    binding.createOfferAmountOfDiscount.setText("");
                    binding.createOfferOfferNameMinimumBuy.setText("");
                    binding.createOfferMinimumBuy.setText("");
                    binding.createOfferOfferPeriodFrom.setText("");
                    binding.createOfferOfferPeriodTo.setText("");
                    binding.createOfferBuy.setText("");
                    binding.createOfferGet.setText("");
                    binding.createOfferBuyGetOfferName.setText("");
                    offerId = "0";
                } else {

                    OfferDetailsRequest offerDetailsRequest = new OfferDetailsRequest(selectCreateOfferId);
                    offerViewModel.sendOfferDetailsRequest(offerDetailsRequest);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showOfferList(List<OfferListResponse> offerListResponses) {
        GenericAdapter<OfferListResponse> adapter = new GenericAdapter<OfferListResponse>(getActivity(), offerListResponses) {
            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                OfferListResponse model = getItem(position);


                View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                label.setText(model.getOfferListTitle());
                return spinnerRow;
            }
        };

        binding.createOfferOfferListDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.createOfferOfferListDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                offerListId = offerListResponses.get(position).getOfferListId();


                if (!offerListResponses.get(position).getOfferListId().equals("0")) {
                    OfferDetailsRequest offerDetailsRequest = new OfferDetailsRequest(offerListResponses.get(position).getOfferListId());
                    offerViewModel.sendOfferDetailsRequest(offerDetailsRequest);
                } else {
                    binding.createOfferAmountOfDiscount.setText("");
                    binding.createOfferOfferName.setText("");
                    binding.createOfferMinimumBuy.setText("");
                    binding.createOfferMaxDiscountEt.setText("");
                    binding.createOfferGenerateOfferEditText.setText("");
                    binding.createOfferOfferPeriodFrom.setText("");
                    binding.createOfferOfferPeriodTo.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showOfferCategory(List<OfferCategory> offerCategories) {
        offerCategoryList.clear();
        offerCategoryList.addAll(offerCategories);
        GenericAdapter<OfferCategory> adapter = new GenericAdapter<OfferCategory>(getActivity(), offerCategories) {
            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                OfferCategory model = getItem(position);


                View spinnerRow = LayoutInflater.from(parent.getContext()).inflate(com.aapnarshop.seller.R.layout.custom_spinner_item, parent, false);

                TextView label = spinnerRow.findViewById(com.aapnarshop.seller.R.id.custom_spinner);
                if (model != null) {
                    label.setText(model.getOfferCategoryTitle());
                }
                return spinnerRow;
            }
        };

        binding.createOfferOfferDropdown.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.createOfferOfferDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                offerCategoryId = offerCategories.get(position).getOfferCategoryId();
                /*for (int i = 0; i < offerCategories.size(); i++) {
                    if (offerDetailsResponse.getOfferCategory().getOfferCategoryId().equals(offerCategories.get(i).getOfferCategoryId())) {
                        binding.createOfferOfferDropdown.setSelection(i);

                    }
                }*/
                if (position == 2) {

                    binding.createOfferBuyGetLayout.setVisibility(View.VISIBLE);
                    binding.createOfferBuyGetOfferNameLayout.setVisibility(View.VISIBLE);
                    binding.createOfferAmountOfDiscountLayout.setVisibility(View.GONE);
                } else {
                    binding.createOfferBuyGetLayout.setVisibility(View.GONE);
                    binding.createOfferBuyGetOfferNameLayout.setVisibility(View.GONE);
                    binding.createOfferAmountOfDiscountLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        switchLayout.setVisibility(View.GONE);

    }

    //Open calender dialog

    @SuppressLint("SetTextI18n")
    private void openCalenderDialog(TextView dateTextView, String message) {
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


            dateTextView.setText(dateFormat.format(date) + "/" + monthFormat.format(date) + "/" + yearFormat.format(date));


            dialog.dismiss();
        });


        dialog.show();
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }

    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    public String convertStringToMilliSecondForFromDate(String date) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(fromDateStartHour(date1).getTime());
    }

    public String convertStringToMilliSecondForToDate(String date) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(toDateLastHour(date1).getTime());
    }
}