package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchTimeModel {

    @SerializedName("company_time_id")
    private String company_time_id;
   @SerializedName("company_id")
    private String company_id;
    @SerializedName("branch_id")
    private String branch_id;
    @SerializedName("day_name")
    private String day_name;
    @SerializedName("is_holiday")
    private String is_holiday;
    @SerializedName("from_time")
    private String from_time;
    @SerializedName("to_time")
    private String to_time;
   @SerializedName("company_time_addedby")
    private String company_time_addedby;
    @SerializedName("company_time_created_at")
    private String company_time_created_at;
    @SerializedName("company_time_updated_at")
    private String company_time_updated_at;


    public String getCompany_time_id() {
        return company_time_id;
    }

    public void setCompany_time_id(String company_time_id) {
        this.company_time_id = company_time_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getIs_holiday() {
        return is_holiday;
    }

    public void setIs_holiday(String is_holiday) {
        this.is_holiday = is_holiday;
    }

    public String getFrom_time() {
        return from_time;
    }

    public void setFrom_time(String from_time) {
        this.from_time = from_time;
    }

    public String getTo_time() {
        return to_time;
    }

    public void setTo_time(String to_time) {
        this.to_time = to_time;
    }

    public String getCompany_time_addedby() {
        return company_time_addedby;
    }

    public void setCompany_time_addedby(String company_time_addedby) {
        this.company_time_addedby = company_time_addedby;
    }

    public String getCompany_time_created_at() {
        return company_time_created_at;
    }

    public void setCompany_time_created_at(String company_time_created_at) {
        this.company_time_created_at = company_time_created_at;
    }

    public String getCompany_time_updated_at() {
        return company_time_updated_at;
    }

    public void setCompany_time_updated_at(String company_time_updated_at) {
        this.company_time_updated_at = company_time_updated_at;
    }
}
