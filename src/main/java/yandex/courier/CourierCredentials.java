package yandex.courier;

import io.qameta.allure.Step;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class CourierCredentials {

    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Step("Формирование данных для авторизации курьера")
    public static CourierCredentials getCredentials(Courier courier){
        return new CourierCredentials(
                courier.getLogin(),
                courier.getPassword()
        );
    }

    @Step("Формирование данных для авторизации курьера без обязательного поля (логина)")
    public static CourierCredentials getCredentialsWithoutLogin(Courier courier) {
        return new CourierCredentials(
                null,
                courier.getPassword()
        );
    }

    @Step("Формирование данных для авторизации курьера без обязательного поля (пароля)")
    public static CourierCredentials getCredentialsWithoutPassword(Courier courier) {
        return new CourierCredentials(
                courier.getLogin(),
                null
        );
    }

    @Step("Формирование данных для авторизации курьера с некорректным логином")
    public static CourierCredentials getCredentialsWithWrongLogin(Courier courier) {
        return new CourierCredentials(
                RandomStringUtils.randomAlphanumeric(12),
                courier.getPassword()
        );
    }

    @Step("Формирование данных для авторизации курьера с некорректным паролем")
    public static CourierCredentials getCredentialsWithWrongPassword(Courier courier) {
        return new CourierCredentials(
                courier.getLogin(),
                RandomStringUtils.randomAlphanumeric(12)
        );
    }

    @Step("Формирование данных для авторизации несуществующего курьера")
    public static CourierCredentials getCredentialsNonexistentCourier(Courier courier) {
        return new CourierCredentials(
                RandomStringUtils.randomAlphanumeric(15),
                RandomStringUtils.randomAlphanumeric(15)
        );
    }
}