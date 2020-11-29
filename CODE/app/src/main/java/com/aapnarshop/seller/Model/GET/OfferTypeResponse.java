package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 22,November,2020
 */
public class OfferTypeResponse {

    @SerializedName("id")
    @Expose
    private String offerTypeId;
    @SerializedName("title")
    @Expose
    private String offerTypeTitle;

    public String getOfferTypeId() {
        return offerTypeId;
    }

    public String getOfferTypeTitle() {
        return offerTypeTitle;
    }
}
