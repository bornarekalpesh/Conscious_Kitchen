package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCustomerBalance {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("wallet_balance")
    @Expose
    private double walletBalance;
    @SerializedName("reward_balance")
    @Expose
    private double rewardBalance;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public double getRewardBalance() {
        return rewardBalance;
    }

    public void setRewardBalance(double rewardBalance) {
        this.rewardBalance = rewardBalance;
    }
}
