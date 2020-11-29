package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 23,November,2020
 */
public class OfferListRequest {
    @SerializedName("timeline")
    @Expose
    private String timeLine;
    @SerializedName("typeId")
    @Expose
    private String offerTypeId;

    public OfferListRequest(String timeLine, String offerTypeId) {
        this.timeLine = timeLine;
        this.offerTypeId = offerTypeId;
    }
}
