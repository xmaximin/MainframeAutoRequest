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
import android.widget.EditText;
import android.widget.ListView;

import com.codeattitude.mainframeautorequest.R;
import com.codeattitude.mainframeautorequest.adapter.ResquestAdapter;
import com.codeattitude.mainframeautorequest.api.TokenAuthenticator;
import com.codeattitude.mainframeautorequest.api.UserService;
import com.codeattitude.mainframeautorequest.config.ApiConfig;
import com.codeattitude.mainframeautorequest.model.IndexSummary;
import com.codeattitude.mainframeautorequest.model.User;
import com.google.gson.Gson;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RequestsActivity extends Activity {



    private static final String TAG = "LoginActivity";

    public static final String PREFS_NAME = "MFAutoRequestPrefsFile";


    // current resources
    Button buttonExpire;
    Button buttonLogout;
    Button buttonSendRequest;
    public static ArrayList<User> listU = new ArrayList<User>();
    ResquestAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        // clear the log list
        listU.clear();

        // retrieve the resource from the view
        setResources();

        // init the listview
        final ResquestAdapter adapter = new ResquestAdapter(this,R.layout.list_row_request, listU);
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);


        // attache element to the button request
        buttonSendRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // get token and username and password
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String token = settings.getString(User.TOKEN, "default_token");
                Log.d(TAG, "log token:"+token);
                String username = settings.getString(User.USERNAME, "default_username");
                String password = settings.getString(User.PASSWORD, "default_password");

                // this will automatically retry 3 times if 401 is detected try if un
                // https://github.com/square/okhttp/wiki/Recipes#handling-authentication
                TokenAuthenticator authAuthenticator = new TokenAuthenticator();
                authAuthenticator.setUsername(username);
                authAuthenticator.setPassword(password);

                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.setAuthenticator(authAuthenticator);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiConfig.baseUrl)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserService service = retrofit.create(UserService.class);

                Call<IndexSummary> user = service.getIndexSummary(token);

                user.enqueue(new Callback<IndexSummary>() {
                    @Override
                    public void onResponse(Response<IndexSummary> response, Retrofit retrofit) {
                        // response.isSuccess() is true if the response code is 2xx
                        if (response.isSuccess()) {
                            IndexSummary indexSummary = response.body();
                            User user = new User();
                            user.setStatus(Integer.toString(response.code()));
                            Gson gson = new Gson();
                            gson.toJson(indexSummary).toString();
                            user.setResponse(gson.toJson(indexSummary).toString());
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                            String formattedDate = dateFormat.format(new Date()).toString();
                            user.setDateReceived(formattedDate);
                            listU.add(user);
                            adapter.notifyDataSetChanged();


                        } else {
                            int statusCode = response.code();
                            // handle request errors yourself
                            ResponseBody errorBody = response.errorBody();
                            User user = new User();
                            user.setStatus(Integer.toString(response.code()));

                            listU.add(user);
                            adapter.notifyDataSetChanged();

                        }

                        Log.d(TAG, "onResponse reached!");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // handle execution failures like no internet connectivity
                        Log.d(TAG, "onFailure reached!");
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

    private String get(String username,String password){
        String authString = username + ":" + password;
        byte[] authEncBytes = Base64.encode(authString.getBytes(), Base64.NO_WRAP);
        String authStringEnc = new String(authEncBytes);
        return authStringEnc;
    }

    private void goBack(){
        Intent intent = new Intent(RequestsActivity.this,
                LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
