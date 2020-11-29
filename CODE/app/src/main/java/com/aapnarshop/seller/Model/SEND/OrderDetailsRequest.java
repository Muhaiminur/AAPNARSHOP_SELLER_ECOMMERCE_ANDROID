package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 18,November,2020
 */
public class OrderDetailsRequest {
    @SerializedName("orderId")
    @Expose
    private String orderId;

    public OrderDetailsRequest(String orderId) {
        this.orderId = orderId;
    }
}
