package com.aapnarshop.seller.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Profile;
import com.aapnarshop.seller.Model.SEND.LogIn;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInRepo {

    private final ApiInterface apiInterface;
    private final MutableLiveData<API_RESPONSE> profileMutableLiveData;
    private final MutableLiveData<API_RESPONSE> apiResponseMutableLiveData;
    private final Utility utility;


    public SignInRepo(Application application) {
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        profileMutableLiveData = new MutableLiveData<>();
        apiResponseMutableLiveData = new MutableLiveData<>();
        utility = new Utility(application);
    }

    public void sendLoginInfo(LogIn logIn) {
        utility.logger("API Data" + logIn.toString());
        Call<API_RESPONSE> call = apiInterface.login(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge(),logIn);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        utility.logger("Response" + apiResponse.toString());
                        profileMutableLiveData.postValue(apiResponse);

                    }
                } catch (Exception e) {
                    utility.logger("Error Line Number" + Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                utility.logger("Error" + t.getMessage());

            }
        });

    }


    public LiveData<API_RESPONSE> getLoginResponse(){

        return profileMutableLiveData;
    }

    public void forgetPassword(LogIn logIn) {
        Call<API_RESPONSE> call = apiInterface.forgetPassword(utility.getAuthorizationKey(), KeyWord.USERID, KeyWord.TOKEN,utility.getLangauge(),logIn);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        API_RESPONSE apiResponse = response.body();
                        apiResponseMutableLiveData.postValue(apiResponse);

                    }
                } catch (Exception e) {
                    utility.logger("Error Line Number" + Log.getStackTraceString(e));
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                utility.logger("Error" + t.getMessage());

            }
        });

    }
    public LiveData<API_RESPONSE> getForgetPasswordResponse(){

        return apiResponseMutableLiveData;
    }
}
