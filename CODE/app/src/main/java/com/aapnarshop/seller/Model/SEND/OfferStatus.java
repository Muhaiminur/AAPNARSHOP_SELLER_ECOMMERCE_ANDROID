package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 23,November,2020
 */
public class OfferStatus {

    @SerializedName("offerId")
    @Expose
    private String offerId;
    @SerializedName("action")
    @Expose
    private String offerStatus;

    public OfferStatus(String offerId, String offerStatus) {
        this.offerId = offerId;
        this.offerStatus = offerStatus;
    }
}
