package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PossibleReward {
    @SerializedName("reward_id")
    @Expose
    private String rewardId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("reward_name")
    @Expose
    private String rewardName;
    @SerializedName("reward_milestone_point")
    @Expose
    private String rewardMilestonePoint;
    @SerializedName("reward_redeem_type")
    @Expose
    private String rewardRedeemType;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("food_category_id")
    @Expose
    private String foodCategoryId;
    @SerializedName("reward_status")
    @Expose
    private String rewardStatus;
    @SerializedName("reward_addedby")
    @Expose
    private String rewardAddedby;
    @SerializedName("reward_created_at")
    @Expose
    private String rewardCreatedAt;
    @SerializedName("reward_updated_at")
    @Expose
    private String rewardUpdatedAt;
    @SerializedName("item_details")
    @Expose
    private List<ItemDetail> itemDetails = null;

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardMilestonePoint() {
        return rewardMilestonePoint;
    }

    public void setRewardMilestonePoint(String rewardMilestonePoint) {
        this.rewardMilestonePoint = rewardMilestonePoint;
    }

    public String getRewardRedeemType() {
        return rewardRedeemType;
    }

    public void setRewardRedeemType(String rewardRedeemType) {
        this.rewardRedeemType = rewardRedeemType;
    }

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

    public String getRewardStatus() {
        return rewardStatus;
    }

    public void setRewardStatus(String rewardStatus) {
        this.rewardStatus = rewardStatus;
    }

    public String getRewardAddedby() {
        return rewardAddedby;
    }

    public void setRewardAddedby(String rewardAddedby) {
        this.rewardAddedby = rewardAddedby;
    }

    public String getRewardCreatedAt() {
        return rewardCreatedAt;
    }

    public void setRewardCreatedAt(String rewardCreatedAt) {
        this.rewardCreatedAt = rewardCreatedAt;
    }

    public String getRewardUpdatedAt() {
        return rewardUpdatedAt;
    }

    public void setRewardUpdatedAt(String rewardUpdatedAt) {
        this.rewardUpdatedAt = rewardUpdatedAt;
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }
}
