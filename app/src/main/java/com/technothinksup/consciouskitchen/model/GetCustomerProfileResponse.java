package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCustomerProfileResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("profile_details")
    @Expose
    private List<ProfileDetail> profileDetails = null;

    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<ProfileDetail> getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(List<ProfileDetail> profileDetails) {
        this.profileDetails = profileDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
