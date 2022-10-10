package yandex.courier;

import io.qameta.allure.Step;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class Courier {

    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Формирование данных для создания курьера со случайными значениями полей")
    public static Courier getRandomCourier() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    @Step("Формирование данных для создания курьера с обязательными полями")
    public static Courier getCourierWithRequiredFields() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(10),
                null
        );
    }

    @Step("Формирование данных для создания курьера без обязательного поля (логина)")
    public static Courier getCourierWithoutLogin() {
        return new Courier(
                null,
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    @Step("Формирование данных для создания курьера без обязательного поля (пароля)")
    public static Courier getCourierWithoutPassword() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(10),
                null,
                RandomStringUtils.randomAlphabetic(8)
        );
    }

    @Step("Формирование данных для создания курьера с дублированием логина")
    public static Courier getCourierDuplicateLogin(Courier courier) {
        return new Courier(
                courier.getLogin(),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(8)
        );
    }
}