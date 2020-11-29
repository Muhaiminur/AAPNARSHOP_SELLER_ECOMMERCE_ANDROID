package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Model.GET.OfferCategory;
import com.aapnarshop.seller.Model.GET.OfferDetailsResponse;
import com.aapnarshop.seller.Model.GET.OfferListResponse;
import com.aapnarshop.seller.Model.GET.OfferProductListResponse;
import com.aapnarshop.seller.Model.GET.OfferTypeResponse;
import com.aapnarshop.seller.Model.SEND.AddOfferProduct;
import com.aapnarshop.seller.Model.SEND.OfferCategoryRequest;
import com.aapnarshop.seller.Model.SEND.OfferCreate;
import com.aapnarshop.seller.Model.SEND.OfferDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OfferListRequest;
import com.aapnarshop.seller.Model.SEND.OfferProductListRequest;
import com.aapnarshop.seller.Model.SEND.OfferStatus;
import com.aapnarshop.seller.Model.SEND.OfferUpdate;
import com.aapnarshop.seller.Repository.OfferRepo;

import java.util.List;

/**
 * Created by Anik Roy on 22,November,2020
 */
public class OfferViewModel extends AndroidViewModel {

    private final OfferRepo offerRepo;

    public OfferViewModel(@NonNull Application application) {
        super(application);
        offerRepo = new OfferRepo(application);
    }

    public MutableLiveData<List<OfferTypeResponse>> getOfferTypeList() {
        return offerRepo.getOfferTypeList();
    }

    public MutableLiveData<List<OfferListResponse>> getOfferList(OfferListRequest offerListRequest) {

        return offerRepo.getOfferList(offerListRequest);
    }

    public MutableLiveData<List<OfferCategory>> getOfferCategory(OfferCategoryRequest offerCategoryRequest) {

        return offerRepo.getOfferCategory(offerCategoryRequest);
    }

    public void sendOfferDetailsRequest(OfferDetailsRequest offerDetailsRequest) {
        offerRepo.sendOfferDetailsRequest(offerDetailsRequest);
    }

    public MutableLiveData<OfferDetailsResponse> getOfferDetailsResponse() {
        return offerRepo.getOfferDetailsResponse();
    }

    public void updateOfferStatus(OfferStatus offerStatus) {

        offerRepo.updateOfferStatus(offerStatus);
    }
    public MutableLiveData<API_RESPONSE> createOffer(OfferCreate offerCreate){
        return offerRepo.createOffer(offerCreate);
    }
    public MutableLiveData<API_RESPONSE> updateOffer(OfferUpdate offerUpdate){
        return offerRepo.updateOffer(offerUpdate);
    }

    public MutableLiveData<List<OfferProductListResponse>> getOfferProductList(OfferProductListRequest offerProductListRequest){
        return offerRepo.getOfferProductList(offerProductListRequest);
    }

    public void addOfferProduct(AddOfferProduct offerProduct){

        offerRepo.addOfferProduct(offerProduct);
    }

    public MutableLiveData<API_RESPONSE> getAPIResponse(){

        return offerRepo.getAPIResponse();
    }

}
