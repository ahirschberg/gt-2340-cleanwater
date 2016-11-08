package model;

/**
 * Created by alex on 10/17/16.
 */
public class Profile {
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

    public Profile() {
        this("", "", "", "", "", "", "");
    }

    public Profile(String name, String email,
                   String street, String city,
                   String state, String country, String org) {
        this.name = name;
        this.email = email;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.org = org;
    }
}
