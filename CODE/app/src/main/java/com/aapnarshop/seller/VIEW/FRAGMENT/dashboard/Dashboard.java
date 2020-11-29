package com.aapnarshop.seller.VIEW.FRAGMENT.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Profile;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEWMODEL.DashboardViewModel;
import com.aapnarshop.seller.databinding.FragmentDashboardBinding;
import com.google.gson.Gson;

public class Dashboard extends Fragment {

    Toolbar toolbar;
    TextView textToolHeader;
    FragmentDashboardBinding binding;
    Utility utility;
    String language;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        try {
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            textToolHeader = (TextView) toolbar.findViewById(R.id.title);
            utility = new Utility(getActivity().getApplicationContext());
            language = utility.getLangauge();

            getSharedPref();


            //Change language ----- Start------------------

            binding.dashboardNewOrderTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.new_order_bn) : getString(R.string.new_order));
            binding.dashboardNewOrder2.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.new_order_bn) : getString(R.string.new_order));
            binding.dashboardPerviousOrderTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.previous_order_bn) : getString(R.string.previous_order));
            binding.dashboardYourProductTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.your_product_bn) : getString(R.string.your_product));
            binding.dashboardAddNewProductTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.add_new_product_bn) : getString(R.string.add_new_product));
            binding.dashboardTotalActiveProduct.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.total_active_product_bn) : getString(R.string.total_active_product));
            binding.dashboardLimitedProductTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.limited_product_bn) : getString(R.string.limited_product));
            binding.dashboardStockLimitedTv.setText(language.equals(KeyWord.BANGLA) ? getString(R.string.stock_limited_product_bn) : getString(R.string.stock_limited_product));
            binding.dashboardSettings.setText(language.equals(KeyWord.BANGLA) ? KeyWord.SETTINGS_BN : KeyWord.SETTINGS);
            binding.dashboardOfferTv.setText(language.equals(KeyWord.BANGLA) ? KeyWord.OFFER_BN : KeyWord.OFFER);
            binding.dashboardReportTv.setText(language.equals(KeyWord.BANGLA) ? KeyWord.REPORT_BN : KeyWord.REPORT);
            binding.dashboardSignoutTv.setText(language.equals(KeyWord.BANGLA) ? KeyWord.LOGOUT_BN : KeyWord.LOGOUT);


            //===== End ======

            binding.dashboardNewOrder.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_pendingOrderFragment));
            binding.dashboardAddNewProduct.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_addProductFragment));
            binding.dashboardYourProduct.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_allProductFragment));
            binding.dashboardPreviousOrder.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_completeOrderFragment));
            binding.dashboardSettingsIcon.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_settingsFragment));
            binding.dashboardReport.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_reportFragment));
            binding.dashboardOffer.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_dashboardFragment_to_createOfferFragment));

            dashboardViewModel.getProductCount().observe(getViewLifecycleOwner(), productCount -> {
                binding.dashboardNewOrderCount.setText(productCount.getNewOrder());
                binding.dashboardTotalActiveProductCount.setText(productCount.getActiveProduct());
                binding.dashboardLimitedProductCount.setText(productCount.getLimitedProduct());

            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }

        return binding.getRoot();
    }

    private void getSharedPref() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("MyObject", "");
        Profile profile = gson.fromJson(json, Profile.class);
        textToolHeader.setText(profile.getShopName());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}