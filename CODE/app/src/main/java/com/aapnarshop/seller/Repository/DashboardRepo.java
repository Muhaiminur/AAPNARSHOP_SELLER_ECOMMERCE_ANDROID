package com.aapnarshop.seller.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.ProductCount;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepo {

    private final MutableLiveData<ProductCount> productCountMutableLiveData;
    private final ApiInterface apiInterface;
    private final Utility utility;
    private final Gson gson;

    public DashboardRepo(Application application) {
        productCountMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        utility = new Utility(application);
        gson = new Gson();
    }

    public LiveData<ProductCount> getProductCount(){

        Call<API_RESPONSE> call = apiInterface.getDashboardInfo(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try{
                    if (response.isSuccessful() && response !=null){
                        API_RESPONSE apiResponse = response.body();
                        if (apiResponse != null && apiResponse.getCode() ==200){
                           ProductCount productCount = gson.fromJson(apiResponse.getData(),ProductCount.class);
                           productCountMutableLiveData.postValue(productCount);
                        }
                    }
                }
                catch(Exception e){
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });


        return productCountMutableLiveData;
    }
}
