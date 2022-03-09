package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PageContentResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("page_content")
    @Expose
    private List<PageContent> pageContent = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PageContent> getPageContent() {
        return pageContent;
    }

    public void setPageContent(List<PageContent> pageContent) {
        this.pageContent = pageContent;
    }
}
