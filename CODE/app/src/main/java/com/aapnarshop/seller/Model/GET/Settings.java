package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {
    @SerializedName("minimumBuy")
    @Expose
    private String minimumBuy;
    @SerializedName("schedule")
    @Expose
    private Schedule schedule;
    @SerializedName("maxDeliveryTime")
    @Expose
    private String maxDeliveryTime;
    @SerializedName("deliveryCharge")
    @Expose
    private String deliveryCharge;
    @SerializedName("weightLimit")
    @Expose
    private String weightLimit;

    public Settings() {
    }

    public Settings(String minimumBuy, Schedule schedule, String maxDeliveryTime, String deliveryCharge, String weightLimit) {
        this.minimumBuy = minimumBuy;
        this.schedule = schedule;
        this.maxDeliveryTime = maxDeliveryTime;
        this.deliveryCharge = deliveryCharge;
        this.weightLimit = weightLimit;
    }

    public Settings(String minimumBuy, String maxDeliveryTime, String deliveryCharge, String weightLimit) {
        this.minimumBuy = minimumBuy;
        this.maxDeliveryTime = maxDeliveryTime;
        this.deliveryCharge = deliveryCharge;
        this.weightLimit = weightLimit;
    }

    public String getMinimumBuy() {
        return minimumBuy;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public String getWeightLimit() {
        return weightLimit;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "minimumBuy='" + minimumBuy + '\'' +
                ", schedule=" + schedule +
                ", maxDeliveryTime='" + maxDeliveryTime + '\'' +
                ", deliveryCharge='" + deliveryCharge + '\'' +
                ", weightLimit='" + weightLimit + '\'' +
                '}';
    }
}
