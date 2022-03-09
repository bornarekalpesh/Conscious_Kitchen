package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustAddress {
    @SerializedName("cust_address_id")
    @Expose
    private String custAddressId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("cust_name")
    @Expose
    private String custName;
    @SerializedName("cust_address")
    @Expose
    private String custAddress;
    @SerializedName("cust_zipcode")
    @Expose
    private String custZipcode;
    @SerializedName("delivery_instruction")
    @Expose
    private String deliveryInstruction;

    public String getCustAddressId() {
        return custAddressId;
    }

    public void setCustAddressId(String custAddressId) {
        this.custAddressId = custAddressId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustZipcode() {
        return custZipcode;
    }

    public void setCustZipcode(String custZipcode) {
        this.custZipcode = custZipcode;
    }

    public String getDeliveryInstruction() {
        return deliveryInstruction;
    }

    public void setDeliveryInstruction(String deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
    }
}
