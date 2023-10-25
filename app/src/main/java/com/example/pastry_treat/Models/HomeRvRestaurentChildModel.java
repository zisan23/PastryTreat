package com.example.pastry_treat.Models;

public class HomeRvRestaurentChildModel {
    String image;
    String name;
    String address;
    String shortDescription;
    String ownerId;

    public HomeRvRestaurentChildModel(String image, String name, String address, String shortDescription, String ownerId) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.shortDescription = shortDescription;
        this.ownerId = ownerId;
    }

    public HomeRvRestaurentChildModel() {

    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
