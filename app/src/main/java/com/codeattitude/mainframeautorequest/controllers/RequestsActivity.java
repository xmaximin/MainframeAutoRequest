package com.codeattitude.mainframeautorequest.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.codeattitude.mainframeautorequest.R;
import com.codeattitude.mainframeautorequest.adapter.ResquestAdapter;
import com.codeattitude.mainframeautorequest.api.RefreshTokenService;
import com.codeattitude.mainframeautorequest.config.ApiConfig;
import com.codeattitude.mainframeautorequest.model.User;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RequestsActivity extends Activity {


    public static final String PREFS_NAME = "MFAutoRequestPrefsFile";
    private static final String TAG = "LoginActivity";
    public static ArrayList<User> listU = new ArrayList<User>();
    private final OkHttpClient client = new OkHttpClient();
    // current resources
    Button buttonExpire;
    Button buttonLogout;
    Button buttonSendRequest;
    ResquestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        // clear the log list
        listU.clear();

        // retrieve the resource from the view
        setResources();

        // init the listview
        final ResquestAdapter adapter = new ResquestAdapter(this, R.layout.list_row_request, listU);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);


        // attache element to the button request
        buttonSendRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // get token and username and password
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String token = settings.getString(User.TOKEN, "default_token");
                final String username = settings.getString(User.USERNAME, "default_username");
                final String password = settings.getString(User.PASSWORD, "default_password");

                Request request = new Request.Builder()
                        .url(ApiConfig.baseUrl+"views/index_summary")
                        .addHeader("Mainframe-Token ", token)
                        .build();


                client.newCall(request).enqueue(new com.squareup.okhttp.Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                        // handle execution failures like no internet connectivity
                        Log.d(TAG, "onFailure reached!");
                    }

                    @Override
                    public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                        if (response.isSuccessful()) {

                            User user = new User();
                            user.setStatus(Integer.toString(response.code()));
                            user.setResponse(response.body().string());
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                            String formattedDate = dateFormat.format(new Date()).toString();
                            user.setDateReceived(formattedDate);
                            listU.add(user);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        } else {
                            User user = new User();
                            user.setStatus(Integer.toString(response.code()));
                            listU.add(user);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                            if( response.code() == 401){


                                String credentials = Credentials.basic(username, password);

                                Request request = new Request.Builder()
                                        .url(ApiConfig.baseUrl+"/authenticate")
                                        .addHeader("Authorization ", credentials)
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


                                String token1 =   RefreshTokenService.refreshToken(username,password);


                                Request request = new Request.Builder()
                                        .url(ApiConfig.baseUrl+"/views/index_summary")
                                        .addHeader("Mainframe-Token ", token1)
                                        .build();

                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Request request, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Response response) throws IOException {
                                        User user = new User();
                                        user.setStatus(Integer.toString(response.code()));
                                        user.setResponse(response.body().string());
                                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                                        String formattedDate = dateFormat.format(new Date()).toString();
                                        user.setDateReceived(formattedDate);
                                        listU.add(user);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });

                                    }
                                });


                            }
                        }

                        Log.d(TAG, "onResponse reached!" + response.code());
                    }
                });


            }
        });


        buttonExpire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // store token in android preference
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                byte[] authEncBytes = Base64.encode("wrong_token".getBytes(), Base64.NO_WRAP);
                String authStringEnc = new String(authEncBytes);
                editor.remove(User.TOKEN);
                editor.putString(User.TOKEN, authStringEnc);
                editor.commit();
                Log.d(TAG, "token changed");
            }
        });


        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // store token in android preference
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove(User.TOKEN);
                editor.remove(User.USERNAME);
                editor.remove(User.PASSWORD);
                editor.putBoolean(User.IS_ALREADY_LOGGED, false);
                editor.commit();
                goBack();

            }
        });


    }

    // private methods
    private void setResources() {
        buttonExpire = (Button) findViewById(R.id.button_expire);
        buttonLogout = (Button) findViewById(R.id.button_logout);
        buttonSendRequest = (Button) findViewById(R.id.button_sendRequest);
    }

    private String get(String username, String password) {
        String authString = username + ":" + password;
        byte[] authEncBytes = Base64.encode(authString.getBytes(), Base64.NO_WRAP);
        String authStringEnc = new String(authEncBytes);
        return authStringEnc;
    }

    private void goBack() {
        Intent intent = new Intent(RequestsActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
