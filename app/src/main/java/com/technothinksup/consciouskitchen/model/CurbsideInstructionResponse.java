package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurbsideInstructionResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("curbside_instruction_list")
    @Expose
    private List<CurbsideInstruction> curbsideInstructionList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CurbsideInstruction> getCurbsideInstructionList() {
        return curbsideInstructionList;
    }

    public void setCurbsideInstructionList(List<CurbsideInstruction> curbsideInstructionList) {
        this.curbsideInstructionList = curbsideInstructionList;
    }

}
