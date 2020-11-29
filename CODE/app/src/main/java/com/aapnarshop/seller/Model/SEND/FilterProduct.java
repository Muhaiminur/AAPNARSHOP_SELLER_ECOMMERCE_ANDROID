package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterProduct {

    @SerializedName("filterBy")
    @Expose
    private String filterBy;


    public FilterProduct(String filterBy) {
        this.filterBy = filterBy;
    }

    public String getFilteredBy() {
        return filterBy;
    }
}
