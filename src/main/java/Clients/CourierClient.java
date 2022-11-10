package Clients;

import Models.Courier;
import Models.Credentials;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client{

    private  static final String PATH_CREATE = "/api/v1/courier";
    private  static final String PATH_LOGIN = "/api/v1/courier/login";

    private  static  String PATH_DELETE = "/api/v1/courier/";
    @Step("Create courier")
    public ValidatableResponse create(Courier courier){
        return given()
                .spec(getSpec())
                .log().all()
                .body(courier)
                .when()
                .post(PATH_CREATE)
                .then()
                .log().all();
    }
    @Step("Login courier")
    public ValidatableResponse login(Credentials credentials){
        return given()
                .spec(getSpec())
                .log().all()
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then()
                .log().all();
    }
    @Step("Delete courier")
    public ValidatableResponse delete(int id){
        String json = "{\"id\": " + id + "}";
        String FULL_PATH = PATH_DELETE+id;
        return given()
                .spec(getSpec())
                .log().all()
                .body(json)
                .when()
                .delete(FULL_PATH)
                .then()
                .log().all();
    }
}
