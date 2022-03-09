package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardItemDetail {
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
    @SerializedName("is_primary")
    @Expose
    private String isPrimary;
    @SerializedName("main_food_category_id")
    @Expose
    private String mainFoodCategoryId;
    @SerializedName("upsell_category_id")
    @Expose
    private String upsellCategoryId;
    @SerializedName("food_category_name")
    @Expose
    private String foodCategoryName;
    @SerializedName("food_category_descr")
    @Expose
    private String foodCategoryDescr;
    @SerializedName("food_category_image")
    @Expose
    private String foodCategoryImage;
    @SerializedName("food_category_status")
    @Expose
    private String foodCategoryStatus;
    @SerializedName("food_category_addedby")
    @Expose
    private String foodCategoryAddedby;
    @SerializedName("food_category_created_at")
    @Expose
    private String foodCategoryCreatedAt;

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

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getMainFoodCategoryId() {
        return mainFoodCategoryId;
    }

    public void setMainFoodCategoryId(String mainFoodCategoryId) {
        this.mainFoodCategoryId = mainFoodCategoryId;
    }

    public String getUpsellCategoryId() {
        return upsellCategoryId;
    }

    public void setUpsellCategoryId(String upsellCategoryId) {
        this.upsellCategoryId = upsellCategoryId;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }

    public String getFoodCategoryDescr() {
        return foodCategoryDescr;
    }

    public void setFoodCategoryDescr(String foodCategoryDescr) {
        this.foodCategoryDescr = foodCategoryDescr;
    }

    public String getFoodCategoryImage() {
        return foodCategoryImage;
    }

    public void setFoodCategoryImage(String foodCategoryImage) {
        this.foodCategoryImage = foodCategoryImage;
    }

    public String getFoodCategoryStatus() {
        return foodCategoryStatus;
    }

    public void setFoodCategoryStatus(String foodCategoryStatus) {
        this.foodCategoryStatus = foodCategoryStatus;
    }

    public String getFoodCategoryAddedby() {
        return foodCategoryAddedby;
    }

    public void setFoodCategoryAddedby(String foodCategoryAddedby) {
        this.foodCategoryAddedby = foodCategoryAddedby;
    }

    public String getFoodCategoryCreatedAt() {
        return foodCategoryCreatedAt;
    }

    public void setFoodCategoryCreatedAt(String foodCategoryCreatedAt) {
        this.foodCategoryCreatedAt = foodCategoryCreatedAt;
    }
}
