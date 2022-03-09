package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryInstructionResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("delivery_instruction")
    @Expose
    private List<DeliveryInstruction> deliveryInstruction = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DeliveryInstruction> getDeliveryInstruction() {
        return deliveryInstruction;
    }

    public void setDeliveryInstruction(List<DeliveryInstruction> deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
    }

}
