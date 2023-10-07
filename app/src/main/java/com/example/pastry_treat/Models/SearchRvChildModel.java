package com.example.pastry_treat.Models;

public class SearchRvChildModel {
    public int image;
    public String name;
    public double rating;

    public String restaurent;

    public SearchRvChildModel(int image, String name, Double rating, String restaurent) {
        this.image = image;
        this.name = name;
        this.rating = rating;
        this.restaurent = restaurent;
    }


}
