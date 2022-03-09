package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TasteResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("taste_list")
    @Expose
    private List<Taste> tasteList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Taste> getTasteList() {
        return tasteList;
    }

    public void setTasteList(List<Taste> tasteList) {
        this.tasteList = tasteList;
    }
}
