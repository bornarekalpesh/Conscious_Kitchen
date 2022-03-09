package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRewardList {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("item_image_path")
    @Expose
    private String itemImagePath;
    @SerializedName("category_image_path")
    @Expose
    private String categoryImagePath;
    @SerializedName("reward_list")
    @Expose
    private List<Reward> rewardList = null;

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

    public String getCategoryImagePath() {
        return categoryImagePath;
    }

    public void setCategoryImagePath(String categoryImagePath) {
        this.categoryImagePath = categoryImagePath;
    }

    public List<Reward> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }

}
