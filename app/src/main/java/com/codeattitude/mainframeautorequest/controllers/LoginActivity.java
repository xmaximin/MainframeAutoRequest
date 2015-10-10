package com.codeattitude.mainframeautorequest.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codeattitude.mainframeautorequest.R;
import com.codeattitude.mainframeautorequest.model.User;

public class LoginActivity extends AppCompatActivity {

    // current resources
    EditText editTestUsername;
    EditText editTextPassword;
    Button btnLogin;
    public static final String PREFS_NAME = "MFAutoRequestPrefsFile";



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
            goToRequestsActivityAndFinish();
        }





        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send the authenticate request
                String username = editTestUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // if success :





                // store token in preference android
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(User.TOKEN, "");
                editor.commit();
                // then go to RequestsActivity
                goToRequestsActivityAndFinish();




                //if error :
                // display error message red with Toast
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


}
