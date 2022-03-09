package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardList {

    @SerializedName("reward_id")
    @Expose
    private String rewardId;
    @SerializedName("reward_name")
    @Expose
    private String rewardName;
    @SerializedName("reward_milestone_point")
    @Expose
    private String rewardMilestonePoint;
    @SerializedName("reward_redeem_type")
    @Expose
    private String rewardRedeemType;

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
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
}
