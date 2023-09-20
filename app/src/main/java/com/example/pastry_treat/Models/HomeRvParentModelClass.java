package com.example.pastry_treat.Models;

import java.util.List;

public class HomeRvParentModelClass {
    public String title;

    public List<HomeRvChildModelClass> homeRvChildModelClassList;

    public HomeRvParentModelClass(String title, List<HomeRvChildModelClass> homeRvChildModelClassList) {
        this.title = title;
        this.homeRvChildModelClassList = homeRvChildModelClassList;
    }
}
