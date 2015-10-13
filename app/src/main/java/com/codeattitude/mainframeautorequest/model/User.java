package com.codeattitude.mainframeautorequest.model;

import java.util.ArrayList;

/**
 * Created by xaviermaximin on 10/10/2015.
 */
public class User {

    public static final String TOKEN = "TOKEN_KEY";
    public static final String IS_ALREADY_LOGGED = "IS_ALREADY_LOGGED";
    public static final String USERNAME = "USERNAME_KEY";
    public static final String PASSWORD = "PASSWORD_KEY";

    String user;
    String token;
    String profile;
    String status;
    String response;
    String url;
    String dateReceived;


    public User(){}


    public User(String user, String token, String profile) {
        this.user = user;
        this.token = token;
        this.profile = profile;
    }

    public User(String user, String token, String profile,String url, String status, String response, String dateReceived) {
        this.user = user;
        this.token = token;
        this.profile = profile;
        this.status = status;
        this.url = url;
        this.token = token;
        this.response = response;
        this.dateReceived = dateReceived;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
