package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 23,November,2020
 */
public class OfferCategory {

    @SerializedName("id")
    @Expose
    private String offerCategoryId;
    @SerializedName("title")
    @Expose
    private String offerCategoryTitle;

    public String getOfferCategoryId() {
        return offerCategoryId;
    }

    public String getOfferCategoryTitle() {
        return offerCategoryTitle;
    }
}
