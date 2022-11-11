import Clients.CourierClient;
import DataGenerator.CourierGenerator;
import Models.Courier;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierWithoutDataTest {
    private Courier courier;
    private CourierClient courierClient;
    private int id;
    private final static String messageForBadRequest = "Недостаточно данных для создания учетной записи";

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    public CreateCourierWithoutDataTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                { CourierGenerator.getDataWithoutLogin()},
                { CourierGenerator.getDataWithoutPassword()},
                { CourierGenerator.getDataWithoutFirstName()},
        };
    }

    @Test
    @DisplayName("Check status code and message of creating courier with insufficient data")
    public void createCourierWithInsufficientDataAndCheckStatusCode(){
        ValidatableResponse responseCreate = courierClient.create(courier);
        compareStatusCode(responseCreate, SC_BAD_REQUEST);
        compareResponseMessage(responseCreate,messageForBadRequest);
    }

    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response, int code){
        response.statusCode(code);
    }

    @Step("Compare response message")
    public void compareResponseMessage(ValidatableResponse response, String message){
        response.assertThat().body("message", equalTo(message));
    }
}
