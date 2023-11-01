package com.example.pastry_treat.Models;

public class OrderedItemModel {
    private String imageUri;
    private String ownerId, foodId, buyerId;
    private String foodName, restaurentName;
    private Integer quantity;
    private Double totalPrice;
    private String orderId;

    private String buyerAddresss;


    public OrderedItemModel() {
    }

    public OrderedItemModel(String imageUri, String ownerId, String foodId, String buyerId, String foodName, String restaurentName, Integer quantity, Double totalPrice, String orderId, String buyerAddresss) {
        this.imageUri = imageUri;
        this.ownerId = ownerId;
        this.foodId = foodId;
        this.buyerId = buyerId;
        this.foodName = foodName;
        this.restaurentName = restaurentName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.buyerAddresss = buyerAddresss;
    }

    public String getBuyerAddresss() {
        return buyerAddresss;
    }

    public void setBuyerAddresss(String buyerAddresss) {
        this.buyerAddresss = buyerAddresss;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurentName() {
        return restaurentName;
    }

    public void setRestaurentName(String restaurentName) {
        this.restaurentName = restaurentName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
