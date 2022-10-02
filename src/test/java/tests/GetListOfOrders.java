package yandex.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import yandex.order.ListOfOrders;
import yandex.order.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOfOrders {

    ListOfOrders listOfOrders;
    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Get list of orders without parameters - Success")
    @Description("Успешное получение списка заказов без параметров в запросе")
    public void testGetListOfOrdersWithoutParametersSuccess() {

        listOfOrders = ListOfOrders.getDataForListOfOrdersWithoutParameters();
        orderClient.getListOfOrders(listOfOrders)
                .statusCode(200) //запрос возвращает правильный код ответа - 200
                .body("orders", notNullValue()); //успешный запрос возвращает orders - список заказов
    }
}