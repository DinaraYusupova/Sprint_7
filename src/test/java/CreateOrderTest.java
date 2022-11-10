import Clients.OrderClient;
import DataGenerator.OrderGenerator;
import Models.Order;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private Order order;
    private OrderClient orderClient;

    Integer track;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] setData() {
        return new Object[][] {
                {OrderGenerator.getDataColourBlack()},
                {OrderGenerator.getDataColourGrey()},
                {OrderGenerator.getDataColourBlackAndGrey()},
                {OrderGenerator.getDefaultDataWithoutColour()},
        };
    }

    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp(){
        orderClient.cancelOrder(track);
    }


    @Test
    @DisplayName("Create orders with different colour")
    public void createOrder() {
        ValidatableResponse response = orderClient.create(order);
        compareStatusCode(response);
        checkTrack(response);
        track = response.extract().path("track");
    }

    @Step("Compare status code")
    public void compareStatusCode(ValidatableResponse response){
        response.statusCode(SC_CREATED);
    }
    @Step("Compare track not null")
    public void checkTrack(ValidatableResponse response){
        response.assertThat().body("track", notNullValue());
    }
}
