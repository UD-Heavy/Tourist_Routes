package com.example.test.models;


/**
 * Класс, представляющий модель пользователя в приложении. Содержит
 * данные о пользователе, включая имя, почту, пароль, изображение профиля
 */
public class User {

    private String name;
    private String email;
    private String password;
    private String image;


    /**
     * Конструктор, принимающий параметры для инициализации данных пользователя.
     *
     * @param name     имя
     * @param email    электронная почта
     * @param password пароль
     * @param image изображение
     */
    public User(String name, String email, String password, String image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    /**
     * Конструктор по умолчанию. Создает пустого пользователя без начальных данных.
     */
    public User() {
    }

    /**
     * Конструктор, принимающий параметры для инициализации данных пользователя.
     *
     * @param name     имя
     * @param email    электронная почта
     * @param password пароль
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
