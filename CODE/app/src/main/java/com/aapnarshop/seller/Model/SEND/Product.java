package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("productId")
    @Expose
    private String productId;

    public Product(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
