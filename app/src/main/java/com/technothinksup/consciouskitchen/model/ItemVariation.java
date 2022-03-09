package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemVariation {

    @SerializedName("item_variation_id")
    @Expose
    private String itemVariationId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_variation_name")
    @Expose
    private String itemVariationName;
    @SerializedName("item_variation_sku")
    @Expose
    private String itemVariationSku;
    @SerializedName("item_variation_weight")
    @Expose
    private String itemVariationWeight;
    @SerializedName("item_variation_price")
    @Expose
    private String itemVariationPrice;
    @SerializedName("item_variation_status")
    @Expose
    private String itemVariationStatus;
    @SerializedName("item_variation_addedby")
    @Expose
    private String itemVariationAddedby;
    @SerializedName("item_variation_created_at")
    @Expose
    private String itemVariationCreatedAt;
    @SerializedName("item_variation_updated_at")
    @Expose
    private String itemVariationUpdatedAt;

    public String getItemVariationId() {
        return itemVariationId;
    }

    public void setItemVariationId(String itemVariationId) {
        this.itemVariationId = itemVariationId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemVariationName() {
        return itemVariationName;
    }

    public void setItemVariationName(String itemVariationName) {
        this.itemVariationName = itemVariationName;
    }

    public String getItemVariationSku() {
        return itemVariationSku;
    }

    public void setItemVariationSku(String itemVariationSku) {
        this.itemVariationSku = itemVariationSku;
    }

    public String getItemVariationWeight() {
        return itemVariationWeight;
    }

    public void setItemVariationWeight(String itemVariationWeight) {
        this.itemVariationWeight = itemVariationWeight;
    }

    public String getItemVariationPrice() {
        return itemVariationPrice;
    }

    public void setItemVariationPrice(String itemVariationPrice) {
        this.itemVariationPrice = itemVariationPrice;
    }

    public String getItemVariationStatus() {
        return itemVariationStatus;
    }

    public void setItemVariationStatus(String itemVariationStatus) {
        this.itemVariationStatus = itemVariationStatus;
    }

    public String getItemVariationAddedby() {
        return itemVariationAddedby;
    }

    public void setItemVariationAddedby(String itemVariationAddedby) {
        this.itemVariationAddedby = itemVariationAddedby;
    }

    public String getItemVariationCreatedAt() {
        return itemVariationCreatedAt;
    }

    public void setItemVariationCreatedAt(String itemVariationCreatedAt) {
        this.itemVariationCreatedAt = itemVariationCreatedAt;
    }

    public String getItemVariationUpdatedAt() {
        return itemVariationUpdatedAt;
    }

    public void setItemVariationUpdatedAt(String itemVariationUpdatedAt) {
        this.itemVariationUpdatedAt = itemVariationUpdatedAt;
    }
}
