package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PickUpInstructionResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("pickup_instruction")
    @Expose
    private List<PickupInstruction> pickupInstruction = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PickupInstruction> getPickupInstruction() {
        return pickupInstruction;
    }

    public void setPickupInstruction(List<PickupInstruction> pickupInstruction) {
        this.pickupInstruction = pickupInstruction;
    }
}
