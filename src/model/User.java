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
    
    public void setProfile(String newName, String newEmail, String newAddress, String newOrg) {
        this.name = newName;
        this.email = newEmail;
        this.address = newAddress;
        this.org = newOrg;
    }
    
    public User(String username) {
        this.username = username;
    }
}
