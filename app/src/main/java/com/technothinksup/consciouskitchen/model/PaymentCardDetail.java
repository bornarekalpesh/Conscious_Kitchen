package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentCardDetail {

    @SerializedName("payment_card_id")
    @Expose
    private String paymentCardId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("card_expire_date")
    @Expose
    private String cardExpireDate;
    @SerializedName("card_security_code")
    @Expose
    private String cardSecurityCode;
    @SerializedName("name_on_card")
    @Expose
    private String nameOnCard;
    @SerializedName("is_default")
    @Expose
    private String isDefault;
    @SerializedName("payment_card_added_date")
    @Expose
    private String paymentCardAddedDate;

    public String getPaymentCardId() {
        return paymentCardId;
    }

    public void setPaymentCardId(String paymentCardId) {
        this.paymentCardId = paymentCardId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpireDate() {
        return cardExpireDate;
    }

    public void setCardExpireDate(String cardExpireDate) {
        this.cardExpireDate = cardExpireDate;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getPaymentCardAddedDate() {
        return paymentCardAddedDate;
    }

    public void setPaymentCardAddedDate(String paymentCardAddedDate) {
        this.paymentCardAddedDate = paymentCardAddedDate;
    }
}
