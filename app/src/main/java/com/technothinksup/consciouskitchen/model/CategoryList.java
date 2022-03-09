package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryList {
    @SerializedName("food_category_id")
    @Expose
    private String foodCategoryId;
    @SerializedName("food_category_name")
    @Expose
    private String foodCategoryName;
    @SerializedName("food_category_descr")
    @Expose
    private String foodCategoryDescr;
    @SerializedName("food_category_image")
    @Expose
    private String foodCategoryImage;
    @SerializedName("upsell_category_id")
    @Expose
    private String upsellCategoryId;

    public String getFoodCategoryId() {
        return foodCategoryId;
    }

    public void setFoodCategoryId(String foodCategoryId) {
        this.foodCategoryId = foodCategoryId;
    }

    public String getFoodCategoryName() {
        return foodCategoryName;
    }

    public void setFoodCategoryName(String foodCategoryName) {
        this.foodCategoryName = foodCategoryName;
    }

    public String getFoodCategoryDescr() {
        return foodCategoryDescr;
    }

    public void setFoodCategoryDescr(String foodCategoryDescr) {
        this.foodCategoryDescr = foodCategoryDescr;
    }

    public String getFoodCategoryImage() {
        return foodCategoryImage;
    }

    public void setFoodCategoryImage(String foodCategoryImage) {
        this.foodCategoryImage = foodCategoryImage;
    }

    public String getUpsellCategoryId() {
        return upsellCategoryId;
    }

    public void setUpsellCategoryId(String upsellCategoryId) {
        this.upsellCategoryId = upsellCategoryId;
    }

}
