package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.Data;
import yandex.order.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import yandex.BaseClient;
import yandex.order.OrderClient;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
@Data
public class CreateOrderTests extends BaseClient {
    OrderClient orderClient;
    Order order;
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }
    public CreateOrderTests(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
    @Parameterized.Parameters(name = "Тестовые данные: {8}")
    public static Object[][] getFieldsOrder() {

        return new Object[][]{
                {"Ivan", "Ivanov", "Moscow", "5", "+7(800)355-88-88", 3, "2022-09-16", "Quickly!", new String[]{"BLACK"}},
                {"Ivan", "Ivanov", "Moscow", "5", "+7(800)355-88-88", 3, "2022-09-16", "Quickly!", new String[]{"GREY"}},
                {"Ivan", "Ivanov", "Moscow", "5", "+7(800)355-88-88", 3, "2022-09-16", "Quickly!", new String[]{"BLACK", "GREY"}},
                {"Ivan", "Ivanov", "Moscow", "5", "+7(800)355-88-88", 3, "2022-09-16", "Quickly!", null},
                {"Ivan", "Ivanov", "Moscow", "5", "+7(800)355-88-88", 3, "2022-09-16", "Quickly!", new String[]{}}
        };
    }
    @Test
    @DisplayName("Create order with various color scooter")
    @Description("Успешное создание заказов с различными цветами самоката")
    public void testCreateOrderWithVariousColorScooter() {
        orderClient.getOrderTrack(order)
                .statusCode(201)
                .extract().path("track");
        assertNotNull(orderClient);
    }
}