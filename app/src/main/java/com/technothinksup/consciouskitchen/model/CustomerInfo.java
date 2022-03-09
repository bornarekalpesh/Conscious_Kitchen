package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfo {
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_lname")
    @Expose
    private String customerLname;
    @SerializedName("customer_mobile")
    @Expose
    private String customerMobile;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_address")
    @Expose
    private String customerAddress;
    @SerializedName("customer_password")
    @Expose
    private String customerPassword;
    @SerializedName("customer_status")
    @Expose
    private String customerStatus;
    @SerializedName("customer_image")
    @Expose
    private String customerImage;
    @SerializedName("oauth_provider")
    @Expose
    private String oauthProvider;
    @SerializedName("oauth_uid")
    @Expose
    private String oauthUid;

    @SerializedName("customer_dob")
    @Expose
    private String customer_dob;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLname() {
        return customerLname;
    }

    public void setCustomerLname(String customerLname) {
        this.customerLname = customerLname;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthUid() {
        return oauthUid;
    }


    public void setOauthUid(String oauthUid) {
        this.oauthUid = oauthUid;
    }

    public String getCustomer_dob() {
        return customer_dob;
    }

    public void setCustomer_dob(String customer_dob) {
        this.customer_dob = customer_dob;
    }
}
