package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventList {

    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("event_type")
    @Expose
    private String eventType;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_descr")
    @Expose
    private String eventDescr;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("event_time")
    @Expose
    private String eventTime;
    @SerializedName("event_last_date_time")
    @Expose
    private String eventLastDateTime;
    @SerializedName("event_attendees_type")
    @Expose
    private String eventAttendeesType;
    @SerializedName("event_attendees_num")
    @Expose
    private String eventAttendeesNum;
    @SerializedName("event_per_fee")
    @Expose
    private String eventPerFee;
    @SerializedName("event_reward_point")
    @Expose
    private String eventRewardPoint;
    @SerializedName("event_app_btn_text")
    @Expose
    private String eventAppBtnText;
    @SerializedName("event_image")
    @Expose
    private String eventImage;
    @SerializedName("event_is_popular")
    @Expose
    private String eventIsPopular;
    @SerializedName("event_type_name")
    @Expose
    private String eventTypeName;
    @SerializedName("event_attendees_type_name")
    @Expose
    private String eventAttendeesTypeName;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescr() {
        return eventDescr;
    }

    public void setEventDescr(String eventDescr) {
        this.eventDescr = eventDescr;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventLastDateTime() {
        return eventLastDateTime;
    }

    public void setEventLastDateTime(String eventLastDateTime) {
        this.eventLastDateTime = eventLastDateTime;
    }

    public String getEventAttendeesType() {
        return eventAttendeesType;
    }

    public void setEventAttendeesType(String eventAttendeesType) {
        this.eventAttendeesType = eventAttendeesType;
    }

    public String getEventAttendeesNum() {
        return eventAttendeesNum;
    }

    public void setEventAttendeesNum(String eventAttendeesNum) {
        this.eventAttendeesNum = eventAttendeesNum;
    }

    public String getEventPerFee() {
        return eventPerFee;
    }

    public void setEventPerFee(String eventPerFee) {
        this.eventPerFee = eventPerFee;
    }

    public String getEventRewardPoint() {
        return eventRewardPoint;
    }

    public void setEventRewardPoint(String eventRewardPoint) {
        this.eventRewardPoint = eventRewardPoint;
    }

    public String getEventAppBtnText() {
        return eventAppBtnText;
    }

    public void setEventAppBtnText(String eventAppBtnText) {
        this.eventAppBtnText = eventAppBtnText;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventIsPopular() {
        return eventIsPopular;
    }

    public void setEventIsPopular(String eventIsPopular) {
        this.eventIsPopular = eventIsPopular;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public String getEventAttendeesTypeName() {
        return eventAttendeesTypeName;
    }

    public void setEventAttendeesTypeName(String eventAttendeesTypeName) {
        this.eventAttendeesTypeName = eventAttendeesTypeName;
    }

}
