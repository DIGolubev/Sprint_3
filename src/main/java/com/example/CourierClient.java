package com.example;


import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Для использования API
 */
public class CourierClient extends ScooterRestClient{

    public final String COURIER_PATH = BASE_URL + "/courier/";
    public final String ORDER_PATH = BASE_URL + "/orders/";

    @Step("Создать курьера")
    public Response create (Courier courier){
        return given()
                .spec(getBaseSpec())
                .log()
                .all()
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Авторизоваться как курьер")
    public Response login (CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .log().all()
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH+"login");
    }

    @Step("Удалить курьера с id = {courierId}")
    public void delete (int courierId){
        given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
    @Step("Создать заказ")
    public Response order (Order order){
        return given()
                .spec(getBaseSpec())
                .log().all()
                .body(order)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Получить список заказов")
    public Response orderList (){
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .get(ORDER_PATH);
    }
}
