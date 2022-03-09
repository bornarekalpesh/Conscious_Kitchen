package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("item_image_path")
    @Expose
    private String itemImagePath;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;
    @SerializedName("total_basic_amount")
    @Expose
    private String totalBasicAmount;
    @SerializedName("total_gst_amount")
    @Expose
    private String totalGstAmount;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("cart_item_list")
    @Expose
    private List<CartItem> cartItemList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getItemImagePath() {
        return itemImagePath;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

    public String getTotalBasicAmount() {
        return totalBasicAmount;
    }

    public void setTotalBasicAmount(String totalBasicAmount) {
        this.totalBasicAmount = totalBasicAmount;
    }

    public String getTotalGstAmount() {
        return totalGstAmount;
    }

    public void setTotalGstAmount(String totalGstAmount) {
        this.totalGstAmount = totalGstAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }


}
