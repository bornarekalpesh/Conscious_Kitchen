package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteItem {
    @SerializedName("favorite_item_id")
    @Expose
    private String favoriteItemId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("item_details")
    @Expose
    private List<ItemDetail> itemDetails = null;

    public String getFavoriteItemId() {
        return favoriteItemId;
    }

    public void setFavoriteItemId(String favoriteItemId) {
        this.favoriteItemId = favoriteItemId;
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

}
