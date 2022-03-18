package com.example;

import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertNotSame;

@Story("Список заказов")
public class ListOrderTest {

    public CourierClient courierClient;
    public Order order;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        order = new Order("firstName", "lastName", "address", 0,
                "87776665544", 6, "2022-03-13", "comment", new String[]{});
    }

    @Test
    @DisplayName("В тело ответа возвращается список заказов")
    public void getOrders() {
        Response response = courierClient.order(order);
        int track = response.then().extract().path("track");
        Response responseList = courierClient.orderList();

        assertNotSame("Заказ не создан, не найден track", 0, track);
        assertNotSame("Заказ не найден", empty(), responseList.then().extract().path("orders"));
    }
}
