package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 2/19/2018.
 */

public class DataListComment {
    private String Id;
    private String number;
    private String date;
    private String comment;
    private String country;
    private String type;
    private String postnumber;

    public DataListComment() {
    }

    public DataListComment(String id, String number, String date, String comment, String country, String type,String postnumber) {
        Id = id;
        this.number = number;
        this.date = date;
        this.comment = comment;
        this.country = country;
        this.type = type;
        this.postnumber=postnumber;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
