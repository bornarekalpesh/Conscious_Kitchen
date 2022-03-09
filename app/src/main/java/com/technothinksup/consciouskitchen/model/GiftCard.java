package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiftCard {
    @SerializedName("cust_gift_card_id")
    @Expose
    private String custGiftCardId;
    @SerializedName("gift_card_code")
    @Expose
    private String giftCardCode;
    @SerializedName("gift_card_name")
    @Expose
    private String giftCardName;
    @SerializedName("gift_card_amt")
    @Expose
    private String giftCardAmt;
    @SerializedName("gift_card_exp_date")
    @Expose
    private String giftCardExpDate;
    @SerializedName("limit_per_user")
    @Expose
    private String limitPerUser;
    @SerializedName("num_of_card")
    @Expose
    private String numOfCard;
    @SerializedName("gift_card_image")
    @Expose
    private String giftCardImage;

    public String getCustGiftCardId() {
        return custGiftCardId;
    }

    public void setCustGiftCardId(String custGiftCardId) {
        this.custGiftCardId = custGiftCardId;
    }

    public String getGiftCardCode() {
        return giftCardCode;
    }

    public void setGiftCardCode(String giftCardCode) {
        this.giftCardCode = giftCardCode;
    }

    public String getGiftCardName() {
        return giftCardName;
    }

    public void setGiftCardName(String giftCardName) {
        this.giftCardName = giftCardName;
    }

    public String getGiftCardAmt() {
        return giftCardAmt;
    }

    public void setGiftCardAmt(String giftCardAmt) {
        this.giftCardAmt = giftCardAmt;
    }

    public String getGiftCardExpDate() {
        return giftCardExpDate;
    }

    public void setGiftCardExpDate(String giftCardExpDate) {
        this.giftCardExpDate = giftCardExpDate;
    }

    public String getLimitPerUser() {
        return limitPerUser;
    }

    public void setLimitPerUser(String limitPerUser) {
        this.limitPerUser = limitPerUser;
    }

    public String getNumOfCard() {
        return numOfCard;
    }

    public void setNumOfCard(String numOfCard) {
        this.numOfCard = numOfCard;
    }

    public String getGiftCardImage() {
        return giftCardImage;
    }

    public void setGiftCardImage(String giftCardImage) {
        this.giftCardImage = giftCardImage;
    }
}
