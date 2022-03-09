package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentCard {
    @SerializedName("payment_card_id")
    @Expose
    private String paymentCardId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("card_expire_month")
    @Expose
    private String cardExpireMonth;
    @SerializedName("card_expire_year")
    @Expose
    private String cardExpireYear;
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

    @SerializedName("card_image")
    @Expose
    private String card_image;

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

    public String getCardExpireMonth() {
        return cardExpireMonth;
    }

    public void setCardExpireMonth(String cardExpireMonth) {
        this.cardExpireMonth = cardExpireMonth;
    }

    public String getCardExpireYear() {
        return cardExpireYear;
    }

    public void setCardExpireYear(String cardExpireYear) {
        this.cardExpireYear = cardExpireYear;
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

    public String getCard_image() {
        return card_image;
    }

    public void setCard_image(String card_image) {
        this.card_image = card_image;
    }
}
