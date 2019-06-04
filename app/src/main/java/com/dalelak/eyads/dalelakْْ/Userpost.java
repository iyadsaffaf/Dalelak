package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 2/23/2018.
 */

public class Userpost {
    private String postnumber;
    private String country;

    public Userpost() {
    }

    public Userpost(String postnumber, String country) {
        this.postnumber = postnumber;
        this.country = country;
    }

    public String getPostnumber() {
        return postnumber;
    }

    public void setPostnumber(String postnumber) {
        this.postnumber = postnumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
