package com.example.appadmin.Models;

public class RestaurentRvFoodModel {
    public String id;
    public String imageUri;
    public String productName, description;
    public Double price;

    public RestaurentRvFoodModel(String id, String imageUri, String productName, String description, Double price) {
        this.id = id;
        this.imageUri = imageUri;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }
}
