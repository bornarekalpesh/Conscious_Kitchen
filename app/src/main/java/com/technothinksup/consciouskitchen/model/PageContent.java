package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageContent {

    @SerializedName("page_content_id")
    @Expose
    private String pageContentId;
    @SerializedName("page_content_page")
    @Expose
    private String pageContentPage;
    @SerializedName("page_content_details")
    @Expose
    private String pageContentDetails;

    public String getPageContentId() {
        return pageContentId;
    }

    public void setPageContentId(String pageContentId) {
        this.pageContentId = pageContentId;
    }

    public String getPageContentPage() {
        return pageContentPage;
    }

    public void setPageContentPage(String pageContentPage) {
        this.pageContentPage = pageContentPage;
    }

    public String getPageContentDetails() {
        return pageContentDetails;
    }

    public void setPageContentDetails(String pageContentDetails) {
        this.pageContentDetails = pageContentDetails;
    }
}
