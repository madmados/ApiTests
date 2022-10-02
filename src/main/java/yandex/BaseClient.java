package yandex;

import config.Config;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    protected RequestSpecification getSpec() {
        return given().log().all()
                .baseUri(Config.BASE_URL)
                .header("Content-Type", "application/json");
    }
}