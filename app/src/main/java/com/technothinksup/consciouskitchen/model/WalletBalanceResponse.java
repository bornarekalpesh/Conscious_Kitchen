package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletBalanceResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("wallet_balance")
    @Expose
    private Integer walletBalance;
    @SerializedName("reward_balance")
    @Expose
    private Integer rewardBalance;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Integer walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Integer getRewardBalance() {
        return rewardBalance;
    }

    public void setRewardBalance(Integer rewardBalance) {
        this.rewardBalance = rewardBalance;
    }

}
