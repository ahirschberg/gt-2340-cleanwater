package model;

import com.lynden.gmapsfx.javascript.object.LatLong;


public class Location {
    private double latitude;


    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

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
