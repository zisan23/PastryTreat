package com.example.pastry_treat.Models;

public class FoodModel {
    String foodId,ownerId,name,description, imageUri,restaurantName;
     Double price;


    public FoodModel(String foodId, String ownerId, String name, String description, String imageUri, String restaurantName, Double price) {
        this.foodId = foodId;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
        this.restaurantName = restaurantName;
        this.price = price;
    }

    public FoodModel() {

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
