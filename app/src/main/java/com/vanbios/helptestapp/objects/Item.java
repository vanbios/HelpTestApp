package com.vanbios.helptestapp.objects;


import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

    private int id;
    private String title, category;
    private ArrayList<String> contentList;


    public Item (int id, String title, String category, ArrayList<String> contentList) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contentList = contentList;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getContentList() {
        return contentList;
    }

}
