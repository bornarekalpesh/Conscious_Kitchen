package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BranchResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("branch_list")
    @Expose
    private List<BranchList> branchList = null;

    @SerializedName("branch_timing")
    @Expose
    private List<BranchTimeModel> branch_timing = null;

    public List<BranchTimeModel> getBranch_timing() {
        return branch_timing;
    }

    public void setBranch_timing(List<BranchTimeModel> branch_timing) {
        this.branch_timing = branch_timing;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BranchList> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<BranchList> branchList) {
        this.branchList = branchList;
    }
}
