package yandex.order;

import io.qameta.allure.Step;
import lombok.Data;

@Data
public class ListOfOrders {

    private Integer courierId;
    private String nearestStation;
    private Integer limit;
    private Integer page;

    public ListOfOrders(Integer courierId, String nearestStation, Integer limit, Integer page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    @Step("Формирование данных для запроса списка заказов без параметров")
    public static ListOfOrders getDataForListOfOrdersWithoutParameters() {
        return new ListOfOrders(
                null,
                null,
                null,
                null
        );
    }
}
