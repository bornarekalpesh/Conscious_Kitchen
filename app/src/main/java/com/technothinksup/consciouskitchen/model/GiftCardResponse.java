package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiftCardResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("iamge_url")
    @Expose
    private String iamgeUrl;
    @SerializedName("gift_card_list")
    @Expose
    private List<GiftCard> giftCardList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public List<GiftCard> getGiftCardList() {
        return giftCardList;
    }

    public void setGiftCardList(List<GiftCard> giftCardList) {
        this.giftCardList = giftCardList;
    }
}
