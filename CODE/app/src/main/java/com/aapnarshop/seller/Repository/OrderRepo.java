package com.aapnarshop.seller.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Http.ApiClient;
import com.aapnarshop.seller.Http.ApiInterface;
import com.aapnarshop.seller.Library.KeyWord;
import com.aapnarshop.seller.Library.Utility;
import com.aapnarshop.seller.Model.GET.OrderDetailsResponse;
import com.aapnarshop.seller.Model.GET.OrderListResponse;
import com.aapnarshop.seller.Model.SEND.OrderDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OrderListRequest;
import com.aapnarshop.seller.Model.SEND.OrderStatusRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anik Roy on 17,November,2020
 */
public class OrderRepo {

    private Context context;
    private final MutableLiveData<List<OrderListResponse>> orderListMutableLiveData;
    private final MutableLiveData<OrderDetailsResponse> orderDetailsMutableLiveData;
    private final MutableLiveData<API_RESPONSE> apiResponseMutableLiveData;
    private final Utility utility;
    private final ApiInterface apiInterface;
    private Gson gson;

    public OrderRepo(Context context) {
        this.context = context;
        orderListMutableLiveData = new MutableLiveData<>();
        apiResponseMutableLiveData = new MutableLiveData<>();
        orderDetailsMutableLiveData = new MutableLiveData<>();
        utility = new Utility(context);
        apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
        gson = new Gson();
    }

    public MutableLiveData<List<OrderListResponse>> getOrderList(OrderListRequest orderListRequest) {
        Call<API_RESPONSE> call = apiInterface.getOrderList(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), orderListRequest);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    API_RESPONSE apiResponse = response.body();

                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Type listType = new TypeToken<List<OrderListResponse>>() {
                        }.getType();
                        List<OrderListResponse> orderList = gson.fromJson(apiResponse.getData(), listType);
                        orderListMutableLiveData.postValue(orderList);
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
        return orderListMutableLiveData;
    }

    public void updateOrderStatus(OrderStatusRequest orderStatusRequest) {
        Call<API_RESPONSE> call = apiInterface.updateOrderStatus(utility.getAuthorizationKey(), "1", KeyWord.TOKEN, utility.getLangauge(), orderStatusRequest);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()) {
                    apiResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<API_RESPONSE> getOrderStatusResponse() {

        return apiResponseMutableLiveData;
    }

    public MutableLiveData<OrderDetailsResponse> getOrderDetails(OrderDetailsRequest orderDetailsRequest){
        Call<API_RESPONSE> call = apiInterface.getOrderDetails(utility.getAuthorizationKey(),"1",KeyWord.TOKEN,utility.getLangauge(),orderDetailsRequest);
        call.enqueue(new Callback<API_RESPONSE>() {
            @Override
            public void onResponse(Call<API_RESPONSE> call, Response<API_RESPONSE> response) {
                if (response.isSuccessful()){
                    API_RESPONSE apiResponse = response.body();

                    if (apiResponse != null && apiResponse.getCode()==200){

                        orderDetailsMutableLiveData.postValue(gson.fromJson(apiResponse.getData(),OrderDetailsResponse.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<API_RESPONSE> call, Throwable t) {

            }
        });

        return orderDetailsMutableLiveData;
    }

}
