package com.aapnarshop.seller.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.Rating;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingRepo {
    Application application;
    private MutableLiveData<Rating> ratingMutableLiveData;
    private Utility utility;
    private ApiInterface apiInterface;
    private Gson gson;

    public RatingRepo(Application application) {
        this.application = application;
        ratingMutableLiveData = new MutableLiveData<>();
        utility = new Utility(application);
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        gson = new Gson();
    }

    public LiveData<Rating> getRating() {

        Call<API_RESPONSE> call = apiInterface.getRating(utility.getAuthorizationKey(), "1", KeyWord.TOKEN,utility.getLangauge());
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful() && response != null) {
                    API_RESPONSE apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {

                        Rating rating = gson.fromJson(apiResponse.getData(), Rating.class);
                        ratingMutableLiveData.postValue(rating);

                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return ratingMutableLiveData;
    }
}
