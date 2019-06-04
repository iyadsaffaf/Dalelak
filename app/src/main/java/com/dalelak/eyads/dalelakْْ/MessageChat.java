package com.dalelak.eyads.dalelakْْ;

/**
 * Created by eyads on 3/1/2018.
 */

public class MessageChat {
private String message,type,from,key,to;
private boolean seen;
private String time;

    public MessageChat() {
    }

    public MessageChat(String message, String type, String from, String key, String to, boolean seen, String time) {
        this.message = message;
        this.type = type;
        this.from = from;
        this.key = key;
        this.to = to;
        this.seen = seen;
        this.time = time;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

