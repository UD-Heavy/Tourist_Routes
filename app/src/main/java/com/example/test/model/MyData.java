package com.example.test.model;

public class MyData {
    private final String title;
    private final int imageResource;

    public MyData(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }
}
