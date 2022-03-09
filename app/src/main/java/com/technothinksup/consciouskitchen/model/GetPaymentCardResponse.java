package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPaymentCardResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("payment_card_list")
    @Expose
    private List<PaymentCard> paymentCardList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PaymentCard> getPaymentCardList() {
        return paymentCardList;
    }

    public void setPaymentCardList(List<PaymentCard> paymentCardList) {
        this.paymentCardList = paymentCardList;
    }



}
