package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomizedMenuResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("customized_menu_item_list")
    @Expose
    private List<CustomizedMenuItem> customizedMenuItemList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<CustomizedMenuItem> getCustomizedMenuItemList() {
        return customizedMenuItemList;
    }

    public void setCustomizedMenuItemList(List<CustomizedMenuItem> customizedMenuItemList) {
        this.customizedMenuItemList = customizedMenuItemList;
    }

}
