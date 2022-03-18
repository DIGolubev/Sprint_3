package com.example;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;

@Story("Логин курьера")
public class LoginCourierTest {
    private CourierClient courierClient;
    CourierCredentials courierCredentials;
    private Response responseLogin;

    private Integer courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
        courierClient.create(courier);
        courierCredentials = new CourierCredentials();
    }

    @After
    public void tearDown() {
        if (null != courierId) {
            courierClient.delete(courierId);
            courierId = null;
            System.out.println("Курьер удален");
        } else {
            System.out.println("Нет курьеров для удаления");
        }
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Курьер может авторизоваться, успешный запрос возвращает id и код ответа 200")
    public void courierLoginSuccess() {
        responseLogin = courierClient.login(CourierCredentials.from(courier));
        courierId = responseLogin.path("id");

        int expectedCodResponse = 200;
        assertEquals("Код ответа не соответствует 200",expectedCodResponse, responseLogin.statusCode());
        assertNotSame("Авторизация курьера не успешна", 0, courierId);
    }

    @Test
    @DisplayName("Логин курьера без логина")
    @Description("Если какого-то поля нет (логин), запрос возвращает ошибку")
    public void courierEmptyLogin() {
        courier = new Courier("", courier.getPassword());
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        String messageEmptyLogin = "Недостаточно данных для входа";
        assertEquals("Ошибка. авторизация курьера без логина", messageEmptyLogin,
                responseLogin.then().extract().path("message"));
    }

    @Test
    @DisplayName("Логин курьера без пароля")
    @Description("Если какого-то поля нет (пароля), запрос возвращает ошибку")
    public void courierEmptyPassword() {
        courier = new Courier(courier.getLogin(), "");
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        String messageEmptyPassword = "Недостаточно данных для входа";
        assertEquals("Ошибка. авторизация курьера без пароля", messageEmptyPassword,
                responseLogin.then().extract().path("message"));
    }

    @Test
    @DisplayName("Логин курьера с неверным логином")
    @Description("Если неправильный логин, запрос возвращает ошибку")
    public void courierInvalidLogin() {
        courier = new Courier(courier.getLogin() + 1, courier.getPassword());
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        String messageInvalidLogin = "Учетная запись не найдена";
        assertEquals("Ошибка. Авторизация с неверным логином", messageInvalidLogin,
                responseLogin.then().extract().path("message"));
    }

    @Test
    @DisplayName("Логин курьера с неверным паролем")
    @Description("Если неправильный пароль, запрос возвращает ошибку")
    public void courierInvalidPassword() {
        courier = new Courier(courier.getLogin(), courier.getPassword() + 1);
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        String messageInvalidPassword = "Учетная запись не найдена";
        assertEquals("Ошибка. Авторизация с неверным паролем", messageInvalidPassword,
                responseLogin.then().extract().path("message"));
    }
}
