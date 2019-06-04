package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 2/27/2018.
 */

public class DataListMainChat {
    private String userid;
    private String date;

    public DataListMainChat() {
    }

    public DataListMainChat(String userid, String date) {
        this.userid = userid;
        this.date = date;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
