package DataGenerator;

import Models.Order;

import java.util.List;

public class OrderGenerator {
    public static Order getDefaultDataWithoutColour(){
        return new Order("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                List.of(""));
    }

    public static Order getDataColourBlack(){
        Order order = OrderGenerator.getDefaultDataWithoutColour();
        order.setColor(List.of("BLACK"));
        return order;
    }
    public static Order getDataColourGrey(){
        Order order = OrderGenerator.getDefaultDataWithoutColour();
        order.setColor(List.of("GREY"));
        return order;
    }
    public static Order getDataColourBlackAndGrey(){
        Order order = OrderGenerator.getDefaultDataWithoutColour();
        order.setColor(List.of("BLACK","GREY"));
        return order;
    }
}
