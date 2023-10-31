package com.example.pastry_treat.Models;

public class WishListModel {
    public String userId, ownerId, restaurantName,restAddress, imageUri;
    public boolean liked;

    public WishListModel() {
    }

    public WishListModel(String userId, String ownerId, String restaurantName, String imageUri,String restAddress, boolean liked) {
        this.userId = userId;
        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.imageUri = imageUri;
        this.liked = liked;
        this.restAddress = restAddress;
    }

    public String getRestAddress() {
        return restAddress;
    }

    public void setRestAddress(String restAddress) {
        this.restAddress = restAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
