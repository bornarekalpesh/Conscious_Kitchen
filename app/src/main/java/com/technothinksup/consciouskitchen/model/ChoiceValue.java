package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChoiceValue {
    @SerializedName("choice_value_id")
    @Expose
    private String choiceValueId;
    @SerializedName("choice_value_name")
    @Expose
    private String choiceValueName;

    public String getChoiceValueId() {
        return choiceValueId;
    }

    public void setChoiceValueId(String choiceValueId) {
        this.choiceValueId = choiceValueId;
    }

    public String getChoiceValueName() {
        return choiceValueName;
    }

    public void setChoiceValueName(String choiceValueName) {
        this.choiceValueName = choiceValueName;
    }

}
