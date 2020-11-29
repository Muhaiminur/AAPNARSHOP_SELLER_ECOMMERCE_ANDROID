package com.aapnarshop.seller.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.GetDeliveryArea;
import com.aapnarshop.seller.Model.SEND.SendDeliveryArea;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryAreaRepo {

    private Application application;
    private MutableLiveData<GetDeliveryArea> getDeliveryAreaMutableLiveData;
    private MutableLiveData<API_RESPONSE> apiResponseMutableLiveData;
    private Utility utility;
    private ApiInterface apiInterface;
    private Gson gson;

    public DeliveryAreaRepo(Application application) {
        this.application = application;
        getDeliveryAreaMutableLiveData = new MutableLiveData<>();
        apiResponseMutableLiveData = new MutableLiveData<>();
        utility = new Utility(application);
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        gson = new Gson();
    }


    public LiveData<GetDeliveryArea> getDeliveryArea(){

        Call<API_RESPONSE> call = apiInterface.getDeliveryArea(utility.getAuthorizationKey(),"16", KeyWord.TOKEN,utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful() && response!=null){
                    API_RESPONSE apiResponse = response.body();

                    if (apiResponse != null && apiResponse.getCode() == 200){

                        GetDeliveryArea getDeliveryArea = gson.fromJson(apiResponse.getData(), GetDeliveryArea.class);
                        getDeliveryAreaMutableLiveData.postValue(getDeliveryArea);
                    }


                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return getDeliveryAreaMutableLiveData;
    }

    public void updateDeliveryArea(SendDeliveryArea sendDeliveryArea){
        Call<API_RESPONSE> call = apiInterface.updateDeliveryArea(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),sendDeliveryArea);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful() && response!=null){
                    apiResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public LiveData<API_RESPONSE> getUpdateDeliveryAreaResponse(){

        return apiResponseMutableLiveData;
    }


}
