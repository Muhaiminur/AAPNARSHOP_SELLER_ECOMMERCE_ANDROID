package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 23,November,2020
 */
public class OfferDetailsRequest {
    @SerializedName("offerId")
    @Expose
    private String offerListId;

    public OfferDetailsRequest(String offerListId) {
        this.offerListId = offerListId;
    }
}
