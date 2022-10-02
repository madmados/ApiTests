package yandex.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import yandex.BaseClient;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
@Data
public class CreateOrderTests extends BaseClient {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrderTests(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
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

        CreateOrderTests createOrderTests = new CreateOrderTests(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        int OrderTrack = getSpec()
                .body(createOrderTests)
                .when()
                .post("/api/v1/orders")
                .then().log().all()
                .assertThat()
                .statusCode(201) //запрос возвращает правильный код ответа - 201
                .extract().path("track");

        assertNotNull(OrderTrack); //успешный запрос возвращает track заказа
    }
}