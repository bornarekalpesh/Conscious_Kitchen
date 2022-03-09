package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemDetailResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("item_image_path")
    @Expose
    private String itemImagePath;

    @SerializedName("is_item_customize")
    @Expose
    private String is_item_customize;

    @SerializedName("item_details")
    @Expose
    private List<ItemDetail> itemDetails = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getItemImagePath() {
        return itemImagePath;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public String getIs_item_customize() {
        return is_item_customize;
    }

    public void setIs_item_customize(String is_item_customize) {
        this.is_item_customize = is_item_customize;
    }
}
