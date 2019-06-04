package com.dalelak.eyads.dalelakْْ;

import java.io.Serializable;

/**
 * Created by eyads on 1/30/2018.
 */

public class dataList  implements Serializable
{
    private String userID;
    private String description;
    private String name ;
    private String price;
    private String date;
    private String status;
    private String delivery;
    private String country;
    private String province;
    private String uri;
    private String uri2;
    private String uri3;
    private String postname;
    private String imageUri;


    public dataList(String userID,String uri3  ,String uri2 ,String uri ,String description, String name, String price, String date, String status, String delivery, String country, String province,String postname,String imageUri) {
        this.setDescription(description);
        this.setName(name);
        this.setPrice(price);
        this.setDate(date);
        this.setStatus(status);
        this.setDelivery(delivery);
        this.setCountry(country);
        this.setProvince(province);
        this.setUri(uri);
        this.setUri2(uri2);
        this.setUri3(uri3);
        this.setPostname(postname);
        this.setImageUri(imageUri);
        this.setUserID(userID);

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public dataList() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }



    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri2() {
        return uri2;
    }

    public void setUri2(String uri2) {
        this.uri2 = uri2;
    }

    public String getUri3() {
        return uri3;
    }

    public void setUri3(String uri3) {
        this.uri3 = uri3;
    }

    @Override
    public String toString() {
        return "dataList{" +
                "description='" + getDescription() + '\'' +
                ", name='" + getName() + '\'' +
                ", price='" + getPrice() + '\'' +
                ", date='" + getDate() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", delivery='" + getDelivery() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", province='" + getProvince() + '\'' +
                ", uri='" + getUri() + '\'' +
                '}';
    }
}
