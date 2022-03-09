package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryInstruction {
    @SerializedName("customer_delivery_ins")
    @Expose
    private String customerDeliveryIns;

    public String getCustomerDeliveryIns() {
        return customerDeliveryIns;
    }

    public void setCustomerDeliveryIns(String customerDeliveryIns) {
        this.customerDeliveryIns = customerDeliveryIns;
    }
}
