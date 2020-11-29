package com.aapnarshop.seller.Repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anik Roy on 22,November,2020
 */

public class OfferRepo {

    private final ApiInterface apiInterface;
    private final MutableLiveData<List<OfferTypeResponse>> offerTypeMutableLiveData;
    private final MutableLiveData<List<OfferListResponse>> offerListMutableLiveData;
    private final MutableLiveData<List<OfferCategory>> offerCategoryMutableLiveData;
    private final MutableLiveData<OfferDetailsResponse> offerDetailsMutableLiveData;
    private final MutableLiveData<List<OfferProductListResponse>> offerProductListMutableLiveData;
    private final MutableLiveData<API_RESPONSE> apiResponseMutableLiveData;
    private final Utility utility;
    private final Gson gson;

    public OfferRepo(Application application) {
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        offerTypeMutableLiveData = new MutableLiveData<>();
        offerListMutableLiveData = new MutableLiveData<>();
        offerCategoryMutableLiveData = new MutableLiveData<>();
        offerDetailsMutableLiveData = new MutableLiveData<>();
        apiResponseMutableLiveData = new MutableLiveData<>();
        offerProductListMutableLiveData = new MutableLiveData<>();
        utility = new Utility(application);
        gson = new Gson();
    }

    public MutableLiveData<List<OfferTypeResponse>> getOfferTypeList() {
        Call<API_RESPONSE> call = apiInterface.getOfferType(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type list = new TypeToken<List<OfferTypeResponse>>() {
                        }.getType();

                        List<OfferTypeResponse> offerTypeResponseList = gson.fromJson(apiResponse.getData(), list);
                        offerTypeMutableLiveData.postValue(offerTypeResponseList);

                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return offerTypeMutableLiveData;
    }

    public MutableLiveData<List<OfferListResponse>> getOfferList(OfferListRequest offerListRequest) {
        Call<API_RESPONSE> call = apiInterface.getOfferList(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge(), offerListRequest);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type list = new TypeToken<List<OfferListResponse>>() {
                        }.getType();
                        List<OfferListResponse> offerListResponseList = gson.fromJson(apiResponse.getData(), list);
                        offerListMutableLiveData.postValue(offerListResponseList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
        return offerListMutableLiveData;
    }

    public MutableLiveData<List<OfferCategory>> getOfferCategory(OfferCategoryRequest offerCategoryRequest) {

        Call<API_RESPONSE> call = apiInterface.getOfferCategory(utility.getAuthorizationKey(), "16", KeyWord.TOKEN, utility.getLangauge(),offerCategoryRequest);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();

                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type list = new TypeToken<List<OfferCategory>>(){}.getType();
                        List<OfferCategory> offerCategoryList = gson.fromJson(apiResponse.getData(),list);
                        offerCategoryMutableLiveData.postValue(offerCategoryList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return offerCategoryMutableLiveData;
    }

    public void sendOfferDetailsRequest(OfferDetailsRequest offerDetailsRequest){
        Call<API_RESPONSE> call = apiInterface.getOfferDetails(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),offerDetailsRequest);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()){
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() ==200){
                        offerDetailsMutableLiveData.postValue(gson.fromJson(apiResponse.getData(),OfferDetailsResponse.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

    }
    public MutableLiveData<OfferDetailsResponse> getOfferDetailsResponse(){

        return offerDetailsMutableLiveData;
    }

    public void updateOfferStatus(OfferStatus offerStatus){

        Call<API_RESPONSE> call = apiInterface.updateOfferStatus(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),offerStatus);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()){
                    apiResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

    }

    public MutableLiveData<API_RESPONSE> createOffer(OfferCreate offerCreate){
        Call<API_RESPONSE> call = apiInterface.createOffer(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),offerCreate);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()){
                   apiResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return apiResponseMutableLiveData;
    }

    public MutableLiveData<API_RESPONSE> updateOffer(OfferUpdate offerUpdate){
        Call<API_RESPONSE> call = apiInterface.updateOffer(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),offerUpdate);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()){
                    apiResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return apiResponseMutableLiveData;
    }

    public MutableLiveData<List<OfferProductListResponse>> getOfferProductList(OfferProductListRequest offerProductListRequest){

        Call<API_RESPONSE> call = apiInterface.getOfferProductList(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),offerProductListRequest);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()){
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null & apiResponse.getCode()==200){
                        Type list = new TypeToken<List<OfferProductListResponse>>(){}.getType();
                        List<OfferProductListResponse> offerProductList = gson.fromJson(apiResponse.getData(),list);
                        offerProductListMutableLiveData.postValue(offerProductList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
        return offerProductListMutableLiveData;
    }

    public void addOfferProduct(AddOfferProduct offerProduct){

        Call<API_RESPONSE> call = apiInterface.addOfferProduct(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),offerProduct);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()){
                    API_RESPONSE apiResponse = response.body();
                    apiResponseMutableLiveData.postValue(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

    }

    public MutableLiveData<API_RESPONSE> getAPIResponse(){

        return apiResponseMutableLiveData;
    }


}
