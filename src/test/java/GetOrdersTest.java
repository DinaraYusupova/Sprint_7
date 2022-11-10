import Clients.OrderClient;
import DataGenerator.OrderGenerator;
import Models.Order;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersTest {
    private Order order;
    private OrderClient orderClient;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Create orders with different colour")
    public void GetOrders() {
        order = OrderGenerator.getDefaultDataWithoutColour();
        orderClient.create(order); //создаю хоть 1 заказ, чтобы список не был пуст
        ValidatableResponse response = orderClient.getListOfOrders();
        checkOrders(response);
    }

    @Step("Compare orders not null")
    public void checkOrders(ValidatableResponse response){
        response.assertThat().body("orders", notNullValue());
    }

}
