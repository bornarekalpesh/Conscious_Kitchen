package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("event_image_path")
    @Expose
    private String eventImagePath;
    @SerializedName("event_list")
    @Expose
    private List<EventList> eventList = null;

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

    public List<EventList> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventList> eventList) {
        this.eventList = eventList;
    }
}
