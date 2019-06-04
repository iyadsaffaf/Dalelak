package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 2/17/2018.
 */

public class DataListInfo {
    private String question;
    private String questionnumber;
    private String dateOfQuestion;
    private String userID;
    private String country;
    private String imagethumUri;


    public DataListInfo() {
    }

    public DataListInfo(String question, String questionnumber,  String dateOfQuestion, String userID, String country, String imagethumUri) {
        this.question = question;
        this.questionnumber = questionnumber;
        this.dateOfQuestion = dateOfQuestion;
        this.userID = userID;
        this.country = country;
        this.imagethumUri = imagethumUri;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionnumber() {
        return questionnumber;
    }

    public void setQuestionnumber(String questionnumber) {
        this.questionnumber = questionnumber;
    }

    public String getDateOfQuestion() {
        return dateOfQuestion;
    }

    public void setDateOfQuestion(String dateOfQuestion) {
        this.dateOfQuestion = dateOfQuestion;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImagethumUri() {
        return imagethumUri;
    }

    public void setImagethumUri(String imagethumUri) {
        this.imagethumUri = imagethumUri;
    }
}
