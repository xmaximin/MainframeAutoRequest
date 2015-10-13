package com.codeattitude.mainframeautorequest.api;

import android.util.Log;

import com.codeattitude.mainframeautorequest.controllers.RequestsActivity;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

/**
 * Created by xaviermaximin on 13/10/2015.
 */
public class TokenAuthenticator implements Authenticator {


    private static final String TAG = "TokenAuthenticator";

    final String AUTHORIZATION = "Authorization";
    String username;
    String password;

    private int mCounter = 0;


    @Override
    public Request authenticate(Proxy proxy, Response response) throws IOException {

        // this will retry automatically 3 times before if the request doesn't have the right token
        if (mCounter++ > 3) {

                throw new IOException(
                        "Invalid Login"+ response.message());

        }
        Log.d(TAG,"authenticate newAccessToken requested");

        // Refresh token with one from the preference or from a dedicated service
        String newAccessToken = Credentials.basic(username,password);

        // Add new header to rejected request and retry it
        return response.request().newBuilder()
                .header(AUTHORIZATION, newAccessToken)
                .build();
    }

    @Override
    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {

        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}