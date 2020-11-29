package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 23,November,2020
 */
public class OfferListResponse {
    @SerializedName("id")
    @Expose
    public String offerListId;
    @SerializedName("title")
    @Expose
    private String offerListTitle;

    public OfferListResponse(String offerListId, String offerListTitle) {
        this.offerListId = offerListId;
        this.offerListTitle = offerListTitle;
    }

    public String getOfferListId() {
        return offerListId;
    }

    public String getOfferListTitle() {
        return offerListTitle;
    }
}
