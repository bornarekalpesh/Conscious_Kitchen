package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("item_id")
    @Expose
    private String itemId;
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
