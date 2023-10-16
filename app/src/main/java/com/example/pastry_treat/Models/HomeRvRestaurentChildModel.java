package com.example.pastry_treat.Models;

public class HomeRvRestaurentChildModel {
    int image;
    String name;
    String address;
    String shortDescription;

    public HomeRvRestaurentChildModel(int image, String name, String address, String shortDescription) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.shortDescription = shortDescription;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
