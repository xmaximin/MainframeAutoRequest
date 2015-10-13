package com.codeattitude.mainframeautorequest.api;

import com.codeattitude.mainframeautorequest.config.ApiConfig;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by xaviermaximin on 13/10/2015.
 */
public class RefreshTokenService {


    private static final OkHttpClient client = new OkHttpClient();


    public static String refreshToken(String username, String password){
        final String token = new String();

        String credentials = Credentials.basic(username, password);

        Request request = new Request.Builder()
                .url(ApiConfig.baseUrl+"/authenticate")
                .addHeader("Authorization", credentials)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println(response);

            }
        });

        return token;
    }
}
