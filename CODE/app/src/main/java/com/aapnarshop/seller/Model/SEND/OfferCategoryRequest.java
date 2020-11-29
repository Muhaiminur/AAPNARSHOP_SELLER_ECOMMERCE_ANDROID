package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 25,November,2020
 */
public class OfferCategoryRequest {

    @SerializedName("offerTypeId")
    @Expose
    private String offerTypeId;

    public OfferCategoryRequest(String offerTypeId) {
        this.offerTypeId = offerTypeId;
    }
}
