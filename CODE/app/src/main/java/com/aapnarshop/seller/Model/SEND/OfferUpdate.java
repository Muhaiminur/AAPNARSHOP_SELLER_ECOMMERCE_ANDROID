package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 24,November,2020
 */
public class OfferUpdate {

    @SerializedName("offerId")
    @Expose
    private String offerId;
    @SerializedName("typeId")
    @Expose
    private String offerTypeId;
    @SerializedName("categoryId")
    @Expose
    private String offerCategoryId;
    @SerializedName("title")
    @Expose
    private String offerName;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("minAmount")
    @Expose
    private String minAmount;
    @SerializedName("maxAmount")
    @Expose
    private String maxDiscount;
    @SerializedName("keyword")
    @Expose
    private String generatedCode;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;

    public OfferUpdate(String offerId, String offerTypeId, String offerCategoryId, String offerName, String amount, String minAmount, String maxDiscount, String generatedCode, String startTime, String endTime) {
        this.offerId = offerId;
        this.offerTypeId = offerTypeId;
        this.offerCategoryId = offerCategoryId;
        this.offerName = offerName;
        this.amount = amount;
        this.minAmount = minAmount;
        this.maxDiscount = maxDiscount;
        this.generatedCode = generatedCode;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
