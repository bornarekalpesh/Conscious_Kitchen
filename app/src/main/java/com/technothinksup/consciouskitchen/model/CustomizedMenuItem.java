package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomizedMenuItem {
    @SerializedName("item_customize_id")
    @Expose
    private String itemCustomizeId;
    @SerializedName("customize_item_id")
    @Expose
    private String customizeItemId;
    @SerializedName("choice_set_id")
    @Expose
    private String choiceSetId;
    @SerializedName("customize_item_name")
    @Expose
    private String customizeItemName;
    @SerializedName("customize_item_descr")
    @Expose
    private String customizeItemDescr;
    @SerializedName("customize_item_sku")
    @Expose
    private String customizeItemSku;
    @SerializedName("customize_item_weight")
    @Expose
    private String customizeItemWeight;
    @SerializedName("customize_item_price")
    @Expose
    private String customizeItemPrice;
    @SerializedName("customize_item_image")
    @Expose
    private String customizeItemImage;
    @SerializedName("choice_set_name")
    @Expose
    private String choiceSetName;
    @SerializedName("choice_set_kitchen_name")
    @Expose
    private String choiceSetKitchenName;
    @SerializedName("choice_value_list")
    @Expose
    private List<ChoiceValue> choiceValueList = null;

    public String getItemCustomizeId() {
        return itemCustomizeId;
    }

    public void setItemCustomizeId(String itemCustomizeId) {
        this.itemCustomizeId = itemCustomizeId;
    }

    public String getCustomizeItemId() {
        return customizeItemId;
    }

    public void setCustomizeItemId(String customizeItemId) {
        this.customizeItemId = customizeItemId;
    }

    public String getChoiceSetId() {
        return choiceSetId;
    }

    public void setChoiceSetId(String choiceSetId) {
        this.choiceSetId = choiceSetId;
    }

    public String getCustomizeItemName() {
        return customizeItemName;
    }

    public void setCustomizeItemName(String customizeItemName) {
        this.customizeItemName = customizeItemName;
    }

    public String getCustomizeItemDescr() {
        return customizeItemDescr;
    }

    public void setCustomizeItemDescr(String customizeItemDescr) {
        this.customizeItemDescr = customizeItemDescr;
    }

    public String getCustomizeItemSku() {
        return customizeItemSku;
    }

    public void setCustomizeItemSku(String customizeItemSku) {
        this.customizeItemSku = customizeItemSku;
    }

    public String getCustomizeItemWeight() {
        return customizeItemWeight;
    }

    public void setCustomizeItemWeight(String customizeItemWeight) {
        this.customizeItemWeight = customizeItemWeight;
    }

    public String getCustomizeItemPrice() {
        return customizeItemPrice;
    }

    public void setCustomizeItemPrice(String customizeItemPrice) {
        this.customizeItemPrice = customizeItemPrice;
    }

    public String getCustomizeItemImage() {
        return customizeItemImage;
    }

    public void setCustomizeItemImage(String customizeItemImage) {
        this.customizeItemImage = customizeItemImage;
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

    public List<ChoiceValue> getChoiceValueList() {
        return choiceValueList;
    }

    public void setChoiceValueList(List<ChoiceValue> choiceValueList) {
        this.choiceValueList = choiceValueList;
    }
}
