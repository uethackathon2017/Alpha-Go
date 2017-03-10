package com.quang.tracnghiemtoan.models;

/**
 * Created by QUANG on 1/10/2017.
 */

public class Message {
    private String userName;
    private String facebookId;
    private String firebaseId;
    private String message;
    private long timeSend;

    public Message() {
    }

    public Message(String userName, String facebookId, String firebaseId, String message, long timeSend) {
        this.userName = userName;
        this.facebookId = facebookId;
        this.firebaseId = firebaseId;
        this.message = message;
        this.timeSend = timeSend;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(long timeSend) {
        this.timeSend = timeSend;
    }
}
