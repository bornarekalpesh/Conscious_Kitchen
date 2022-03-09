package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearestBranchResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("branch_details")
    @Expose
    private List<BranchDetail> branchList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BranchDetail> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<BranchDetail> branchList) {
        this.branchList = branchList;
    }
}
