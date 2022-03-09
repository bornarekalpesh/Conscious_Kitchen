package com.technothinksup.consciouskitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("branch_id")
    @Expose
    private String branchId;
    @SerializedName("order_no")
    @Expose
    private String orderNo;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("car_make")
    @Expose
    private String carMake;
    @SerializedName("car_model")
    @Expose
    private String carModel;
    @SerializedName("car_color")
    @Expose
    private String carColor;
    @SerializedName("pickup_delivery_time")
    @Expose
    private String pickupDeliveryTime;
    @SerializedName("order_basic_amount")
    @Expose
    private String orderBasicAmount;
    @SerializedName("order_tax_amount")
    @Expose
    private String orderTaxAmount;
    @SerializedName("order_shipping_amount")
    @Expose
    private String orderShippingAmount;
    @SerializedName("order_tip_amount")
    @Expose
    private String orderTipAmount;
    @SerializedName("order_total_amount")
    @Expose
    private String orderTotalAmount;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("payment_card_no")
    @Expose
    private String paymentCardNo;
    @SerializedName("used_coupon_amount")
    @Expose
    private String usedCouponAmount;
    @SerializedName("used_coupon_code")
    @Expose
    private String usedCouponCode;
    @SerializedName("used_coupon_id")
    @Expose
    private String usedCouponId;
    @SerializedName("used_wallet_amount")
    @Expose
    private String usedWalletAmount;
    @SerializedName("used_gift_card_amount")
    @Expose
    private String usedGiftCardAmount;
    @SerializedName("used_gift_card_code")
    @Expose
    private String usedGiftCardCode;
    @SerializedName("used_gift_card_id")
    @Expose
    private String usedGiftCardId;
    @SerializedName("redeem_point_amount")
    @Expose
    private String redeemPointAmount;
    @SerializedName("reward_use_point")
    @Expose
    private String rewardUsePoint;
    @SerializedName("online_payment_amount")
    @Expose
    private String onlinePaymentAmount;
    @SerializedName("order_instruction")
    @Expose
    private String orderInstruction;
    @SerializedName("order_is_paid")
    @Expose
    private String orderIsPaid;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_created_at")
    @Expose
    private String orderCreatedAt;
    @SerializedName("order_updated_at")
    @Expose
    private String orderUpdatedAt;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
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

    public String getPickupDeliveryTime() {
        return pickupDeliveryTime;
    }

    public void setPickupDeliveryTime(String pickupDeliveryTime) {
        this.pickupDeliveryTime = pickupDeliveryTime;
    }

    public String getOrderBasicAmount() {
        return orderBasicAmount;
    }

    public void setOrderBasicAmount(String orderBasicAmount) {
        this.orderBasicAmount = orderBasicAmount;
    }

    public String getOrderTaxAmount() {
        return orderTaxAmount;
    }

    public void setOrderTaxAmount(String orderTaxAmount) {
        this.orderTaxAmount = orderTaxAmount;
    }

    public String getOrderShippingAmount() {
        return orderShippingAmount;
    }

    public void setOrderShippingAmount(String orderShippingAmount) {
        this.orderShippingAmount = orderShippingAmount;
    }

    public String getOrderTipAmount() {
        return orderTipAmount;
    }

    public void setOrderTipAmount(String orderTipAmount) {
        this.orderTipAmount = orderTipAmount;
    }

    public String getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(String orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentCardNo() {
        return paymentCardNo;
    }

    public void setPaymentCardNo(String paymentCardNo) {
        this.paymentCardNo = paymentCardNo;
    }

    public String getUsedCouponAmount() {
        return usedCouponAmount;
    }

    public void setUsedCouponAmount(String usedCouponAmount) {
        this.usedCouponAmount = usedCouponAmount;
    }

    public String getUsedCouponCode() {
        return usedCouponCode;
    }

    public void setUsedCouponCode(String usedCouponCode) {
        this.usedCouponCode = usedCouponCode;
    }

    public String getUsedCouponId() {
        return usedCouponId;
    }

    public void setUsedCouponId(String usedCouponId) {
        this.usedCouponId = usedCouponId;
    }

    public String getUsedWalletAmount() {
        return usedWalletAmount;
    }

    public void setUsedWalletAmount(String usedWalletAmount) {
        this.usedWalletAmount = usedWalletAmount;
    }

    public String getUsedGiftCardAmount() {
        return usedGiftCardAmount;
    }

    public void setUsedGiftCardAmount(String usedGiftCardAmount) {
        this.usedGiftCardAmount = usedGiftCardAmount;
    }

    public String getUsedGiftCardCode() {
        return usedGiftCardCode;
    }

    public void setUsedGiftCardCode(String usedGiftCardCode) {
        this.usedGiftCardCode = usedGiftCardCode;
    }

    public String getUsedGiftCardId() {
        return usedGiftCardId;
    }

    public void setUsedGiftCardId(String usedGiftCardId) {
        this.usedGiftCardId = usedGiftCardId;
    }

    public String getRedeemPointAmount() {
        return redeemPointAmount;
    }

    public void setRedeemPointAmount(String redeemPointAmount) {
        this.redeemPointAmount = redeemPointAmount;
    }

    public String getRewardUsePoint() {
        return rewardUsePoint;
    }

    public void setRewardUsePoint(String rewardUsePoint) {
        this.rewardUsePoint = rewardUsePoint;
    }

    public String getOnlinePaymentAmount() {
        return onlinePaymentAmount;
    }

    public void setOnlinePaymentAmount(String onlinePaymentAmount) {
        this.onlinePaymentAmount = onlinePaymentAmount;
    }

    public String getOrderInstruction() {
        return orderInstruction;
    }

    public void setOrderInstruction(String orderInstruction) {
        this.orderInstruction = orderInstruction;
    }

    public String getOrderIsPaid() {
        return orderIsPaid;
    }

    public void setOrderIsPaid(String orderIsPaid) {
        this.orderIsPaid = orderIsPaid;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderCreatedAt() {
        return orderCreatedAt;
    }

    public void setOrderCreatedAt(String orderCreatedAt) {
        this.orderCreatedAt = orderCreatedAt;
    }

    public String getOrderUpdatedAt() {
        return orderUpdatedAt;
    }

    public void setOrderUpdatedAt(String orderUpdatedAt) {
        this.orderUpdatedAt = orderUpdatedAt;
    }
}
