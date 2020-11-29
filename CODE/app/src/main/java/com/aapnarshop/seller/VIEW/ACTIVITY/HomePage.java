package com.aapnarshop.seller.VIEW.ACTIVITY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.databinding.ActivityHomePageBinding;

import static android.view.Gravity.START;

public class HomePage extends AppCompatActivity {

    ActivityHomePageBinding binding;
    ImageView drawerLogo;
    public DrawerLayout drawer;
    public static NavController navController;
    boolean isOrderExpand= false;
    boolean isProductExpand= false;
    boolean isOfferExpand = false;
    private Utility utility;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_home_page);
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            drawerLogo = findViewById(R.id.drawer_logo);
            setSupportActionBar(toolbar);
            drawer = findViewById(R.id.drawer_layout);
            navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            utility = new Utility(getApplicationContext());

            binding.recyclerviewNavMenuItemEnBnSwitch.setChecked(utility.getLangauge().equalsIgnoreCase("bn"));

            //Add transition drawer to fragment
            NavOptions.Builder navBuilder =  new NavOptions.Builder();
            navBuilder.setEnterAnim(R.anim.slide_in_left).setExitAnim(R.anim.slide_out_left).setPopEnterAnim(R.anim.slide_in_right).setPopExitAnim(R.anim.slide_out_right);

            drawerLogo.setOnClickListener(view -> {

                if (!drawer.isDrawerOpen(START)) drawer.openDrawer(START);
                else drawer.closeDrawer(Gravity.END);
            });
            binding.navHeaderMainBackArrow.setOnClickListener(v -> drawer.closeDrawers());


            binding.orderLayout.setOnClickListener(v->{
                if (!isOrderExpand){
                    if (isProductExpand){
                        binding.productExpandLayout.setVisibility(View.GONE);
                        binding.productArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                        isProductExpand = false;
                    }
                    if (isOfferExpand){
                        binding.offerExpandLayout.setVisibility(View.GONE);
                        binding.offerArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                        isOfferExpand = false;
                    }
                    binding.orderExpandLayout.setVisibility(View.VISIBLE);
                    binding.orderArrowImage.setImageResource(R.drawable.ic_baseline_expand_more_24);

                    isOrderExpand = true;

                }else {
                    binding.orderExpandLayout.setVisibility(View.GONE);
                    binding.orderArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);

                    isOrderExpand = false;
                }
            });

            binding.productLayout.setOnClickListener(v->{
                if (!isProductExpand){
                    if (isOrderExpand){
                        binding.orderExpandLayout.setVisibility(View.GONE);
                        binding.orderArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);

                        isOrderExpand = false;
                    }
                    if (isOfferExpand){
                        binding.offerExpandLayout.setVisibility(View.GONE);
                        binding.offerArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);

                        isOfferExpand = false;
                    }
                    binding.productExpandLayout.setVisibility(View.VISIBLE);
                    binding.productArrowImage.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    isProductExpand = true;

                }else {
                    binding.productExpandLayout.setVisibility(View.GONE);
                    binding.productArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);

                    isProductExpand = false;
                }
            });

            binding.offerLayout.setOnClickListener(v->{
                if (!isOfferExpand){
                    if (isOrderExpand){
                        binding.orderExpandLayout.setVisibility(View.GONE);
                        binding.orderArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                        isOrderExpand = false;
                    }
                    if (isProductExpand){
                        binding.productExpandLayout.setVisibility(View.GONE);
                        binding.productArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                        isProductExpand = false;
                    }
                    binding.offerExpandLayout.setVisibility(View.VISIBLE);
                    binding.offerArrowImage.setImageResource(R.drawable.ic_baseline_expand_more_24);
                    isOfferExpand = true;

                }else {
                    binding.offerExpandLayout.setVisibility(View.GONE);
                    binding.offerArrowImage.setImageResource(R.drawable.ic_baseline_arrow_forward_ios_24);

                    isOfferExpand = false;
                }
            });

            binding.dahsboardLayout.setOnClickListener(v->{

                navController.navigate(R.id.dashboardFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);

            });
            binding.reportLayout.setOnClickListener(v->{

                navController.navigate(R.id.reportFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);

            });
            binding.deliveryAreaLayout.setOnClickListener(v->{

                navController.navigate(R.id.deliveryAreaFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.profileLayout.setOnClickListener(v->{

                navController.navigate(R.id.profileFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.notificationLayout.setOnClickListener(v->{

                navController.navigate(R.id.notificationFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.settingLayout.setOnClickListener(v->{
                navController.navigate(R.id.settingsFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.ratingLayout.setOnClickListener(v->{

                navController.navigate(R.id.ratingFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.addProduct.setOnClickListener(v->{

                navController.navigate(R.id.addProductFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.allProduct.setOnClickListener(v->{

                navController.navigate(R.id.allProductFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.pendingOrder.setOnClickListener(v->{

                navController.navigate(R.id.pendingOrderFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.allOrder.setOnClickListener(v->{

                navController.navigate(R.id.completeOrderFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.createOffer.setOnClickListener(v->{
                navController.navigate(R.id.createOfferFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.offerList.setOnClickListener(v->{

                navController.navigate(R.id.offerListFragment,null,navBuilder.build());
                drawer.closeDrawer(GravityCompat.START);
            });
            binding.logoutLayout.setOnClickListener(v->{

                startActivity(new Intent(HomePage.this,Authentication.class));
                drawer.closeDrawer(GravityCompat.START);
                finish();
            });
            binding.recyclerviewNavMenuItemEnBnSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {
                    utility.setLanguage(KeyWord.BANGLA);


                } else {
                    utility.setLanguage(KeyWord.ENGLISH);

                }
                recreate();
            });


        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        drawer.closeDrawers();
    }


}