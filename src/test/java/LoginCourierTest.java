import Clients.CourierClient;
import DataGenerator.CourierGenerator;
import DataGenerator.CredentialsGenerator;
import Models.Courier;
import Models.Credentials;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    private Courier courier;
    private Credentials credentials;
    private CourierClient courierClient;
    private int statusCode = SC_OK;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("login courier and check statusCode and id")
    public void loginCourier() {
        courier = CourierGenerator.getDefault();
        credentials = CredentialsGenerator.getDefault();
        courierClient.create(courier); // создаю курьера, чтобы он точно был в базе
        ValidatableResponse responseLogin = courierClient.login(credentials);
        compareStatusCode(responseLogin, statusCode);
        checkCourierId(responseLogin);
    }


    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response, int code){
        response.statusCode(code);
    }

    @Step("Compare courier id")
    public void checkCourierId(ValidatableResponse response){
        response.assertThat().body("id",  notNullValue());
    }
}
