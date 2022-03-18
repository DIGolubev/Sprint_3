package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

@RunWith(Parameterized.class)
@Story("Создание заказа")
public class NewOrderTest {
    public CourierClient courierClient;
    public Order order;

    private final String[] color;
    private final int expectedCodResponse;

    public NewOrderTest(String[] color, int expectedCodResponse){
        this.color = color;
        this.expectedCodResponse = expectedCodResponse;
    }

    @Parameterized.Parameters(name = "Тестовые данные: цвет  = {0}; код = {1}")
    public static Object[][] data() {
        return new Object[][]{
                {new String[]{},201},
                {new String[]{"GREY"},201},
                {new String[]{"BLACK"},201},
                {new String[]{"GREY","BLACK"},201},
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Сделать заказ с различными цветами")
    @Description("Заказ можно создать, успешный запрос возвращает код ответа 201")
    public void orderCreateWithColor (){
        order = Order.getRandomOrder(color);
        Response response = courierClient.order(order);
        int actualCodResponse = response.statusCode();

        assertEquals("Ошибка, заказ не создан", expectedCodResponse, actualCodResponse);

        System.out.println("Заказ создан, код: " + actualCodResponse + " " + Arrays.toString(color));
    }

    @Test
    @DisplayName("Сделать заказ, в ответе есть track")
    public void orderCreate(){
        order = Order.getRandomOrder(color);
        Response response = courierClient.order(order);
        int isTrack = response.then().extract().path("track");

        assertNotSame("Заказ не создан, не найден track",0, isTrack);
    }
}
