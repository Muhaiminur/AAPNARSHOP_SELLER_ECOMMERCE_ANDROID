package com.aapnarshop.seller.Model.SEND;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePassword {

    @SerializedName("oldPin")
    @Expose
    private String oldPassword;
    @SerializedName("newPin")
    @Expose
    private String newPassword;

    public ChangePassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
