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
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    public static final String PREFS_NAME = "MFAutoRequestPrefsFile";


    // Current resources
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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // retrieve login access info
                String username = editTestUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                String authStringEnc  = Credentials.basic(username, password);
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

                            // store token in android preference
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString(User.USERNAME,editTestUsername.getText().toString());
                            editor.putString(User.PASSWORD, editTextPassword.getText().toString());
                            editor.putString(User.TOKEN,user.getToken());
                            editor.commit();

                            // then go to RequestsActivity
                            goToRequestsActivityAndFinish();

                        } else {
                            int statusCode = response.code();
                            // handle request errors yourself
                            ResponseBody errorBody = response.errorBody();
                            Log.d(TAG,"Authentification request -> status code : "+Integer.toString(statusCode));
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
        editTestUsername = (EditText) findViewById(R.id.editText_username);
        editTextPassword = (EditText) findViewById(R.id.editText_password);
        btnLogin = (Button) findViewById(R.id.button_login);
    }

    private void setColorAndFont() {
        Typeface tf = getTypeFaceFont("fonts/Museo Sans/MuseoSans-300.otf");
        editTestUsername.setTypeface(tf, Typeface.BOLD);
        editTextPassword.setTypeface(tf, Typeface.BOLD);
        btnLogin.setTypeface(tf, Typeface.BOLD);

    }

    private Typeface getTypeFaceFont(String fontFile) {
        Typeface tf = Typeface.createFromAsset(getAssets(),
                fontFile);
        return tf;
    }

    private void goToRequestsActivityAndFinish(){
        Intent intent = new Intent(LoginActivity.this,
                RequestsActivity.class);
        startActivity(intent);
        finish();
    }


}
