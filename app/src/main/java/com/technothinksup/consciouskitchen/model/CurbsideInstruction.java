package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurbsideInstruction {
    @SerializedName("curbside_instruction_id")
    @Expose
    private String curbsideInstructionId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("car_make")
    @Expose
    private String carMake;
    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("car_color")
    @Expose
    private String carColor;
    @SerializedName("curbside_instruction")
    @Expose
    private String curbsideInstruction;
    @SerializedName("is_default")
    @Expose
    private String isDefault;
    @SerializedName("curbside_instruction_added_date")
    @Expose
    private String curbsideInstructionAddedDate;

    public String getCurbsideInstructionId() {
        return curbsideInstructionId;
    }

    public void setCurbsideInstructionId(String curbsideInstructionId) {
        this.curbsideInstructionId = curbsideInstructionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCurbsideInstruction() {
        return curbsideInstruction;
    }

    public void setCurbsideInstruction(String curbsideInstruction) {
        this.curbsideInstruction = curbsideInstruction;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getCurbsideInstructionAddedDate() {
        return curbsideInstructionAddedDate;
    }

    public void setCurbsideInstructionAddedDate(String curbsideInstructionAddedDate) {
        this.curbsideInstructionAddedDate = curbsideInstructionAddedDate;
    }
}
