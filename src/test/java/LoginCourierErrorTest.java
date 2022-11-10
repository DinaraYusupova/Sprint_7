import Clients.CourierClient;
import DataGenerator.CredentialsGenerator;
import Models.Credentials;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierErrorTest {
    private Credentials credentials;
    private CourierClient courierClient;
    private int statusCode;
    private  String message;
    private final static String messageForBadRequest = "Недостаточно данных для входа";
    private final static String messageForNotFound = "Учетная запись не найдена";


    public LoginCourierErrorTest(Credentials credentials, int statusCode, String message) {
        this.credentials = credentials;
        this.statusCode = statusCode;
        this.message = message;
    }

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }


    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                {CredentialsGenerator.getDataWithoutLogin(),SC_BAD_REQUEST,messageForBadRequest},
                {CredentialsGenerator.getDataWithoutPassword(),SC_BAD_REQUEST,messageForBadRequest},
                {CredentialsGenerator.getNewData(),SC_NOT_FOUND,messageForNotFound},
        };
    }
    @Test
    @DisplayName("login courier with non-existent data")
    public void loginCourierWithErrorData() {
        ValidatableResponse responseLogin = courierClient.login(credentials);
        compareStatusCode(responseLogin, statusCode);
        compareResponseMessage(responseLogin,message);
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
