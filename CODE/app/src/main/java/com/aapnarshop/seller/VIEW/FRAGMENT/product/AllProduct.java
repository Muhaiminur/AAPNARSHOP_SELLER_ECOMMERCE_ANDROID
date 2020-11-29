package com.aapnarshop.seller.VIEW.FRAGMENT.product;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.aapnarshop.seller.Model.SEND.FilterProduct;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEW.Adapter.AllProductAdapter;
import com.aapnarshop.seller.VIEW.Adapter.CategoryAdapter;
import com.aapnarshop.seller.VIEW.Adapter.PopupWindowAdapter;
import com.aapnarshop.seller.VIEWMODEL.ProductViewModel;
import com.aapnarshop.seller.databinding.FragmentAllProductBinding;

import java.util.ArrayList;
import java.util.List;

public class AllProduct extends Fragment implements CategoryAdapter.CategoryInterface {

    Toolbar toolbar;
    TextView textToolHeader;
    private FragmentAllProductBinding binding;
    private ProductViewModel productViewModel;
    private final List<Category> categoryList = new ArrayList<>();
    private final List<com.aapnarshop.seller.Model.GET.AllProduct> productList = new ArrayList<>();
    private boolean isCategoryExpanded =false;
    private boolean popMenuOpen = false;
    ListPopupWindow listPopupWindow;

    public AllProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_product, container, false);

        try {
            Utility utility = new Utility(getActivity());
            String language = utility.getLangauge();
            productViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            textToolHeader.setText(language.equals(KeyWord.BANGLA) ? KeyWord.ALL_PRODUCT_BN : KeyWord.ALL_PRODUCT);

            FilterProduct filterProduct = new FilterProduct("new");
            binding.allProductSwipeRefresh.setRefreshing(true);
            getAllProduct(filterProduct);

            binding.allProductSwipeRefresh.setOnRefreshListener(() -> {
                getAllProduct(filterProduct);
                binding.allProductSwipeRefresh.setRefreshing(false);
            });

            productViewModel.getProductCategory().observe(getActivity(),categoryies -> {
                categoryList.clear();
                categoryList.addAll(categoryies);
            });

            binding.allProductFilter.setOnClickListener(v -> {
                if (!popMenuOpen){
                    showListPopupWindow(v);
                    popMenuOpen = true;
                }else {
                    popMenuOpen = false;
                    listPopupWindow.dismiss();
                }
            });

            //Search product by product name

            binding.allProductSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int textLength = s.length();
                    ArrayList<com.aapnarshop.seller.Model.GET.AllProduct> tempArrayList = new ArrayList<>();
                    for(com.aapnarshop.seller.Model.GET.AllProduct c: productList ){
                        if (textLength <= c.getProductTitle().length()) {
                            if (c.getProductTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    initAdapter(tempArrayList);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }


        return binding.getRoot();
    }

    private void getAllProduct(FilterProduct filterProduct) {
        productViewModel.getProductList(filterProduct).observe(getActivity(),allProducts -> {

            if (allProducts.size()>0){
                productList.clear();
                productList.addAll(allProducts);
                initAdapter(allProducts);
                binding.allProductSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void initAdapter(List<com.aapnarshop.seller.Model.GET.AllProduct> allProducts) {
        AllProductAdapter adapter = new AllProductAdapter(getActivity(), allProducts,productViewModel);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.allProduct.setLayoutManager(mLayoutManager);
        binding.allProduct.setAdapter(adapter);
    }


    private void showListPopupWindow(View anchor) {
        List<String> listPopupItems = new ArrayList<>();
        listPopupItems.add("Limited Stock");
        listPopupItems.add("Category");
        listPopupItems.add("Ascending");
        listPopupItems.add("Descending");
        listPopupItems.add("New");
        listPopupItems.add("Old");
        listPopupItems.add("Other");

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
                            CategoryAdapter adapter = new CategoryAdapter(categoryList,getActivity(),AllProduct.this,popup);
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
                    }else if (model.equalsIgnoreCase("limited stock")){
                        FilterProduct filterProduct = new FilterProduct("limited");
                        getAllProduct(filterProduct);
                        popup.dismiss();
                    }
                    else {
                        FilterProduct filterProduct = new FilterProduct(model);
                        getAllProduct(filterProduct);
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
        FilterProduct filterProduct = new FilterProduct(categoryId);
        getAllProduct(filterProduct);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}