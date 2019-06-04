package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 3/7/2018.
 */

public class datalistlike {
    private  String userid;
    private  String typeoflike;
    private  String key;
    private String country;
    private String type;

    public datalistlike() {
    }

    public datalistlike(String userid, String typeoflike, String key, String country, String type) {
        this.userid = userid;
        this.typeoflike = typeoflike;
        this.key = key;
        this.country = country;
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTypeoflike() {
        return typeoflike;
    }

    public void setTypeoflike(String typeoflike) {
        this.typeoflike = typeoflike;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
