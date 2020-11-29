package com.aapnarshop.seller.VIEW.FRAGMENT.offer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Category;
import com.aapnarshop.seller.Model.GET.OfferProductListResponse;
import com.aapnarshop.seller.Model.SEND.AddOfferProduct;
import com.aapnarshop.seller.Model.SEND.OfferProductListRequest;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.CategoryAdapter;
import com.aapnarshop.seller.VIEW.Adapter.IncludeExcludeAdapter;
import com.aapnarshop.seller.VIEW.Adapter.PopupWindowAdapter;
import com.aapnarshop.seller.VIEWMODEL.OfferViewModel;
import com.aapnarshop.seller.VIEWMODEL.ProductViewModel;
import com.aapnarshop.seller.databinding.FragmentIncludeExcludeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik Roy on 25,November,2020
 */
public class IncludeExclude extends Fragment implements CategoryAdapter.CategoryInterface {

    FragmentIncludeExcludeBinding binding;
    OfferViewModel offerViewModel;
    ProductViewModel productViewModel;
    Toolbar toolbar;
    TextView textToolHeader;
    String offerId, title;
    Utility utility;
    ListPopupWindow listPopupWindow;
    private boolean popMenuOpen = false;
    private boolean isCategoryExpanded =false;
    private final List<Category> categoryList = new ArrayList<>();
    private final List<OfferProductListResponse> offerProductListResponseList = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_include_exclude, container, false);
        offerViewModel = new ViewModelProvider(this).get(OfferViewModel.class);
        productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        textToolHeader = (TextView) toolbar.findViewById(R.id.title);
        offerId = IncludeExcludeArgs.fromBundle(getArguments()).getOfferId();
        title = IncludeExcludeArgs.fromBundle(getArguments()).getMessage();
        textToolHeader.setText(title.toLowerCase());
        utility = new Utility(getActivity());

        offerViewModel.getAPIResponse().observe(getActivity(), api_response -> {

            if (api_response != null && api_response.getCode() == 200) {
                observeOfferProduct(offerId,KeyWord.FILTERED_BY_NEW);
                utility.showDialog(api_response.getData().getAsString());

            } else {
                utility.showDialog(api_response.getData().getAsString());
            }
        });

        binding.includeExcludeIncludeBtn.setOnClickListener(v -> {
            AddOfferProduct addOfferProduct = new AddOfferProduct(offerId, KeyWord.TYPE_ALL, KeyWord.STATUS_INCLUDE);
            offerViewModel.addOfferProduct(addOfferProduct);
        });

        binding.includeExcludeExcludeBtn.setOnClickListener(v -> {
            AddOfferProduct addOfferProduct = new AddOfferProduct(offerId, KeyWord.TYPE_ALL, KeyWord.STATUS_EXCLUDE);
            offerViewModel.addOfferProduct(addOfferProduct);
        });
        observeOfferProduct(offerId,KeyWord.FILTERED_BY_NEW);

        binding.includeExcludeFilter.setOnClickListener(v -> {
            if (!popMenuOpen){
                showListPopupWindow(v);
                popMenuOpen = true;
            }else {
                popMenuOpen = false;
                listPopupWindow.dismiss();
            }
        });

        Toast.makeText(getActivity(), offerId, Toast.LENGTH_SHORT).show();
        productViewModel.getProductCategory().observe(getActivity(),categoryies -> {
            categoryList.clear();
            categoryList.addAll(categoryies);
        });

        binding.searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textLength = s.length();
                ArrayList<OfferProductListResponse> tempArrayList = new ArrayList<>();
                for(OfferProductListResponse offerProductListResponse: offerProductListResponseList ){
                    if (textLength <= offerProductListResponse.getProductName().length()) {
                        if (offerProductListResponse.getProductName().toLowerCase().contains(s.toString().toLowerCase())) {
                            tempArrayList.add(offerProductListResponse);
                        }
                    }
                }
                initAdapter(tempArrayList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return binding.getRoot();
    }

    private void observeOfferProduct(String offerId, String filteredByNew) {
        OfferProductListRequest offerProductListRequest = new OfferProductListRequest(offerId,filteredByNew);

        offerViewModel.getOfferProductList(offerProductListRequest).observe(getActivity(), offerProductListResponses -> {
            if (offerProductListResponses.size() > 0) {
                offerProductListResponseList.clear();
                offerProductListResponseList.addAll(offerProductListResponses);
                binding.rvIncludeExclude.setVisibility(View.VISIBLE);
                initAdapter(offerProductListResponses);
            }else {
                binding.rvIncludeExclude.setVisibility(View.GONE);
            }
        });
    }

    private void initAdapter(List<OfferProductListResponse> offerProductList) {
        IncludeExcludeAdapter adapter = new IncludeExcludeAdapter(getActivity(), offerProductList, offerId, offerViewModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.rvIncludeExclude.setLayoutManager(mLayoutManager);
        binding.rvIncludeExclude.setAdapter(adapter);
    }

    private void showListPopupWindow(View anchor) {
        List<String> listPopupItems = new ArrayList<>();
        listPopupItems.add("Include");
        listPopupItems.add("Exclude");
        listPopupItems.add("Category");
        listPopupItems.add("Ascending");
        listPopupItems.add("Descending");
        listPopupItems.add("New");
        listPopupItems.add("Old");
        listPopupWindow =
                createListPopupWindow(anchor, 400, listPopupItems);
        listPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            listPopupWindow.dismiss();
            Toast.makeText(getActivity(), "clicked at " + listPopupItems.get(position), Toast.LENGTH_SHORT)
                    .show();
        });
        listPopupWindow.show();
    }

    private ListPopupWindow createListPopupWindow(View anchor, int width,
                                                  List<String> items) {
        final ListPopupWindow popup = new ListPopupWindow(getContext());
        PopupWindowAdapter<String> adapter = new PopupWindowAdapter<String>(items) {

            @Override
            public View getCustomView(int position, View view, ViewGroup parent) {
                String model = getItem(position);

                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popup, parent, false);
                TextView title = view1.findViewById(R.id.popup_menu_title);
                ImageView arrow = view1.findViewById(R.id.expand_arrow);
                RecyclerView categoryRecyclerView = view1.findViewById(R.id.popup_menu_rv);

                title.setText(model);
                if (model.equalsIgnoreCase("Category")){
                    arrow.setVisibility(View.VISIBLE);
                }
                view1.setOnClickListener(v -> {
                    if(model.equalsIgnoreCase("Category")){
                        if (!isCategoryExpanded){
                            arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
                            categoryRecyclerView.setVisibility(View.VISIBLE);
                            CategoryAdapter adapter = new CategoryAdapter(categoryList,getActivity(),IncludeExclude.this, popup);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            categoryRecyclerView.setLayoutManager(mLayoutManager);
                            categoryRecyclerView.setAdapter(adapter);
                            isCategoryExpanded = true;
                        }
                        else {
                            arrow.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                            categoryRecyclerView.setVisibility(View.GONE);
                            isCategoryExpanded =false;
                        }
                    }else if (model.equalsIgnoreCase("Include")){
                        observeOfferProduct(offerId,KeyWord.FILTERED_BY_INCLUDED);
                        popup.dismiss();
                    }
                    else if (model.equalsIgnoreCase("Exclude")){
                        observeOfferProduct(offerId,KeyWord.FILTERED_BY_EXCLUDED);
                        popup.dismiss();
                    }
                    else if (model.equalsIgnoreCase("Ascending")){
                        observeOfferProduct(offerId,KeyWord.FILTERED_BY_ASCENDING);
                        popup.dismiss();
                    }
                    else if (model.equalsIgnoreCase("Descending")){
                        observeOfferProduct(offerId,KeyWord.FILTERED_BY_DESCENDING);
                        popup.dismiss();
                    }
                    else if (model.equalsIgnoreCase("New")){
                        observeOfferProduct(offerId,KeyWord.FILTERED_BY_NEW);
                        popup.dismiss();
                    }
                    else if (model.equalsIgnoreCase("Old")){
                        observeOfferProduct(offerId,KeyWord.FILTERED_BY_OLD);
                        popup.dismiss();
                    }
                });

                return view1;
            }
        };
        popup.setAnchorView(anchor);
        popup.setWidth(width);
        popup.setAdapter(adapter);
        popup.setDropDownGravity(Gravity.END);
        return popup;
    }

    @Override
    public void categoryId(String categoryId) {
        observeOfferProduct(offerId,categoryId);

    }
}