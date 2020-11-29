package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllProduct {
    @SerializedName("id")
    @Expose
    private String productId;
    @SerializedName("title")
    @Expose
    private String productTitle;
    @SerializedName("image")
    @Expose
    private String productImage;
    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("remaining")
    @Expose
    private String remainingProduct;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemainingProduct() {
        return remainingProduct;
    }

    public void setRemainingProduct(String remainingProduct) {
        this.remainingProduct = remainingProduct;
    }
}
