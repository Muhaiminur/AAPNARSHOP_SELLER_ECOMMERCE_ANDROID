package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anik Roy on 18,November,2020
 */
public class OrderDetailsResponse {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("orderProducts")
    @Expose
    private List<OrderProduct> orderProducts = null;
    @SerializedName("deliveryCharge")
    @Expose
    private String deliveryCharge;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("subTotal")
    @Expose
    private String subTotal;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("paymentType")
    @Expose
    private String paymentType;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
}
