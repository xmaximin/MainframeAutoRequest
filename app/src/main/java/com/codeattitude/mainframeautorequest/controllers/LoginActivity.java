package com.codeattitude.mainframeautorequest.controllers;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codeattitude.mainframeautorequest.R;

public class LoginActivity extends AppCompatActivity {

    // current ressources
    EditText username;
    EditText password;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set current resources
        setResources();
        setColorAndFont();

        // attach event on button



    }







    // private methods
    private void setResources(){
        username = (EditText)findViewById(R.id.editTextUsername);
        password = (EditText)findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.login);
    }

    private void setColorAndFont(){
        Typeface tf = getTypeFaceFont("fonts/Museo Sans/MuseoSans-300.otf");
        username.setTypeface(tf, Typeface.BOLD);
        password.setTypeface(tf, Typeface.BOLD);
        login.setTypeface(tf, Typeface.BOLD);

    }

    private Typeface getTypeFaceFont(String fontfile){
        Typeface tf = Typeface.createFromAsset(getAssets(),
               fontfile);
        return tf;
    }



}
