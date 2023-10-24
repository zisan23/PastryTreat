package com.example.appadmin.Models;

public class RestaurentModel {
    String restaurentName, ownerId, location, profile_image;

    public RestaurentModel() {
    }

    public RestaurentModel(String restaurentName, String ownerId, String location, String profile_image) {
        this.restaurentName = restaurentName;
        this.ownerId = ownerId;
        this.location = location;
        this.profile_image = profile_image;
    }

    public String getRestaurentName() {
        return restaurentName;
    }

    public void setRestaurentName(String restaurentName) {
        this.restaurentName = restaurentName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
