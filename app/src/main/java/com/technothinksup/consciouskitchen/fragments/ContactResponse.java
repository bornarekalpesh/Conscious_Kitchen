package com.technothinksup.consciouskitchen.fragments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactResponse {

    @SerializedName("UserId")
    @Expose
    private String customerId;

    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("customerNumber")
    @Expose
    private String customerNumber;

    @SerializedName("customerStatus")
    @Expose
    private String customerStatus;

    @SerializedName("UserName")
    @Expose
    private String UserName;

    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;



    //All Getter and Setter Method..

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }


}
