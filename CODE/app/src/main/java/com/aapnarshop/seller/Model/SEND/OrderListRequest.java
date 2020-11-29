package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 17,November,2020
 */
public class OrderListRequest {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("keyword")
    @Expose
    private String keyword;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    public OrderListRequest() {
    }

    public OrderListRequest(String type, String keyword, String startDate, String endDate) {
        this.type = type;
        this.keyword = keyword;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
