package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Story("Создание курьера")
public class NewCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private Response response;

    private final String courierLogin = RandomStringUtils.randomAlphabetic(10);

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }

    @After
    public void tearDown() {
        int actualCodResponse = response.statusCode();
        int expectedCodResponse = 201;

        if (actualCodResponse == expectedCodResponse) {
            int courierId = courierClient.login(CourierCredentials.from(courier)).then().extract().path("id");
            courierClient.delete(courierId);
            System.out.println("Курьер удален");
        } else {
            System.out.println("Нет курьеров для удаления");
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void courierCreateSuccess() {
        response = courierClient.create(courier);
        boolean isCourierCreated = true;

        assertEquals("Курьер не создан", isCourierCreated, response.then().extract().path("ok"));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Нельзя создать двух одинаковых курьеров")
    public void twoIdenticalCouriersNotCreate() {
        response = courierClient.create(courier);
        Response responseCreateIdenticalCouriers = courierClient
                .create(new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName()));

        String messageIdenticalCouriers = "Этот логин уже используется. Попробуйте другой.";
        assertEquals("Созданы два одинаковых курьера", messageIdenticalCouriers,
                responseCreateIdenticalCouriers.then().extract().path("message"));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Если создать курьера без логина, возвращается ошибка")
    public void loginMustBeFilledIn() {
        courier = new Courier("",
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10));
        response = courierClient.create(courier);

        String courierWithoutLogin = "Недостаточно данных для создания учетной записи";
        assertEquals("Создан курьер без логина",
                courierWithoutLogin, response.then().extract().path("message"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Если создать курьера без пароля, возвращается ошибка")
    public void passwordMustBeFilledIn() {
        courier = new Courier(RandomStringUtils.randomAlphabetic(10),
                "",
                RandomStringUtils.randomAlphabetic(10));
        response = courierClient.create(courier);

        String courierWithoutLogin = "Недостаточно данных для создания учетной записи";
        assertEquals("Создан курьер без пароля",
                courierWithoutLogin, response.then().extract().path("message"));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Если создать курьера с логином, который уже есть, возвращается ошибка")
    public void courierCreateWithExistLogin() {
        courier = new Courier(courierLogin,
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10));
        Courier courierWithExistLogin = new Courier(courierLogin,
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10));
        response = courierClient.create(courier);
        Response responseCourierWithExistLogin = courierClient.create(courierWithExistLogin);

        String courierCreateDoubleLogin = "Этот логин уже используется. Попробуйте другой.";
        assertEquals("Курьер создан с логином, который уже есть", courierCreateDoubleLogin,
                responseCourierWithExistLogin.then().extract().path("message"));
    }
}
