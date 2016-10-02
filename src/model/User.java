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
    private String street;
    private String city;
    private String state;
    private String country;
    private String org;
    
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
    public String getOrg() {
        return org;
    }
    
    public String getAddress() {
        return street
        + "\n"
        + city
        + ", "
        + state
        + "\n"
        + country;
    }
    
    public void setProfile(String newName, String newEmail, String newStreet, String newCity, String newState, String newCountry, String newOrg) {
        this.name = newName;
        this.email = newEmail;
        this.street = newStreet;
        this.city = newCity;
        this.state = newState;
        this.country = newCountry;
        this.org = newOrg;
    }
    
    public User(String username) {
        this.username = username;
    }
}
