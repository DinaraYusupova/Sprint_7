package DataGenerator;

import Models.Credentials;

import java.sql.Timestamp;

public class CredentialsGenerator {
    public static Credentials getDefault(){
        return new Credentials("loginApiDefault","123456");
    }
    public static Credentials getDataWithoutLogin(){
        return new Credentials("","123456");
    }
    public static Credentials getDataWithoutPassword(){
        return new Credentials("login","");
    }
    public static Credentials getNewData(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String login = "apitest"+ Long.toString(timestamp.getTime());
        return new Credentials(login,"123456");
    }

}
