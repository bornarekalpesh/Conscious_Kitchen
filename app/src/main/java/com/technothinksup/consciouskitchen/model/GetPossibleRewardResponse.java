package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPossibleRewardResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("reward_count")
    @Expose
    private Integer rewardCount;
    @SerializedName("possible_reward_list")
    @Expose
    private List<PossibleReward> possibleRewardList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(Integer rewardCount) {
        this.rewardCount = rewardCount;
    }

    public List<PossibleReward> getPossibleRewardList() {
        return possibleRewardList;
    }

    public void setPossibleRewardList(List<PossibleReward> possibleRewardList) {
        this.possibleRewardList = possibleRewardList;
    }
}
