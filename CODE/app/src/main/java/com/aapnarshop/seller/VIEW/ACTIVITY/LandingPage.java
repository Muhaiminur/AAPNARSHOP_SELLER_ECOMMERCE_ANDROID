package com.aapnarshop.seller.VIEW.ACTIVITY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.GetConfig;
import com.aapnarshop.seller.Model.GET.Profile;
import com.aapnarshop.seller.R;
import com.aapnarshop.seller.VIEWMODEL.HomeViewModel;
import com.aapnarshop.seller.VIEWMODEL.LandingPageViewModel;
import com.aapnarshop.seller.databinding.ActivityMainBinding;
import com.google.gson.Gson;

public class LandingPage extends AppCompatActivity {

    ActivityMainBinding binding;
    LandingPageViewModel landingPageViewModel;
    String language;
    Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        landingPageViewModel = new ViewModelProvider(this).get(LandingPageViewModel.class);
        utility = new Utility(this);
        language = utility.getLangauge();
        
        observer();
    }

    private void observer() {
       /* landingPageViewModel.getProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    binding.mainActivityProgressbar.setVisibility(View.VISIBLE);

                }else {
                    binding.mainActivityProgressbar.setVisibility(View.GONE);
                }
            }
        });*/

        landingPageViewModel.getConfigLiveData().observe(this, getConfig -> {
            if (getConfig != null){
                
                getObjectSharedPreference();

            }

        });
    }

    private void getObjectSharedPreference() {
        SharedPreferences sharedPref = getSharedPreferences("Profile", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("MyObject", "");
        Profile obj = gson.fromJson(json, Profile.class);
        if (obj !=null){
            startActivity(new Intent(LandingPage.this,HomePage.class));
            finish();
        }else {
            startActivity(new Intent(LandingPage.this,Authentication.class));
            finish();
        }
    }
}
