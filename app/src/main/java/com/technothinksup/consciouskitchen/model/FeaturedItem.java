package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedItem {
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("food_category_id")
    @Expose
    private String foodCategoryId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_descr")
    @Expose
    private String itemDescr;
    @SerializedName("item_is_featured")
    @Expose
    private String itemIsFeatured;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("tax_rate_id")
    @Expose
    private String taxRateId;
    @SerializedName("food_category_name")
    @Expose
    private String foodCategoryName;
    @SerializedName("tax_rate_name")
    @Expose
    private String taxRateName;
    @SerializedName("tax_rate_per")
    @Expose
    private String taxRatePer;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getTaxRateId() {
        return taxRateId;
    }

    public void setTaxRateId(String taxRateId) {
        this.taxRateId = taxRateId;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }

    public String getTaxRateName() {
        return taxRateName;
    }

    public void setTaxRateName(String taxRateName) {
        this.taxRateName = taxRateName;
    }

    public String getTaxRatePer() {
        return taxRatePer;
    }

    public void setTaxRatePer(String taxRatePer) {
        this.taxRatePer = taxRatePer;
    }
}
