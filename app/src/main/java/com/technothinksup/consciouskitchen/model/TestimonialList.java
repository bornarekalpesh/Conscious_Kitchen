package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestimonialList {

    @SerializedName("testimonial_id")
    @Expose
    private String testimonialId;
    @SerializedName("testimonial_cust_name")
    @Expose
    private String testimonialCustName;
    @SerializedName("testimonial_cust_address")
    @Expose
    private String testimonialCustAddress;
    @SerializedName("testimonial_image")
    @Expose
    private String testimonialImage;
    @SerializedName("testimonial_details")
    @Expose
    private String testimonialDetails;
    @SerializedName("testimonial_status")
    @Expose
    private String testimonialStatus;

    public String getTestimonialId() {
        return testimonialId;
    }

    public void setTestimonialId(String testimonialId) {
        this.testimonialId = testimonialId;
    }

    public String getTestimonialCustName() {
        return testimonialCustName;
    }

    public void setTestimonialCustName(String testimonialCustName) {
        this.testimonialCustName = testimonialCustName;
    }

    public String getTestimonialCustAddress() {
        return testimonialCustAddress;
    }

    public void setTestimonialCustAddress(String testimonialCustAddress) {
        this.testimonialCustAddress = testimonialCustAddress;
    }

    public String getTestimonialImage() {
        return testimonialImage;
    }

    public void setTestimonialImage(String testimonialImage) {
        this.testimonialImage = testimonialImage;
    }

    public String getTestimonialDetails() {
        return testimonialDetails;
    }

    public void setTestimonialDetails(String testimonialDetails) {
        this.testimonialDetails = testimonialDetails;
    }

    public String getTestimonialStatus() {
        return testimonialStatus;
    }

    public void setTestimonialStatus(String testimonialStatus) {
        this.testimonialStatus = testimonialStatus;
    }
}
