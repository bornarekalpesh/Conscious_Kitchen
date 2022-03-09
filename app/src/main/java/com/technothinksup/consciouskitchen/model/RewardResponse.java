package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RewardResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("reward_list")
    @Expose
    private List<RewardList> rewardList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<RewardList> getRewardList() {
        return rewardList;
    }

    public void setRewardList(List<RewardList> rewardList) {
        this.rewardList = rewardList;
    }
}
