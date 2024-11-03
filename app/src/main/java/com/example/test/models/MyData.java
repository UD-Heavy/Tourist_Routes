package com.example.test.models;

/**
 * Класс, представляющий модель данных для элемента списка, отображаемого в интерфейсе.
 * Содержит заголовок и ресурс изображения для элемента.
 */
public class MyData {
    private final String title;
    private final int imageResource;

    /**
     * Конструктор, инициализирующий объект данными для заголовка и ресурса изображения.
     *
     * @param title         заголовок элемента
     * @param imageResource идентификатор ресурса изображения
     */
    public MyData(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    /**
     * Получает заголовок элемента.
     *
     * @return заголовок элемента
     */
    public String getTitle() {
        return title;
    }

    /**
     * Получает идентификатор ресурса изображения элемента.
     *
     * @return идентификатор ресурса изображения
     */
    public int getImageResource() {
        return imageResource;
    }
}
