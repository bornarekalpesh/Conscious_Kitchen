package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PickupTimeResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("pickup_time_list")
    @Expose
    private List<PickupTime> pickupTimeList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PickupTime> getPickupTimeList() {
        return pickupTimeList;
    }

    public void setPickupTimeList(List<PickupTime> pickupTimeList) {
        this.pickupTimeList = pickupTimeList;
    }
}
