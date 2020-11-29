package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 17,November,2020
 */
public class OrderStatusRequest {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("reason")
    @Expose
    private String reason;

    public OrderStatusRequest(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
