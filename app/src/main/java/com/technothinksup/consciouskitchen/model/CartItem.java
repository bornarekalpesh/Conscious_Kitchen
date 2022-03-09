package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItem {
    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_variation_id")
    @Expose
    private String itemVariationId;
    @SerializedName("item_variation_name")
    @Expose
    private String itemVariationName;
    @SerializedName("item_variation_price")
    @Expose
    private String itemVariationPrice;
    @SerializedName("choice_set_id")
    @Expose
    private String choiceSetId;
    @SerializedName("choice_set_name")
    @Expose
    private String choiceSetName;
    @SerializedName("choice_set_kitchen_name")
    @Expose
    private String choiceSetKitchenName;
    @SerializedName("choice_value_id")
    @Expose
    private String choiceValueId;
    @SerializedName("choice_value_name")
    @Expose
    private String choiceValueName;
    @SerializedName("customize_item_id")
    @Expose
    private String customizeItemId;
    @SerializedName("customize_item_name")
    @Expose
    private String customizeItemName;
    @SerializedName("customize_item_amount")
    @Expose
    private String customizeItemAmount;
    @SerializedName("item_total_price")
    @Expose
    private String itemTotalPrice;
    @SerializedName("item_qty")
    @Expose
    private String itemQty;
    @SerializedName("item_basic_amount")
    @Expose
    private String itemBasicAmount;
    @SerializedName("tax_rate_id")
    @Expose
    private String taxRateId;
    @SerializedName("tax_rate_per")
    @Expose
    private String taxRatePer;
    @SerializedName("item_gst_amount")
    @Expose
    private String itemGstAmount;
    @SerializedName("item_total_amount")
    @Expose
    private String itemTotalAmount;
    @SerializedName("cart_item_added_at")
    @Expose
    private String cartItemAddedAt;
    @SerializedName("item_descr")
    @Expose
    private String itemDescr;
    @SerializedName("item_image")
    @Expose
    private String itemImage;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemVariationId() {
        return itemVariationId;
    }

    public void setItemVariationId(String itemVariationId) {
        this.itemVariationId = itemVariationId;
    }

    public String getItemVariationName() {
        return itemVariationName;
    }

    public void setItemVariationName(String itemVariationName) {
        this.itemVariationName = itemVariationName;
    }

    public String getItemVariationPrice() {
        return itemVariationPrice;
    }

    public void setItemVariationPrice(String itemVariationPrice) {
        this.itemVariationPrice = itemVariationPrice;
    }

    public String getChoiceSetId() {
        return choiceSetId;
    }

    public void setChoiceSetId(String choiceSetId) {
        this.choiceSetId = choiceSetId;
    }

    public String getChoiceSetName() {
        return choiceSetName;
    }

    public void setChoiceSetName(String choiceSetName) {
        this.choiceSetName = choiceSetName;
    }

    public String getChoiceSetKitchenName() {
        return choiceSetKitchenName;
    }

    public void setChoiceSetKitchenName(String choiceSetKitchenName) {
        this.choiceSetKitchenName = choiceSetKitchenName;
    }

    public String getChoiceValueId() {
        return choiceValueId;
    }

    public void setChoiceValueId(String choiceValueId) {
        this.choiceValueId = choiceValueId;
    }

    public String getChoiceValueName() {
        return choiceValueName;
    }

    public void setChoiceValueName(String choiceValueName) {
        this.choiceValueName = choiceValueName;
    }

    public String getCustomizeItemId() {
        return customizeItemId;
    }

    public void setCustomizeItemId(String customizeItemId) {
        this.customizeItemId = customizeItemId;
    }

    public String getCustomizeItemName() {
        return customizeItemName;
    }

    public void setCustomizeItemName(String customizeItemName) {
        this.customizeItemName = customizeItemName;
    }

    public String getCustomizeItemAmount() {
        return customizeItemAmount;
    }

    public void setCustomizeItemAmount(String customizeItemAmount) {
        this.customizeItemAmount = customizeItemAmount;
    }

    public String getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(String itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemBasicAmount() {
        return itemBasicAmount;
    }

    public void setItemBasicAmount(String itemBasicAmount) {
        this.itemBasicAmount = itemBasicAmount;
    }

    public String getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(String taxRateId) {
        this.taxRateId = taxRateId;
    }

    public String getTaxRatePer() {
        return taxRatePer;
    }

    public void setTaxRatePer(String taxRatePer) {
        this.taxRatePer = taxRatePer;
    }

    public String getItemGstAmount() {
        return itemGstAmount;
    }

    public void setItemGstAmount(String itemGstAmount) {
        this.itemGstAmount = itemGstAmount;
    }

    public String getItemTotalAmount() {
        return itemTotalAmount;
    }

    public void setItemTotalAmount(String itemTotalAmount) {
        this.itemTotalAmount = itemTotalAmount;
    }

    public String getCartItemAddedAt() {
        return cartItemAddedAt;
    }

    public void setCartItemAddedAt(String cartItemAddedAt) {
        this.cartItemAddedAt = cartItemAddedAt;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

}
