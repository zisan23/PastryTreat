package com.example.pastry_treat.Models;

import java.util.List;

public class SearchRvParentModel {
    public String title;

    public List<SearchRvChildModel> searchRvChildModelList;

    public SearchRvParentModel(String title, List<SearchRvChildModel> searchRvChildModelList) {
        this.title = title;
        this.searchRvChildModelList = searchRvChildModelList;
    }
}
