package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductQuantity {

    @SerializedName("productId")
    @Expose
    private String productId;

    @SerializedName("quantity")
    @Expose
    private String productQuantity;

    public ProductQuantity(String productId, String productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }
}
