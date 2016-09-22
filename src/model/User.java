package model;

/**
 * Created by jster on 9/20/2016.
 */
public class User {
    private byte[] authToken;

    public User(byte[] authToken) {
        this.authToken = authToken;
    }
}
