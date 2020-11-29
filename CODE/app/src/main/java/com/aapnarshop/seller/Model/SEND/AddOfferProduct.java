package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 25,November,2020
 */
public class AddOfferProduct {
    @SerializedName("offerId")
    @Expose
    private String offerId;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("status")
    @Expose
    private String status;

    public AddOfferProduct(String offerId, String productId, String status) {
        this.offerId = offerId;
        this.productId = productId;
        this.status = status;
    }
}
