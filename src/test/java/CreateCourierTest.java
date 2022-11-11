import Clients.CourierClient;
import DataGenerator.CourierGenerator;
import Models.Courier;
import Models.Credentials;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private int id;
    private final static String messageForConflict = "Этот логин уже используется. Попробуйте другой.";

    @Before
    public void setUp(){
        courier = CourierGenerator.getNewData();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Check status code, message and id of creating courier")
    public void createNewCourier() {
        ValidatableResponse responseCreate = courierClient.create(courier);
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        compareStatusCode(responseCreate,SC_CREATED);
        compareCorrectResponseMessage(responseCreate);
        checkId(responseLogin);
    }

    @Test
    @DisplayName("Check status code, message of creating double courier")
    public void createDoubleCourier() {
        courierClient.create(courier); //создаем нового курьера
        ValidatableResponse doubleResponseCreate = courierClient.create(courier); //создаем еще одного курьера с теми же данными
        ValidatableResponse responseLogin = courierClient.login(Credentials.from(courier));
        id = responseLogin.extract().path("id");
        compareStatusCode(doubleResponseCreate,SC_CONFLICT);
        compareResponseMessage(doubleResponseCreate,messageForConflict);
    }

    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response, int code){
        response.statusCode(code);
    }

    @Step("Compare successful response")
    public void compareCorrectResponseMessage(ValidatableResponse response){
        response.assertThat().body("ok", equalTo(true));
    }
    @Step("Compare response message")
    public void compareResponseMessage(ValidatableResponse response, String message){
        response.assertThat().body("message", equalTo(message));
    }
    @Step("Compare id not null")
    public void checkId(ValidatableResponse response){
        response.assertThat().body("id", notNullValue());
    }
}
