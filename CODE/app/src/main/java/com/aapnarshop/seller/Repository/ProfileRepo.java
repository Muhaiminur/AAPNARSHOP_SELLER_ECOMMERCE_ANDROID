package com.aapnarshop.seller.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.ProductDetails;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepo {

    private Application application;
    private Utility utility;
    private ApiInterface apiInterface;
    private MutableLiveData<API_RESPONSE> apiResponseMutableLiveData;

    public ProfileRepo(Application application) {
        this.application = application;
        utility = new Utility(application);
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        apiResponseMutableLiveData = new MutableLiveData<>();
    }

    public void updateSellerProfileImage(MultipartBody.Part image){

        utility.logger("Image"+image.toString());


        Call<API_RESPONSE> call = apiInterface.updateProfileImage(utility.getAuthorizationKey(),"16", KeyWord.TOKEN,utility.getLangauge(),image);

        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                utility.logger("response"+response.toString());
                if (response.isSuccessful() && response!= null){
                    apiResponseMutableLiveData.postValue(response.body());
                    utility.logger("Get Response:"+response.body().getCode());
                }else {
                    API_RESPONSE api_response = response.body();
                    if (api_response != null) {
                        utility.logger("Failed response"+api_response.getMessage());

                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {
                utility.logger("Fail"+t.getMessage());

            }
        });
    }

    public LiveData<API_RESPONSE> getProfileImageUploadResponse(){

        return apiResponseMutableLiveData;
    }
}
