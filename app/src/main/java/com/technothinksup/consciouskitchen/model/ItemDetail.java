package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDetail {
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("food_category_id")
    @Expose
    private String foodCategoryId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_descr")
    @Expose
    private String itemDescr;
    @SerializedName("tax_rate_id")
    @Expose
    private String taxRateId;
    @SerializedName("item_is_featured")
    @Expose
    private String itemIsFeatured;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("item_status")
    @Expose
    private String itemStatus;
    @SerializedName("item_addedby")
    @Expose
    private String itemAddedby;
    @SerializedName("item_created_at")
    @Expose
    private String itemCreatedAt;
    @SerializedName("item_variation")
    @Expose
    private List<ItemVariation> itemVariation = null;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(String foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public String getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(String taxRateId) {
        this.taxRateId = taxRateId;
    }

    public String getItemIsFeatured() {
        return itemIsFeatured;
    }

    public void setItemIsFeatured(String itemIsFeatured) {
        this.itemIsFeatured = itemIsFeatured;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemAddedby() {
        return itemAddedby;
    }

    public void setItemAddedby(String itemAddedby) {
        this.itemAddedby = itemAddedby;
    }

    public String getItemCreatedAt() {
        return itemCreatedAt;
    }

    public void setItemCreatedAt(String itemCreatedAt) {
        this.itemCreatedAt = itemCreatedAt;
    }

    public List<ItemVariation> getItemVariation() {
        return itemVariation;
    }

    public void setItemVariation(List<ItemVariation> itemVariation) {
        this.itemVariation = itemVariation;
    }

}
