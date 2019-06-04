package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 2/18/2018.
 */

public class DataEvent {
    private String title;
    private String publisher;
    private String publisherID;
    private String eventNumber;
    private String date;
    private String dateOfEvent;
    private String price;
    private String country;
    private String city;
    private String adress;
    private String satrtAt;
    private String endAt;
    private String forWho;
    private String detail;
    private String coverIMage;

    public DataEvent(String title, String publisher, String publisherID, String eventNumber, String date, String dateOfEvent, String price, String country, String city, String adress, String satrtAt, String endAt, String forWho, String detail,String coverIMage) {
        this.title = title;
        this.publisher = publisher;
        this.publisherID = publisherID;
        this.eventNumber = eventNumber;
        this.date = date;
        this.dateOfEvent = dateOfEvent;
        this.price = price;
        this.country = country;
        this.city = city;
        this.adress = adress;
        this.satrtAt = satrtAt;
        this.endAt = endAt;
        this.forWho = forWho;
        this.detail = detail;
        this.coverIMage= coverIMage;
    }

    public DataEvent() {
    }

    public String getCoverIMage() {
        return coverIMage;
    }

    public void setCoverIMage(String coverIMage) {
        this.coverIMage = coverIMage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public String getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(String eventNumber) {
        this.eventNumber = eventNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSatrtAt() {
        return satrtAt;
    }

    public void setSatrtAt(String satrtAt) {
        this.satrtAt = satrtAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getForWho() {
        return forWho;
    }

    public void setForWho(String forWho) {
        this.forWho = forWho;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
