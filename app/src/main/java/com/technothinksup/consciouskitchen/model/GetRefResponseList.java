package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRefResponseList {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("tot_pending_count")
    @Expose
    private Integer totPendingCount;
    @SerializedName("tot_pending_amt")
    @Expose
    private Integer totPendingAmt;
    @SerializedName("pending_referral_list")
    @Expose
    private List<PendingReferral> pendingReferralList = null;
    @SerializedName("accepted_referral_list")
    @Expose
    private List<AcceptedReferral> acceptedReferralList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotPendingCount() {
        return totPendingCount;
    }

    public void setTotPendingCount(Integer totPendingCount) {
        this.totPendingCount = totPendingCount;
    }

    public Integer getTotPendingAmt() {
        return totPendingAmt;
    }

    public void setTotPendingAmt(Integer totPendingAmt) {
        this.totPendingAmt = totPendingAmt;
    }

    public List<PendingReferral> getPendingReferralList() {
        return pendingReferralList;
    }

    public void setPendingReferralList(List<PendingReferral> pendingReferralList) {
        this.pendingReferralList = pendingReferralList;
    }

    public List<AcceptedReferral> getAcceptedReferralList() {
        return acceptedReferralList;
    }

    public void setAcceptedReferralList(List<AcceptedReferral> acceptedReferralList) {
        this.acceptedReferralList = acceptedReferralList;
    }
}
