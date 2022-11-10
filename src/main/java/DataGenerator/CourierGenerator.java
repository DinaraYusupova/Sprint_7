package DataGenerator;

import Models.Courier;

import java.sql.Timestamp;


public class CourierGenerator {
    public static Courier getNewData(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime());
        return new Courier(login,"123456","chebyrashka");
    }

    public static Courier getDataWithoutLogin() {
        return new Courier("","123456","chebyrashka");
    }

    public static Courier getDataWithoutPassword() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime());
        return new Courier(login,"","chebyrashka");
    }

    public static Courier getDataWithoutFirstName() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime());
        return new Courier(login,"123456","");
    }

    public static Courier getDefault() {
        return new Courier("loginApiDefault","123456","chebyrashka");
    }
}
