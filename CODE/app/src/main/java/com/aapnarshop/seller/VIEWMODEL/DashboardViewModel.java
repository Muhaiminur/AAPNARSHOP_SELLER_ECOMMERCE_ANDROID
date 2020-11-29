package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aapnarshop.seller.Model.GET.ProductCount;
import com.aapnarshop.seller.Repository.DashboardRepo;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardRepo dashboardRepo;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        dashboardRepo= new DashboardRepo(application);
    }


    public LiveData<ProductCount> getProductCount() {
        return dashboardRepo.getProductCount();
    }
}