package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aapnarshop.seller.Model.GET.GetConfig;
import com.aapnarshop.seller.Repository.LandingPageRepo;

public class LandingPageViewModel extends AndroidViewModel {

    private LandingPageRepo landingPageRepo;
    public LandingPageViewModel(@NonNull Application application) {
        super(application);
        landingPageRepo = new LandingPageRepo(application);
    }

    public LiveData<GetConfig> getConfigLiveData(){

        return landingPageRepo.getConfigLiveData();
    }

    public LiveData<Boolean> getProgressBar(){
        return landingPageRepo.getProgress();
    }
}
