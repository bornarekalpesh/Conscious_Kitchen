package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestimonialResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("testimonial_image_path")
    @Expose
    private String testimonialImagePath;
    @SerializedName("testimonial_list")
    @Expose
    private List<TestimonialList> testimonialList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTestimonialImagePath() {
        return testimonialImagePath;
    }

    public void setTestimonialImagePath(String testimonialImagePath) {
        this.testimonialImagePath = testimonialImagePath;
    }

    public List<TestimonialList> getTestimonialList() {
        return testimonialList;
    }

    public void setTestimonialList(List<TestimonialList> testimonialList) {
        this.testimonialList = testimonialList;
    }
}
