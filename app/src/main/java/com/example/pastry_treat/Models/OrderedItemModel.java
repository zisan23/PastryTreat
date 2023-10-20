package com.example.pastry_treat.Models;

public class OrderedItemModel {
    private Integer image;
    private String foodName, restaurentName;
    private Integer totalPrice, quantity;

    public OrderedItemModel(Integer image, String foodName, String restaurentName, Integer totalPrice, Integer quantity) {
        this.image = image;
        this.foodName = foodName;
        this.restaurentName = restaurentName;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
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

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
