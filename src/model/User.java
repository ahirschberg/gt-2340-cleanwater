package model;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by jster on 9/20/2016.
 */
public class User {
    private String username;

    //Profile
    private String name;
    private String email;
    private String address;
    private String org;
    
    public User(String username) {
        this.username = username;
    }
}
