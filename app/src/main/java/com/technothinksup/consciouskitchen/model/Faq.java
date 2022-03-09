package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Faq {
    @SerializedName("faq_category_id")
    @Expose
    private String faqCategoryId;
    @SerializedName("faq_question")
    @Expose
    private String faqQuestion;
    @SerializedName("faq_answer")
    @Expose
    private String faqAnswer;

    public String getFaqCategoryId() {
        return faqCategoryId;
    }

    public void setFaqCategoryId(String faqCategoryId) {
        this.faqCategoryId = faqCategoryId;
    }

    public String getFaqQuestion() {
        return faqQuestion;
    }

    public void setFaqQuestion(String faqQuestion) {
        this.faqQuestion = faqQuestion;
    }

    public String getFaqAnswer() {
        return faqAnswer;
    }

    public void setFaqAnswer(String faqAnswer) {
        this.faqAnswer = faqAnswer;
    }
}
