package com.example.shankaryadav.contactsgps;

/**
 * Created by shankaryadav on 20/06/17.
 */


public class Location1 {

    private Double latitude;

    private Double longitude;

    public Double getLatitude(){
        return latitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public String toString(){
        return latitude+","+longitude;
    }
}