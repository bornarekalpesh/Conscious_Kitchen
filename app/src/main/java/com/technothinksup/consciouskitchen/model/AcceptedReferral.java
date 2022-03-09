package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptedReferral {
    @SerializedName("referral_id")
    @Expose
    private String referralId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("ref_person_name")
    @Expose
    private String refPersonName;
    @SerializedName("ref_person_mobile")
    @Expose
    private String refPersonMobile;
    @SerializedName("is_ref_used")
    @Expose
    private String isRefUsed;
    @SerializedName("ref_person_customer_id")
    @Expose
    private String refPersonCustomerId;
    @SerializedName("ref_person_customer_name")
    @Expose
    private String refPersonCustomerName;
    @SerializedName("referral_created_at")
    @Expose
    private String referralCreatedAt;
    @SerializedName("referral_joined_at")
    @Expose
    private String referralJoinedAt;

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRefPersonName() {
        return refPersonName;
    }

    public void setRefPersonName(String refPersonName) {
        this.refPersonName = refPersonName;
    }

    public String getRefPersonMobile() {
        return refPersonMobile;
    }

    public void setRefPersonMobile(String refPersonMobile) {
        this.refPersonMobile = refPersonMobile;
    }

    public String getIsRefUsed() {
        return isRefUsed;
    }

    public void setIsRefUsed(String isRefUsed) {
        this.isRefUsed = isRefUsed;
    }

    public String getRefPersonCustomerId() {
        return refPersonCustomerId;
    }

    public void setRefPersonCustomerId(String refPersonCustomerId) {
        this.refPersonCustomerId = refPersonCustomerId;
    }

    public String getRefPersonCustomerName() {
        return refPersonCustomerName;
    }

    public void setRefPersonCustomerName(String refPersonCustomerName) {
        this.refPersonCustomerName = refPersonCustomerName;
    }

    public String getReferralCreatedAt() {
        return referralCreatedAt;
    }

    public void setReferralCreatedAt(String referralCreatedAt) {
        this.referralCreatedAt = referralCreatedAt;
    }

    public String getReferralJoinedAt() {
        return referralJoinedAt;
    }

    public void setReferralJoinedAt(String referralJoinedAt) {
        this.referralJoinedAt = referralJoinedAt;
    }

}
