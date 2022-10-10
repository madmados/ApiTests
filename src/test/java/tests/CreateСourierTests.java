package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import yandex.courier.Courier;
import yandex.courier.CourierClient;
import yandex.courier.CourierCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CreateСourierTests {

    Courier courier;
    CourierClient courierClient;
    private boolean isOk;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (isOk){
            CourierCredentials courierCredentials = CourierCredentials.getCredentials(courier);
            int courierId = courierClient.loginCourier(courierCredentials).extract().path("id");
            courierClient.deleteCourier(courierId).statusCode(200);
        }
    }


    @Test
    @DisplayName("Creating courier - Success")
    @Description("Успешное создание курьера")
    public void testCreatingСourierSuccess() {

        courier = Courier.getRandomCourier();
        isOk = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");

        assertTrue(isOk);
    }


    @Test
    @DisplayName("Creating courier with required fields - Success")
    @Description("Успешное создание курьера при передаче в запросе всех обязательных полей")
    public void testCreatingСourierWithRequiredFieldsSuccess() {

        courier = Courier.getCourierWithRequiredFields();
        isOk = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");

        assertTrue(isOk);
    }


    @Test
    @DisplayName("Creating courier without login - Failure")
    @Description("Неуспешное создание курьера при отсутствии в запросе поля login")
    public void testCreatingСourierWithoutLoginFailure() {

        courier = Courier.getCourierWithoutLogin();
        courierClient.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Creating courier without password - Failure")
    @Description("Неуспешное создание курьера при отсутствии в запросе поля password")
    public void testCreatingСourierWithoutPasswordFailure() {

        courier = Courier.getCourierWithoutPassword();
        courierClient.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Creating courier with duplicate login - Failure")
    @Description("Неуспешное создание курьера с login как у существующего курьера")
    public void testCreatingСourierWithDuplicateLoginFailure() {

        courier = Courier.getRandomCourier();
        isOk = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");
        assertTrue(isOk);

        Courier duplicateCourier = Courier.getCourierDuplicateLogin(courier);
        courierClient.createCourier(duplicateCourier)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}