package com.example.shankaryadav.contactsgps;

/**
 * Created by shankaryadav on 20/06/17.
 */


public class Contact {

    private String mobile;

    private Location1 location;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Location1 getLocation() {
        return location;
    }

    public void setLocation(Location1 location) {
        this.location = location;
    }

    @Override
    public String toString(){
        return mobile+":"+location;
    }
}