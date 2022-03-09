package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultBranchResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("branch_details")
    @Expose
    private List<BranchDetail> branchDetails = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BranchDetail> getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(List<BranchDetail> branchDetails) {
        this.branchDetails = branchDetails;
    }
}
