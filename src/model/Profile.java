package model;

/**
 * Created by alex on 10/17/16.
 */
public class Profile {
    //Profile
    private final String name;
    private final String email;
    private final String street;
    private final String city;
    private final String state;
    private final String country;
    private final String org;

    /**
     * retrieves name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * retrieves email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * retrieves street
     * @return street
     */
    public String getStreet() {
        return street;
    }
    
    /**
     * retrieves city
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * retrieves state
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * retrieves country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * retrieves organization 
     * @return organization
     */
    public String getOrg() {
        return org;
    }

    /**
     * retrieves address
     * @return address
     */
    public String getAddress() {
        return street
                + "\n"
                + city
                + ", "
                + state
                + "\n"
                + country;
    }
    
    /**
     * initializes empty profile
     */
    public Profile() {
        this("", "", "", "", "", "", "");
    }

    /**
     * initializes profile with given info
     * @param name user's name
     * @param email user's email
     * @param street user's street
     * @param city user's city
     * @param state user's state
     * @param country user's country
     * @param org user's organization
     */
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
