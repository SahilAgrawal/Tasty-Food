package com.tastyfoodpvtltd.ordermanagementservice.response;

public class Geolocation {
    private double latitude;
    private double longitude;

    public Geolocation() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
