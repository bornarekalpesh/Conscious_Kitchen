package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("faq_category_list")
    @Expose
    private List<FaqCategory> faqCategoryList = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<FaqCategory> getFaqCategoryList() {
        return faqCategoryList;
    }

    public void setFaqCategoryList(List<FaqCategory> faqCategoryList) {
        this.faqCategoryList = faqCategoryList;
    }

}
