package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDetailResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("event_image_path")
    @Expose
    private String eventImagePath;
    @SerializedName("event_details")
    @Expose
    private List<EventDetail> eventDetails = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEventImagePath() {
        return eventImagePath;
    }

    public void setEventImagePath(String eventImagePath) {
        this.eventImagePath = eventImagePath;
    }

    public List<EventDetail> getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(List<EventDetail> eventDetails) {
        this.eventDetails = eventDetails;
    }
}
