package com.example.pastry_treat;

public class UserModel {
    private String name, email, address, password;

    public UserModel() {

    }



    public UserModel(String name, String email, String address, String password){
        this.name = name;
        this.email = email;
        this.address = address;
        this.password = password;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
