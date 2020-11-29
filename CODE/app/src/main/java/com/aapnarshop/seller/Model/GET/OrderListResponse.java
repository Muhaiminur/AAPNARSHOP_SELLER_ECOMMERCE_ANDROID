package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 17,November,2020
 */
public class OrderListResponse {

    @SerializedName("id")
    @Expose
    private String orderId;
    @SerializedName("name")
    @Expose
    private String buyerName;
    @SerializedName("buyerProfilePicture")
    @Expose
    private String buyerProfileImage;
    @SerializedName("phone")
    @Expose
    private String buyerPhone;
    @SerializedName("status")
    @Expose
    private String orderStatus;
    @SerializedName("transactionId")
    @Expose
    private String orderTransactionId;
    @SerializedName("reason")
    @Expose
    private String orderReason;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getOrderId() {
        return orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerProfileImage() {
        return buyerProfileImage;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderTransactionId() {
        return orderTransactionId;
    }

    public String getOrderReason() {
        return orderReason;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
