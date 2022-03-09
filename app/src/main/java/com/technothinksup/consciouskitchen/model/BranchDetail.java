package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchDetail {
    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("branch_name")
    @Expose
    private String branchName;
    @SerializedName("branch_address")
    @Expose
    private String branchAddress;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("branch_mobile")
    @Expose
    private String branchMobile;
    @SerializedName("branch_mobile2")
    @Expose
    private String branchMobile2;
    @SerializedName("branch_email")
    @Expose
    private String branchEmail;
    @SerializedName("branch_website")
    @Expose
    private String branchWebsite;
    @SerializedName("branch_vat_no")
    @Expose
    private String branchVatNo;
    @SerializedName("branch_dl1")
    @Expose
    private String branchDl1;
    @SerializedName("branch_longitude")
    @Expose
    private String branchLongitude;
    @SerializedName("branch_latitude")
    @Expose
    private String branchLatitude;
    @SerializedName("branch_status")
    @Expose
    private String branchStatus;
    @SerializedName("branch_addedby")
    @Expose
    private String branchAddedby;
    @SerializedName("branch_created_at")
    @Expose
    private String branchCreatedAt;
    @SerializedName("branch_updated_at")
    @Expose
    private String branchUpdatedAt;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("mon_sat_time")
    @Expose
    private String monSatTime;
    @SerializedName("sun_time")
    @Expose
    private String sunTime;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBranchMobile() {
        return branchMobile;
    }

    public void setBranchMobile(String branchMobile) {
        this.branchMobile = branchMobile;
    }

    public String getBranchMobile2() {
        return branchMobile2;
    }

    public void setBranchMobile2(String branchMobile2) {
        this.branchMobile2 = branchMobile2;
    }

    public String getBranchEmail() {
        return branchEmail;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail;
    }

    public String getBranchWebsite() {
        return branchWebsite;
    }

    public void setBranchWebsite(String branchWebsite) {
        this.branchWebsite = branchWebsite;
    }

    public String getBranchVatNo() {
        return branchVatNo;
    }

    public void setBranchVatNo(String branchVatNo) {
        this.branchVatNo = branchVatNo;
    }

    public String getBranchDl1() {
        return branchDl1;
    }

    public void setBranchDl1(String branchDl1) {
        this.branchDl1 = branchDl1;
    }

    public String getBranchLongitude() {
        return branchLongitude;
    }

    public void setBranchLongitude(String branchLongitude) {
        this.branchLongitude = branchLongitude;
    }

    public String getBranchLatitude() {
        return branchLatitude;
    }

    public void setBranchLatitude(String branchLatitude) {
        this.branchLatitude = branchLatitude;
    }

    public String getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(String branchStatus) {
        this.branchStatus = branchStatus;
    }

    public String getBranchAddedby() {
        return branchAddedby;
    }

    public void setBranchAddedby(String branchAddedby) {
        this.branchAddedby = branchAddedby;
    }

    public String getBranchCreatedAt() {
        return branchCreatedAt;
    }

    public void setBranchCreatedAt(String branchCreatedAt) {
        this.branchCreatedAt = branchCreatedAt;
    }

    public String getBranchUpdatedAt() {
        return branchUpdatedAt;
    }

    public void setBranchUpdatedAt(String branchUpdatedAt) {
        this.branchUpdatedAt = branchUpdatedAt;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMonSatTime() {
        return monSatTime;
    }

    public void setMonSatTime(String monSatTime) {
        this.monSatTime = monSatTime;
    }

    public String getSunTime() {
        return sunTime;
    }

    public void setSunTime(String sunTime) {
        this.sunTime = sunTime;
    }
    
}
