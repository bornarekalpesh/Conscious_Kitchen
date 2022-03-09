package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("reward_point_use")
    @Expose
    private String rewardPointUse;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRewardPointUse() {
        return rewardPointUse;
    }

    public void setRewardPointUse(String rewardPointUse) {
        this.rewardPointUse = rewardPointUse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
