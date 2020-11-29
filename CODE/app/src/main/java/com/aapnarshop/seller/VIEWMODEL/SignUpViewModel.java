package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Model.GET.Area;
import com.aapnarshop.seller.Model.GET.City;
import com.aapnarshop.seller.Model.GET.District;
import com.aapnarshop.seller.Model.GET.Division;
import com.aapnarshop.seller.Model.GET.ShopCategory;
import com.aapnarshop.seller.Model.SEND.Registration;
import com.aapnarshop.seller.Repository.SignUpRepo;

import java.util.List;

public class SignUpViewModel extends AndroidViewModel {

    private SignUpRepo signUpRepo;

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        signUpRepo = new SignUpRepo(application);
    }

    public LiveData<List<ShopCategory>> getShopCategoryList(){

        return signUpRepo.getShopCategoryList();
    }

    public LiveData<List<Division>> getDivisionList(){

        return signUpRepo.getDivisionList();
    }
    public LiveData<List<District>> getDistrictList(Division division){

        return signUpRepo.getDistrictList(division);
    }
    public LiveData<List<City>> getCityList(District district){
        return signUpRepo.getCityList(district);
    }
    public LiveData<List<Area>> getAreaList(City city){
        return signUpRepo.getAreaList(city);
    }

    public LiveData<API_RESPONSE> sendRegistration(Registration registration){
        return signUpRepo.sendRegistration(registration);
    }
}
