package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Model.GET.GetDeliveryArea;
import com.aapnarshop.seller.Model.SEND.SendDeliveryArea;
import com.aapnarshop.seller.Repository.DeliveryAreaRepo;

public class DeliveryAreaViewModel extends AndroidViewModel {

    private DeliveryAreaRepo deliveryAreaRepo;
    public DeliveryAreaViewModel(@NonNull Application application) {
        super(application);
        deliveryAreaRepo = new DeliveryAreaRepo(application);
    }

    public LiveData<GetDeliveryArea> getDeliveryArea(){

        return deliveryAreaRepo.getDeliveryArea();
    }

    public void updateDeliveryArea(SendDeliveryArea sendDeliveryArea){
        deliveryAreaRepo.updateDeliveryArea(sendDeliveryArea);
    }
    public LiveData<API_RESPONSE> getUpdateDeliveryAreaResponse(){
        return deliveryAreaRepo.getUpdateDeliveryAreaResponse();
    }
}
