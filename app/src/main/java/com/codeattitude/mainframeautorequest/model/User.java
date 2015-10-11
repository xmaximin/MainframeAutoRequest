package com.codeattitude.mainframeautorequest.model;

/**
 * Created by xaviermaximin on 10/10/2015.
 */
public class User {

    public static final String TOKEN = "token_key";
    public static final String USERNAME = "username_key";
    public static final String PASSWORD = "password_key";



    String user;
    String token;
    String profile;



    public User(String user, String token, String profile) {
        this.user = user;
        this.token = token;
        this.profile = profile;
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
