package com.example.test.models;

/**
 * Класс, представляющий модель данных для элемента списка, отображаемого в интерфейсе.
 * Содержит заголовок и ресурс изображения для элемента.
 */
public class MyData {
    private String title;
    private int imageResId;

    /**
     * Конструктор, инициализирующий объект данными для заголовка и ресурса изображения.
     *
     * @param title         заголовок элемента
     * @param imageResId идентификатор ресурса изображения
     */
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
