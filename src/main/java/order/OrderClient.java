package yandex.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import yandex.BaseClient;

public class OrderClient extends BaseClient {

    private final String ROOT_ORDER = "/api/v1/orders";

    @Step("Получение списка заказов")
    public ValidatableResponse getListOfOrders(ListOfOrders listOfOrders) {

        return getSpec()
                .body(listOfOrders)
                .when()
                .get(ROOT_ORDER)
                .then().log().all()
                .assertThat();
    }

}