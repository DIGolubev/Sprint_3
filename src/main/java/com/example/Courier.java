package com.example;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private final String login;
    private final String password;
    private final String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
        firstName = null;
    }

    /**
     * @return Возвращает курьера с рандомно сгенерированными логином, паролем, именем
     */
    @Step("Создать случайные парамеры для курьера")
    public static Courier getRandomCourier() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        Allure.addAttachment("Логин: ", courierLogin);
        Allure.addAttachment("Пароль: ", courierPassword);
        Allure.addAttachment("Имя: ", courierFirstName);

        return new Courier(courierLogin, courierPassword, courierFirstName);
    }

    @Override
    public String toString() {
        return "Courier {" +
                "login:" + login + "," +
                "password:" + password + "," +
                "firstName:" + firstName + "}";
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }
}
