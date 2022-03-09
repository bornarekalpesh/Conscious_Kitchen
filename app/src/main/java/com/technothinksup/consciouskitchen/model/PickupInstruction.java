package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PickupInstruction {
    @SerializedName("customer_pickup_ins")
    @Expose
    private String customerPickupIns;

    public String getCustomerPickupIns() {
        return customerPickupIns;
    }

    public void setCustomerPickupIns(String customerPickupIns) {
        this.customerPickupIns = customerPickupIns;
    }
}
