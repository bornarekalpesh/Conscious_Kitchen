package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerAddressResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("cust_address_list")
    @Expose
    private List<CustAddress> custAddressList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CustAddress> getCustAddressList() {
        return custAddressList;
    }

    public void setCustAddressList(List<CustAddress> custAddressList) {
        this.custAddressList = custAddressList;
    }
}
