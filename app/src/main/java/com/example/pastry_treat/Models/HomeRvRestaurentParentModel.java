package com.example.pastry_treat.Models;

import java.util.List;

public class HomeRvRestaurentParentModel {
    String title;

    List<HomeRvRestaurentChildModel> childModelList;

    public HomeRvRestaurentParentModel(String title, List<HomeRvRestaurentChildModel> childModelList) {
        this.title = title;
        this.childModelList = childModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HomeRvRestaurentChildModel> getChildModelList() {
        return childModelList;
    }

    public void setChildModelList(List<HomeRvRestaurentChildModel> childModelList) {
        this.childModelList = childModelList;
    }
}
