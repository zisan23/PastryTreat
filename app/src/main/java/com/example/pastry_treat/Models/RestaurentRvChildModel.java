package com.example.pastry_treat.Models;

public class RestaurentRvChildModel {
    public int image;
    public String productName, description;
    public Double price;

    public RestaurentRvChildModel(int image, String productName, String description, Double price) {
        this.image = image;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public RestaurentRvChildModel() {
    }
}
