package Clients;

import Models.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client{
    private  static final String PATH_CREATE = "/api/v1/orders";
    private  static final String PATH_LIST_OF_ORDERS = "/api/v1/orders?courierId=";

    private  static final String PATH_CANCEL = "/api/v1/orders/cancel?track=";
    @Step("Create order")
    public ValidatableResponse create(Order order){
        return given()
                .spec(getSpec())
                .log().all()
                .body(order)
                .when()
                .post(PATH_CREATE)
                .then()
                .log().all();
    }
    @Step("Get List of orders")
    public ValidatableResponse getListOfOrders(){
        return given()
                .spec(getSpec())
                .log().all()
                .get(PATH_LIST_OF_ORDERS)
                .then()
                .log().all();
    }
    @Step("Cancel order")
    public void cancelOrder(Integer track) {
        String json = "{\"track\": "+ track + "}";
        String FULL_PATH = PATH_CANCEL + track;
        ValidatableResponse cancel = given()
                .spec(getSpec())
                .log().all()
                .body(json)
                .when()
                .put(FULL_PATH)
                .then()
                .log().all();
    }
}
