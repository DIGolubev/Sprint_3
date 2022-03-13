import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.MatcherAssert.assertThat;

@Story("Список заказов")
public class ListOrderTest {

    public CourierClient courierClient;
    public Order order;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        order = new Order("firstName","lastName","address",0,
                "87776665544",6,"2022-03-13","comment", new String[]{});

    }

    @Test
    @DisplayName("В тело ответа возвращается список заказов")
    public void getOrders (){
        Response response = courierClient.order(order);
        int track = response.then().extract().path("track");
        Response responseList = courierClient.orderList();

        assertThat("Заказ не создан, не найден track",track,is(not(0)));
        assertThat("Заказ не найден",responseList.then().extract().path("orders"),not(empty()));
    }

}
