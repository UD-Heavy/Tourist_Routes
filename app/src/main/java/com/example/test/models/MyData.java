package com.example.test.models;

public class MyData {
    private String title;
    private int imageResId;

    public MyData(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}
