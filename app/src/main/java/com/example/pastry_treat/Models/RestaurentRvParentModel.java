package com.example.pastry_treat.Models;

import java.util.List;

public class RestaurentRvParentModel {
    public String title;

    public List<RestaurentRvChildModel> homeRvChildModelClassList;

    public RestaurentRvParentModel(String title, List<RestaurentRvChildModel> homeRvChildModelClassList) {
        this.title = title;
        this.homeRvChildModelClassList = homeRvChildModelClassList;
    }
}
