package com.example.test.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс, представляющий модель данных для элемента списка, отображаемого в интерфейсе.
 * Содержит заголовок и ресурс изображения для элемента.
 */
public class MyData implements Serializable {
    private String title;           // Заголовок объекта
    private int imageResId;         // хз чо эт эм
    private String address;         // Название улицы
    private String category;        // Категория/подкатегория
    private double rating;          // Рейтинг
    private int reviewCount;        // Количество отзывов
    private String workingHours;    // Часы работы
    private String openUntil;       // Открыто до
    private String phoneNumber;     // Контактный телефон
    private ArrayList<Integer> additionalImages; // Дополнительные изображения

    public MyData(String title, int imageResId) {

        this.title = title;
        this.imageResId = imageResId;
    }

    // новый конструктор
    public MyData(String title, int imageResId, String address, String category,
                  double rating, int reviewCount, String workingHours,
                  String openUntil, String phoneNumber, ArrayList<Integer> additionalImages) {

        this.title = title;
        this.imageResId = imageResId;
        this.address = address;
        this.category = category;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.workingHours = workingHours;
        this.openUntil = openUntil;
        this.phoneNumber = phoneNumber;
        this.additionalImages = additionalImages;
    }


    public MyData() {
    }

    // существующие геттеры
    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }

    // новые геттеры
    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public String getOpenUntil() {
        return openUntil;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Integer> getAdditionalImages() {
        return additionalImages;
    }

    // сеттеры для всех полей
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public void setOpenUntil(String openUntil) {
        this.openUntil = openUntil;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAdditionalImages(ArrayList<Integer> additionalImages) {
        this.additionalImages = additionalImages;
    }
}
