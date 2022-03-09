package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FaqCategory {
    @SerializedName("faq_category_id")
    @Expose
    private String faqCategoryId;
    @SerializedName("faq_category_name")
    @Expose
    private String faqCategoryName;
    @SerializedName("faq_list")
    @Expose
    private List<Faq> faqList = null;

    public String getFaqCategoryId() {
        return faqCategoryId;
    }

    public void setFaqCategoryId(String faqCategoryId) {
        this.faqCategoryId = faqCategoryId;
    }

    public String getFaqCategoryName() {
        return faqCategoryName;
    }

    public void setFaqCategoryName(String faqCategoryName) {
        this.faqCategoryName = faqCategoryName;
    }

    public List<Faq> getFaqList() {
        return faqList;
    }

    public void setFaqList(List<Faq> faqList) {
        this.faqList = faqList;
    }
}
