package com.aapnarshop.seller.Repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.GetConfig;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPageRepo {

    private MutableLiveData<GetConfig> configMutableLiveData;
    private MutableLiveData<Boolean> progressbarMutableLiveData;
    private ApiInterface apiInterface;
    private Utility utility;
    private Gson gson;
    private GetConfig config;


    public LandingPageRepo(Application application) {
        configMutableLiveData = new MutableLiveData<>();
        progressbarMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        utility = new Utility(application);
        gson = new Gson();
    }

    public LiveData<GetConfig> getConfigLiveData() {
        progressbarMutableLiveData.postValue(true);
        Call<API_RESPONSE> call = apiInterface.getConfig(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE api_response = response.body();

                        if (api_response != null && api_response.getCode() == 200) {
                            config = gson.fromJson(api_response.getData().toString(), GetConfig.class);
                            configMutableLiveData.postValue(config);
                            progressbarMutableLiveData.postValue(false);
                            utility.logger("Get Config" + config.toString());

                        } else {
                            configMutableLiveData.postValue(config);
                            utility.logger("Get Config" + config.toString());
                        }
                    } else {
                        utility.logger("Config failed");
                    }
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                }

            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                progressbarMutableLiveData.postValue(false);
                Log.d("Error", t.toString());
            }
        });

        return configMutableLiveData;
    }


    public MutableLiveData<Boolean> getProgress() {
        return progressbarMutableLiveData;
    }
}
