package com.aapnarshop.seller.VIEWMODEL;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aapnarshop.seller.Http.API_RESPONSE;
import com.aapnarshop.seller.Model.GET.OrderDetailsResponse;
import com.aapnarshop.seller.Model.GET.OrderListResponse;
import com.aapnarshop.seller.Model.SEND.OrderDetailsRequest;
import com.aapnarshop.seller.Model.SEND.OrderListRequest;
import com.aapnarshop.seller.Model.SEND.OrderStatusRequest;
import com.aapnarshop.seller.Repository.OrderRepo;

import java.util.List;

/**
 * Created by Anik Roy on 17,November,2020
 */

public class OrderViewModel extends AndroidViewModel {

    private final OrderRepo orderRepo;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepo = new OrderRepo(application);
    }

    public MutableLiveData<List<OrderListResponse>> getOrderList(OrderListRequest orderListRequest) {

        return orderRepo.getOrderList(orderListRequest);
    }

    public void updateOrderStatus(OrderStatusRequest orderStatusRequest) {

        orderRepo.updateOrderStatus(orderStatusRequest);
    }

    public MutableLiveData<API_RESPONSE> getOrderStatusResponse() {
        return orderRepo.getOrderStatusResponse();
    }
    public MutableLiveData<OrderDetailsResponse> getOrderDetails(OrderDetailsRequest orderDetailsRequest){

        return orderRepo.getOrderDetails(orderDetailsRequest);
    }
}
