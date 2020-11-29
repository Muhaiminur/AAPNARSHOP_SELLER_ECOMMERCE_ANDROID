package com.aapnarshop.seller.Model.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetConfig {

    @SerializedName("UPDATE_TYPE")
    @Expose
    private String updateType;
    @SerializedName("VERSION_CODE")
    @Expose
    private String versionCode;
    @SerializedName("MESSAGE_BODY")
    @Expose
    private String messageBody;
    @SerializedName("MESSAGE_SHOW")
    @Expose
    private String messageShow;

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageShow() {
        return messageShow;
    }

    public void setMessageShow(String messageShow) {
        this.messageShow = messageShow;
    }

    @Override
    public String toString() {
        return "GetConfig{" +
                "updateType='" + updateType + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", messageShow='" + messageShow + '\'' +
                '}';
    }
}
