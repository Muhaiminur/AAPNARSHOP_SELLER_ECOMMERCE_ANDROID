package com.aapnarshop.seller.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Schedule;
import com.aapnarshop.seller.Model.GET.Settings;
import com.aapnarshop.seller.Model.SEND.ChangePassword;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsRepo {
    private Application application;
    private MutableLiveData<Settings> settingsMutableLiveData;
    private MutableLiveData<API_RESPONSE> apiResponseMutableLiveData;

    private ApiInterface apiInterface;
    private Utility utility;
    private Gson gson;

    public SettingsRepo(Application application) {
        this.application = application;
        settingsMutableLiveData = new MutableLiveData<>();
        apiResponseMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        utility = new Utility(application);
        gson = new Gson();
    }

    public LiveData<Settings> getSettings(){
        Call<API_RESPONSE> call = apiInterface.getSettings(utility.getAuthorizationKey(),"16", KeyWord.TOKEN,utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful() && response!=null){
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode()==200){
                        Settings settings = gson.fromJson(apiResponse.getData(),Settings.class);
                        settingsMutableLiveData.postValue(settings);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                utility.logger("Get Setting"+t.getMessage());
            }
        });

        return settingsMutableLiveData;
    }

    public void changePassword(ChangePassword changePassword){
        Call<API_RESPONSE> call = apiInterface.changePassword(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),changePassword);
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

    public void updateSettings(Settings settings){

        Call<API_RESPONSE> call = apiInterface.updateSettings(utility.getAuthorizationKey(),"16",KeyWord.TOKEN,utility.getLangauge(),settings);
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

    public LiveData<API_RESPONSE> getChangePasswordResponse(){

        return apiResponseMutableLiveData;
    }
}
