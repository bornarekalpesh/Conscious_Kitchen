package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddGiftCardResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("gift_card_code")
    @Expose
    private String giftCardCode;
    @SerializedName("gift_card_amt")
    @Expose
    private String giftCardAmt;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getGiftCardCode() {
        return giftCardCode;
    }

    public void setGiftCardCode(String giftCardCode) {
        this.giftCardCode = giftCardCode;
    }

    public String getGiftCardAmt() {
        return giftCardAmt;
    }

    public void setGiftCardAmt(String giftCardAmt) {
        this.giftCardAmt = giftCardAmt;
    }
}
