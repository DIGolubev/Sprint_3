package com.example;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public String[] color;

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime,
                 String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;

    }

    /**
     *
     * @return создает случайный заказ
     */
    @Step("Создать случайные параметры для заказа")
    public static Order getRandomOrder(String[] getColor){

        String firstName = RandomStringUtils.randomAlphabetic(10);
        String lastName = RandomStringUtils.randomAlphabetic(10);
        String address = RandomStringUtils.randomAlphabetic(10);
        int metroStation = (int) (Math.random()*237);
        String phone = RandomStringUtils.randomAlphabetic(11);
        int rentTime = (int) (Math.random()*7);
        String comment = RandomStringUtils.randomAlphabetic(10);

        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2030);
        gc.set(Calendar.YEAR, year);
        String deliveryDate = gc.get(Calendar.YEAR) + "-" + Calendar.MONTH + "-" + Calendar.DAY_OF_YEAR;

        Allure.addAttachment("Имя", firstName);
        Allure.addAttachment("Фамилия", lastName);
        Allure.addAttachment("Адрес", address);
        Allure.addAttachment("Станция метро", String.valueOf(metroStation));
        Allure.addAttachment("Телефон", phone);
        Allure.addAttachment("Срок аренды", String.valueOf(rentTime));
        Allure.addAttachment("Когда привести самокат", deliveryDate);
        Allure.addAttachment("Комментарий", comment);
        Allure.addAttachment("Цвет", Arrays.toString(getColor));

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, getColor);
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}
