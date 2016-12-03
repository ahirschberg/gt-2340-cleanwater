package model;

import com.lynden.gmapsfx.javascript.object.LatLong;


public class Location {
    private final double latitude;


    private final double longitude;

    /**
     * initializes location
     * @param latitude lat of location
     * @param longitude long of location
     */
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * retrieves lat
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }
    
    /**
     * Retrieves longitude
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Converts location to latlong
     * @return the latlong
     */
    public LatLong toLatLong() {
        return new LatLong(this.latitude, this.longitude);
    }

    /**
     * Returns the location of the report
     *
     * @return location, as a string
     */
    public String toString() {
        return latitude + "," + longitude;
    }
}
