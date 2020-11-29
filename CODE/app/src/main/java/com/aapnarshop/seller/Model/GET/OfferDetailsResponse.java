package com.aapnarshop.seller.Model.GET;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anik Roy on 23,November,2020
 */
public class OfferDetailsResponse implements Parcelable {
    @SerializedName("typeResponse")
    @Expose
    private OfferTypeResponse offerTypeResponse;
    @SerializedName("categoryResponse")
    @Expose
    private OfferCategory offerCategory;
    @SerializedName("minAmount")
    @Expose
    private String minAmount;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("title")
    @Expose
    private String offerName;
    @SerializedName("keyword")
    @Expose
    private String generatedCode;
    @SerializedName("maxAmount")
    @Expose
    private String maxAmount;
    @SerializedName("status")
    @Expose
    private String status;

    protected OfferDetailsResponse(Parcel in) {
        minAmount = in.readString();
        amount = in.readString();
        startTime = in.readString();
        id = in.readString();
        endTime = in.readString();
        offerName = in.readString();
        generatedCode = in.readString();
        maxAmount = in.readString();
        status = in.readString();
    }

    public static final Creator<OfferDetailsResponse> CREATOR = new Creator<OfferDetailsResponse>() {
        @Override
        public OfferDetailsResponse createFromParcel(Parcel in) {
            return new OfferDetailsResponse(in);
        }

        @Override
        public OfferDetailsResponse[] newArray(int size) {
            return new OfferDetailsResponse[size];
        }
    };

    public OfferTypeResponse getOfferTypeResponse() {
        return offerTypeResponse;
    }

    public OfferCategory getOfferCategory() {
        return offerCategory;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public String getAmount() {
        return amount;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getId() {
        return id;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getOfferName() {
        return offerName;
    }

    public String getGeneratedCode() {
        return generatedCode;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(minAmount);
        dest.writeString(amount);
        dest.writeString(startTime);
        dest.writeString(id);
        dest.writeString(endTime);
        dest.writeString(offerName);
        dest.writeString(generatedCode);
        dest.writeString(maxAmount);
        dest.writeString(status);
    }
}
