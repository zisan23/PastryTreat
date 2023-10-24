package com.example.appadmin.Models;

public class FoodModel {
    public String foodId,ownerId,name,description, imageUri;
    public Double price;


    public FoodModel(String foodId, String ownerId, String name, String description, String imageUri, Double price) {
        this.foodId = foodId;
        this.ownerId = ownerId;
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
        this.price = price;
    }

    public FoodModel() {

    }
}
