package com.example.appadmin.Models;

public class OrderedItemModel {
    private String imageUri;
    private String ownerId, foodId, buyerId, buyerAddress;
    private String foodName, restaurentName;
    private Integer quantity;
    private Double totalPrice;
    private String orderId;


    public OrderedItemModel() {
    }


    public OrderedItemModel(String imageUri, String ownerId, String foodId, String buyerId, String buyerAddress, String foodName, String restaurentName, Integer quantity, Double totalPrice, String orderId) {
        this.imageUri = imageUri;
        this.ownerId = ownerId;
        this.foodId = foodId;
        this.buyerId = buyerId;
        this.buyerAddress = buyerAddress;
        this.foodName = foodName;
        this.restaurentName = restaurentName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
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
