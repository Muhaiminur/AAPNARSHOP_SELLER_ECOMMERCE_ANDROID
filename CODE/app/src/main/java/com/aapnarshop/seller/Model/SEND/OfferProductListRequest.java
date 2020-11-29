package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 25,November,2020
 */
public class OfferProductListRequest {

    @SerializedName("offerId")
    @Expose
    private String offerId;
    @SerializedName("filterBy")
    @Expose
    private String filterBy;

    public OfferProductListRequest(String offerId, String filterBy) {
        this.offerId = offerId;
        this.filterBy = filterBy;
    }
}
