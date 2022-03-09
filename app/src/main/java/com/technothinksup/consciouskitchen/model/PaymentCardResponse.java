package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentCardResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payment_card_details")
    @Expose
    private List<PaymentCardDetail> paymentCardDetails = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PaymentCardDetail> getPaymentCardDetails() {
        return paymentCardDetails;
    }

    public void setPaymentCardDetails(List<PaymentCardDetail> paymentCardDetails) {
        this.paymentCardDetails = paymentCardDetails;
    }



}
