package com.codeattitude.mainframeautorequest.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codeattitude.mainframeautorequest.R;
import com.codeattitude.mainframeautorequest.api.UserService;
import com.codeattitude.mainframeautorequest.config.ApiConfig;
import com.codeattitude.mainframeautorequest.model.User;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    public static final String PREFS_NAME = "MFAutoRequestPrefsFile";
    // current resources
    EditText editTestUsername;
    EditText editTextPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set current resources
        setResources();
        setColorAndFont();


        // verify token has not already been set.
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = settings.getString(User.TOKEN, "no_token");

        // if token already here ...
        if(!token.equals("no_token")){
            // ... go to RequestsActivity
            //goToRequestsActivityAndFinish();
        }





        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // retrieve login access info
                String username = editTestUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                String authStringEnc  = getBasicHttpAuthenticationHeader(username, password);
                // Send the authenticate request

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiConfig.baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserService service =  retrofit.create(UserService.class);

                Call<User> user  = service.getUser(authStringEnc);

                user.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        // response.isSuccess() is true if the response code is 2xx
                        if (response.isSuccess()) {
                            User user = response.body();

                            // store token in preference android
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString(User.TOKEN, user.getToken());
                            editor.commit();

                            Log.d(TAG, "Authentfication request -> Success Token : " + user.getToken());
                            Log.d(TAG, "Authentfication request -> Success Profile : " + user.getProfile());
                            Log.d(TAG, "Authentfication request -> Success User : " +  user.getUser());

                            // then go to RequestsActivity
                            goToRequestsActivityAndFinish();

                        } else {
                            int statusCode = response.code();

                            // handle request errors yourself
                            ResponseBody errorBody = response.errorBody();

                            Log.d(TAG,"Authentfication request -> status code : "+Integer.toString(statusCode));

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        // handle execution failures like no internet connectivity
                        Log.d(TAG,"Authentfication request -> NO CONNECTIVITY");


                    }
                });

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    // private methods
    private void setResources() {
        editTestUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.login);
    }

    private void setColorAndFont() {
        Typeface tf = getTypeFaceFont("fonts/Museo Sans/MuseoSans-300.otf");
        editTestUsername.setTypeface(tf, Typeface.BOLD);
        editTextPassword.setTypeface(tf, Typeface.BOLD);
        btnLogin.setTypeface(tf, Typeface.BOLD);

    }

    private Typeface getTypeFaceFont(String fontfile) {
        Typeface tf = Typeface.createFromAsset(getAssets(),
                fontfile);
        return tf;
    }

    private void goToRequestsActivityAndFinish(){
        Intent intent = new Intent(LoginActivity.this,
                RequestsActivity.class);
        startActivity(intent);
        finish();
    }

    private String getBasicHttpAuthenticationHeader(String username,String password){
        return String.format("Basic %s", Base64.encodeToString(String.format("%s:%s",username,password).getBytes(),Base64.NO_WRAP)) ;
    }


}
