package yandex.scooter.tests;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import yandex.courier.Courier;
import yandex.courier.CourierClient;
import yandex.courier.CourierCredentials;

public class LoginCourierTests {

    Courier courier;
    CourierClient courierClient;
    CourierCredentials courierCredentials;
    CourierCredentials courierCredentialsCorrect;

    @Before
    public void setUp() {

        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
        courierClient.createCourier(courier).statusCode(201);
    }

    @After
    public void tearDown() {

        courierCredentialsCorrect = CourierCredentials.getCredentials(courier);
        int courierId = courierClient.loginCourier(courierCredentialsCorrect).extract().path("id");
        courierClient.deleteCourier(courierId).statusCode(200);
    }


    @Test
    @DisplayName("Login courier with required fields - Success")
    @Description("Успешная авторизация курьера при передаче всех обязательных полей")
    public void testLoginСourierWithRequiredFieldsSuccess() {

        courierCredentials = CourierCredentials.getCredentials(courier);
        courierClient.loginCourier(courierCredentials)
                .statusCode(200) //запрос возвращает правильный код ответа - 200
                .body("id", notNullValue()); //успешный запрос возвращает id != null
    }


    @Test
    @DisplayName("Login courier without login - Failure")
    @Description("Неуспешная авторизация курьером при отсутствии поля login")
    public void testLoginСourierWithoutLoginFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithoutLogin(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(400) //запрос возвращает код ответа - 400
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Недостаточно данных для входа", message); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Login courier without password - Failure")
    @Description("Неуспешная авторизация курьером при отсутствии поля password")
    public void testLoginСourierWithoutPasswordFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithoutPassword(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(400) //запрос возвращает код ответа - 400 (Баг: по документации - 400, по факту - 504)
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Недостаточно данных для входа", message); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Login courier with wrong login - Failure")
    @Description("Неуспешная авторизация курьером с некорректным значением login")
    public void testLoginСourierWithWrongLoginFailure() {

        courierCredentials = CourierCredentials.getCredentialsWithWrongLogin(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(404) //запрос возвращает код ответа - 404
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Login courier with wrong password - Failure")
    @Description("Неуспешная авторизация курьером с некорректным значением password")
    public void testLoginСourierWithWrongPasswordFailure() { //авторизоваться курьером невозможно с некорректным паролем

        courierCredentials = CourierCredentials.getCredentialsWithWrongPassword(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(404) //запрос возвращает код ответа - 404
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Login nonexistent courier - Failure")
    @Description("Авторизация несуществующим курьером невозможна")
    public void testLoginNonexistentCourierFailure() {

        courierCredentials = CourierCredentials.getCredentialsNonexistentCourier(courier);
        String message = courierClient.loginCourier(courierCredentials)
                .statusCode(404) //запрос возвращает код ответа - 404
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message); //неуспешный запрос возвращает корректное сообщение
    }
}