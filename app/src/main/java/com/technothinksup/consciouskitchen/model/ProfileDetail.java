package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetail {
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("customer_ref_code")
    @Expose
    private String customerRefCode;
    @SerializedName("oauth_provider")
    @Expose
    private String oauthProvider;
    @SerializedName("oauth_uid")
    @Expose
    private String oauthUid;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_lname")
    @Expose
    private String customerLname;
    @SerializedName("customer_dob")
    @Expose
    private String customerDob;
    @SerializedName("customer_gender")
    @Expose
    private String customerGender;
    @SerializedName("customer_address")
    @Expose
    private String customerAddress;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("district_id")
    @Expose
    private Object districtId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("customer_pincode")
    @Expose
    private String customerPincode;
    @SerializedName("customer_statecode")
    @Expose
    private String customerStatecode;
    @SerializedName("customer_mobile")
    @Expose
    private String customerMobile;
    @SerializedName("customer_password")
    @Expose
    private String customerPassword;
    @SerializedName("customer_mobile2")
    @Expose
    private String customerMobile2;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_website")
    @Expose
    private String customerWebsite;
    @SerializedName("customer_pan_no")
    @Expose
    private String customerPanNo;
    @SerializedName("customer_gst_no")
    @Expose
    private String customerGstNo;
    @SerializedName("customer_vat_no")
    @Expose
    private Object customerVatNo;
    @SerializedName("customer_cst_no")
    @Expose
    private Object customerCstNo;
    @SerializedName("customer_lic1")
    @Expose
    private String customerLic1;
    @SerializedName("customer_lic2")
    @Expose
    private String customerLic2;
    @SerializedName("customer_op_crd_balance")
    @Expose
    private String customerOpCrdBalance;
    @SerializedName("customer_text_notify")
    @Expose
    private String customerTextNotify;
    @SerializedName("customer_email_notify")
    @Expose
    private String customerEmailNotify;
    @SerializedName("customer_push_notify")
    @Expose
    private String customerPushNotify;
    @SerializedName("customer_pickup_ins")
    @Expose
    private String customerPickupIns;
    @SerializedName("customer_curbside_ins")
    @Expose
    private String customerCurbsideIns;
    @SerializedName("customer_delivery_ins")
    @Expose
    private String customerDeliveryIns;
    @SerializedName("customer_tot_rewards")
    @Expose
    private String customerTotRewards;
    @SerializedName("customer_tot_referrals")
    @Expose
    private String customerTotReferrals;
    @SerializedName("customer_image")
    @Expose
    private String customerImage;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("default_branch_id")
    @Expose
    private String defaultBranchId;
    @SerializedName("customer_status")
    @Expose
    private String customerStatus;
    @SerializedName("customer_addedby")
    @Expose
    private String customerAddedby;
    @SerializedName("customer_created_at")
    @Expose
    private String customerCreatedAt;
    @SerializedName("customer_updated_at")
    @Expose
    private String customerUpdatedAt;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCustomerRefCode() {
        return customerRefCode;
    }

    public void setCustomerRefCode(String customerRefCode) {
        this.customerRefCode = customerRefCode;
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

    public String getCustomerDob() {
        return customerDob;
    }

    public void setCustomerDob(String customerDob) {
        this.customerDob = customerDob;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Object getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Object districtId) {
        this.districtId = districtId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCustomerPincode() {
        return customerPincode;
    }

    public void setCustomerPincode(String customerPincode) {
        this.customerPincode = customerPincode;
    }

    public String getCustomerStatecode() {
        return customerStatecode;
    }

    public void setCustomerStatecode(String customerStatecode) {
        this.customerStatecode = customerStatecode;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerMobile2() {
        return customerMobile2;
    }

    public void setCustomerMobile2(String customerMobile2) {
        this.customerMobile2 = customerMobile2;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerWebsite() {
        return customerWebsite;
    }

    public void setCustomerWebsite(String customerWebsite) {
        this.customerWebsite = customerWebsite;
    }

    public String getCustomerPanNo() {
        return customerPanNo;
    }

    public void setCustomerPanNo(String customerPanNo) {
        this.customerPanNo = customerPanNo;
    }

    public String getCustomerGstNo() {
        return customerGstNo;
    }

    public void setCustomerGstNo(String customerGstNo) {
        this.customerGstNo = customerGstNo;
    }

    public Object getCustomerVatNo() {
        return customerVatNo;
    }

    public void setCustomerVatNo(Object customerVatNo) {
        this.customerVatNo = customerVatNo;
    }

    public Object getCustomerCstNo() {
        return customerCstNo;
    }

    public void setCustomerCstNo(Object customerCstNo) {
        this.customerCstNo = customerCstNo;
    }

    public String getCustomerLic1() {
        return customerLic1;
    }

    public void setCustomerLic1(String customerLic1) {
        this.customerLic1 = customerLic1;
    }

    public String getCustomerLic2() {
        return customerLic2;
    }

    public void setCustomerLic2(String customerLic2) {
        this.customerLic2 = customerLic2;
    }

    public String getCustomerOpCrdBalance() {
        return customerOpCrdBalance;
    }

    public void setCustomerOpCrdBalance(String customerOpCrdBalance) {
        this.customerOpCrdBalance = customerOpCrdBalance;
    }

    public String getCustomerTextNotify() {
        return customerTextNotify;
    }

    public void setCustomerTextNotify(String customerTextNotify) {
        this.customerTextNotify = customerTextNotify;
    }

    public String getCustomerEmailNotify() {
        return customerEmailNotify;
    }

    public void setCustomerEmailNotify(String customerEmailNotify) {
        this.customerEmailNotify = customerEmailNotify;
    }

    public String getCustomerPushNotify() {
        return customerPushNotify;
    }

    public void setCustomerPushNotify(String customerPushNotify) {
        this.customerPushNotify = customerPushNotify;
    }

    public String getCustomerPickupIns() {
        return customerPickupIns;
    }

    public void setCustomerPickupIns(String customerPickupIns) {
        this.customerPickupIns = customerPickupIns;
    }

    public String getCustomerCurbsideIns() {
        return customerCurbsideIns;
    }

    public void setCustomerCurbsideIns(String customerCurbsideIns) {
        this.customerCurbsideIns = customerCurbsideIns;
    }

    public String getCustomerDeliveryIns() {
        return customerDeliveryIns;
    }

    public void setCustomerDeliveryIns(String customerDeliveryIns) {
        this.customerDeliveryIns = customerDeliveryIns;
    }

    public String getCustomerTotRewards() {
        return customerTotRewards;
    }

    public void setCustomerTotRewards(String customerTotRewards) {
        this.customerTotRewards = customerTotRewards;
    }

    public String getCustomerTotReferrals() {
        return customerTotReferrals;
    }

    public void setCustomerTotReferrals(String customerTotReferrals) {
        this.customerTotReferrals = customerTotReferrals;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDefaultBranchId() {
        return defaultBranchId;
    }

    public void setDefaultBranchId(String defaultBranchId) {
        this.defaultBranchId = defaultBranchId;
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerAddedby() {
        return customerAddedby;
    }

    public void setCustomerAddedby(String customerAddedby) {
        this.customerAddedby = customerAddedby;
    }

    public String getCustomerCreatedAt() {
        return customerCreatedAt;
    }

    public void setCustomerCreatedAt(String customerCreatedAt) {
        this.customerCreatedAt = customerCreatedAt;
    }

    public String getCustomerUpdatedAt() {
        return customerUpdatedAt;
    }

    public void setCustomerUpdatedAt(String customerUpdatedAt) {
        this.customerUpdatedAt = customerUpdatedAt;
    }

}
